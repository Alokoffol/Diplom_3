package tests;

import constants.AppConstants;
import io.qameta.allure.Description;
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
import com.github.javafaker.Faker;
import java.util.Locale;

public class LoginTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private String email;
    private String password;
    private String accessToken;
    private Faker faker = new Faker(new Locale("en"));

    @Parameters("browser")
    @BeforeMethod
    @Description("Создание тестового пользователя через API")
    public void setUp(@Optional("chrome") String browser) {
        String userName = faker.name().username();
        email = userName + "@example.com";
        password = "password123";

        createTestUser(email, password);

        driver = DriverFactory.createDriver(browser);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test(description = "Проверка входа через кнопку 'Войти в аккаунт' на главной странице")
    public void testLoginFromMainButton() {
        mainPage.clickLoginButton();
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), AppConstants.BASE_URL, "После входа пользователь не был перенаправлен на главную страницу.");
    }

    @Test(description = "Проверка входа через кнопку 'Личный Кабинет'")
    public void testLoginFromPersonalCabinet() {
        mainPage.clickPersonalCabinet();
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), AppConstants.BASE_URL, "После входа пользователь не был перенаправлен на главную страницу.");
    }

    @Test(description = "Проверка входа с страницы регистрации")
    public void testLoginFromRegisterPage() {
        mainPage.clickLoginButton();
        loginPage.goToRegisterPage();
        registerPage.goToLoginPage();
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), AppConstants.BASE_URL, "После входа пользователь не был перенаправлен на главную страницу.");
    }

    @Test(description = "Проверка входа с страницы восстановления пароля")
    public void testLoginFromForgotPasswordPage() {
        mainPage.clickLoginButton();
        loginPage.goToForgotPasswordPage();
        loginPage.goToLoginPage();
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), AppConstants.BASE_URL, "После входа пользователь не был перенаправлен на главную страницу.");
    }

    @AfterMethod
    @Description("Удаление тестового пользователя через API и закрытие браузера")
    public void tearDown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            ApiHelper.deleteCurrentUser(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    private void createTestUser(String email, String password) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", password);
        userData.put("name", faker.name().fullName());

        Map<String, Object> apiResponse = ApiHelper.registerUser(userData);

        if (apiResponse == null || !apiResponse.containsKey("accessToken")) {
            throw new RuntimeException("Не удалось создать пользователя через API: " + apiResponse);
        }

        accessToken = (String) apiResponse.get("accessToken");
    }
}