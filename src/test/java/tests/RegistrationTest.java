package tests;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;
import utils.DriverFactory;

import java.util.UUID;

public class RegistrationTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    @Parameters("browser")
    @BeforeMethod
    @Step("Настройка драйвера для браузера: {browser}")
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.createDriver(browser);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test
    @Description("Тест успешной регистрации пользователя")
    public void testSuccessfulRegistration() {
        // Переход на страницу регистрации
        navigateToRegistrationPage();

        // Генерация тестовых данных
        String name = "Test User";
        String email = generateTestEmail();
        String password = "123456";

        // Регистрация пользователя
        registerUser(name, email, password);

        // Проверка успешной регистрации
        verifySuccessfulRegistration();
    }

    @Test
    @Description("Тест ошибки при коротком пароле")
    public void testInvalidPasswordError() {
        // Переход на страницу регистрации
        navigateToRegistrationPage();

        // Попытка регистрации с коротким паролем
        registerWithShortPassword();

        // Проверка отображения ошибки
        verifyPasswordErrorDisplayed();
    }

    @Step("Переход на страницу регистрации")
    private void navigateToRegistrationPage() {
        mainPage.clickLoginButton();
        loginPage.goToRegisterPage();
    }

    @Step("Генерация тестового email")
    private String generateTestEmail() {
        return "testuser" + UUID.randomUUID() + "@example.com";
    }

    @Step("Регистрация пользователя: имя={name}, email={email}")
    private void registerUser(String name, String email, String password) {
        registerPage.register(name, email, password);
    }

    @Step("Проверка успешной регистрации")
    private void verifySuccessfulRegistration() {
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/"),
                "После регистрации пользователь не был перенаправлен на главную страницу."
        );
    }

    @Step("Регистрация с коротким паролем")
    private void registerWithShortPassword() {
        registerPage.register("Test User", "test@example.com", "123");
    }

    @Step("Проверка отображения ошибки пароля")
    private void verifyPasswordErrorDisplayed() {
        Assert.assertTrue(registerPage.isErrorPasswordDisplayed(),
                "Ошибка о коротком пароле не отображается");
    }

    @AfterMethod
    @Step("Завершение работы драйвера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}