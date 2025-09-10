package tests;

import org.openqa.selenium.By;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;
import utils.ApiHelper;
import utils.DriverFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.restassured.response.Response;

public class RegistrationTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private String accessToken;

    @Parameters("browser")
    @BeforeMethod
    @Step("Настройка драйвера для браузера: {browser}")
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.createDriver(browser);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test(description = "Проверка успешной регистрации нового пользователя через UI")
    @DisplayName("Успешная регистрация")
    public void testSuccessfulRegistration() {
        navigateToRegistrationPage();

        String name = "Test User";
        String email = generateTestEmail();
        String password = "password123";

        registerUser(name, email, password);

        // Ждем пока появится кнопка "Войти" (значит мы на странице логина)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[text()='Войти']")));

        verifySuccessfulRegistration();

        loginToGetToken(email, password);
    }

    @Test(description = "Проверка отображения ошибки при вводе короткого пароля (менее 6 символов)")
    @DisplayName("Ошибка при коротком пароле")
    public void testInvalidPasswordError() {
        navigateToRegistrationPage();

        registerWithShortPassword();

        verifyPasswordErrorDisplayed();
    }

    @AfterMethod
    @Step("Завершение работы: удаление пользователя через API и закрытие драйвера")
    public void tearDown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            ApiHelper.deleteCurrentUser(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Переход на страницу регистрации")
    private void navigateToRegistrationPage() {
        mainPage.clickLoginButton();
        loginPage.goToRegisterPage();
    }

    @Step("Генерация уникального тестового email")
    private String generateTestEmail() {
        return "testuser" + UUID.randomUUID() + "@example.com";
    }

    @Step("Регистрация пользователя через UI: имя={name}, email={email}")
    private void registerUser(String name, String email, String password) {
        registerPage.register(name, email, password);
    }

    @Step("Проверка успешной регистрации: проверяем что находимся на странице логина")
    private void verifySuccessfulRegistration() {
        // После регистрации пользователь должен быть перенаправлен на страницу логина
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login") ||
                        isLoginButtonDisplayed(),
                "После регистрации пользователь не был перенаправлен на страницу логина. Текущий URL: " + driver.getCurrentUrl()
        );
    }

    @Step("Проверка отображения кнопки Войти")
    private boolean isLoginButtonDisplayed() {
        try {
            return driver.findElement(By.xpath("//button[text()='Войти']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Регистрация с коротким паролем (3 символа)")
    private void registerWithShortPassword() {
        registerPage.register("Test User", "test@example.com", "123");
    }

    @Step("Проверка отображения ошибки о некорректном пароле")
    private void verifyPasswordErrorDisplayed() {
        Assert.assertTrue(
                registerPage.isErrorPasswordDisplayed(),
                "Ошибка о коротком пароле не отображается"
        );
    }

    @Step("Получение токена пользователя через API для удаления")
    private void loginToGetToken(String email, String password) {
        Map<String, Object> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);

        Response response = io.restassured.RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(loginData)
                .post("https://stellarburgers.nomoreparties.site/api/auth/login");

        if (response.statusCode() == 200) {
            accessToken = response.jsonPath().getString("accessToken");
        } else {
            System.out.println("Не удалось получить токен для удаления пользователя: " + response.asString());
        }
    }
}