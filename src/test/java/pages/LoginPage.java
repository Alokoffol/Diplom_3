package pages;

import constants.AppConstants;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private By emailField = By.xpath("//input[@name='name']");
    private By passwordField = By.xpath("//input[@name='Пароль']");
    private By loginButton = By.xpath("//button[text()='Войти']");
    private By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private By forgotPasswordLink = By.xpath("//a[text()='Восстановить пароль']");
    private By loginLink = By.xpath("//a[text()='Войти']"); // ДОБАВЛЕНО: локатор для ссылки "Войти"

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();

        // Ждём, пока URL изменится (редирект на главную страницу)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(AppConstants.BASE_URL));
    }

    // ИСПРАВЛЕНО: wait_until -> wait.until
    public void goToLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    public void goToRegisterPage() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
    }

    public void goToForgotPasswordPage() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
    }
}