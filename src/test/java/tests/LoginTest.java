package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;
import utils.DriverFactory;

public class LoginTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        driver = DriverFactory.createDriver(browser);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test
    public void testLoginFromMainButton() {
        mainPage.clickLoginButton();
        loginPage.login("testuser@example.com", "123456");
        Assert.assertTrue(driver.getCurrentUrl().contains("/")); // Перешли на главную
    }

    @Test
    public void testLoginFromPersonalCabinet() {
        mainPage.clickPersonalCabinet();
        loginPage.login("testuser@example.com", "123456");
        Assert.assertTrue(driver.getCurrentUrl().contains("/"));
    }

    @Test
    public void testLoginFromRegisterPage() {
        mainPage.clickLoginButton();
        loginPage.goToRegisterPage();
        registerPage.goToLoginPage();
        loginPage.login("testuser@example.com", "123456");
        Assert.assertTrue(driver.getCurrentUrl().contains("/"));
    }

    @Test
    public void testLoginFromForgotPasswordPage() {
        mainPage.clickLoginButton();
        loginPage.goToForgotPasswordPage();
        driver.findElement(org.openqa.selenium.By.xpath("//a[text()='Войти']")).click();
        loginPage.login("testuser@example.com", "123456");
        Assert.assertTrue(driver.getCurrentUrl().contains("/"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}