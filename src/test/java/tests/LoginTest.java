package tests;

import constants.AppConstants;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
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
import utils.ApiHelper;
import utils.DriverFactory;

import java.util.HashMap;
import java.util.Map;

public class LoginTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private String email;
    private String password;
    private String accessToken;

    @Parameters("browser")
    @BeforeMethod
    @Description("Создание тестового пользователя через API")
    public void setUp(@Optional("chrome") String browser) {
        // Генерируем уникальные данные для пользователя
        email = "user" + System.currentTimeMillis() + "@example.com";
        password = "password123";

        // Создаем пользователя через API
        createTestUser(email, password);

        // Инициализируем драйвер и страницы
        driver = DriverFactory.createDriver(browser);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test(description = "Проверка входа через кнопку 'Войти в аккаунт' на главной странице")
    @DisplayName("Вход с главной страницы")
    public void testLoginFromMainButton() {
        mainPage.clickLoginButton();
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), AppConstants.BASE_URL, "После входа пользователь не был перенаправлен на главную страницу.");
    }

    @Test(description = "Проверка входа через кнопку 'Личный Кабинет'")
    @DisplayName("Вход из Личного Кабинета")
    public void testLoginFromPersonalCabinet() {
        mainPage.clickPersonalCabinet();
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), AppConstants.BASE_URL, "После входа пользователь не был перенаправлен на главную страницу.");
    }

    @Test(description = "Проверка входа с страницы регистрации")
    @DisplayName("Вход с страницы регистрации")
    public void testLoginFromRegisterPage() {
        mainPage.clickLoginButton();
        loginPage.goToRegisterPage();
        registerPage.goToLoginPage(); // Используем метод Page Object
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), AppConstants.BASE_URL, "После входа пользователь не был перенаправлен на главную страницу.");
    }

    @Test(description = "Проверка входа с страницы восстановления пароля")
    @DisplayName("Вход с страницы восстановления пароля")
    public void testLoginFromForgotPasswordPage() {
        mainPage.clickLoginButton();
        loginPage.goToForgotPasswordPage();
        loginPage.goToLoginPage(); // Используем метод Page Object вместо driver.findElement
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), AppConstants.BASE_URL, "После входа пользователь не был перенаправлен на главную страницу.");
    }

    @AfterMethod
    @Description("Удаление тестового пользователя через API и закрытие браузера")
    public void tearDown() {
        // Удаляем пользователя через API
        if (accessToken != null && !accessToken.isEmpty()) {
            ApiHelper.deleteCurrentUser(accessToken);
        }
        // Закрываем браузер
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Создает тестового пользователя через API и сохраняет его токен.
     */
    private void createTestUser(String email, String password) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", password);
        userData.put("name", "Test User");

        Map<String, Object> apiResponse = ApiHelper.registerUser(userData);

        if (apiResponse == null || !apiResponse.containsKey("accessToken")) {
            throw new RuntimeException("Не удалось создать пользователя через API: " + apiResponse);
        }

        accessToken = (String) apiResponse.get("accessToken");
    }
}