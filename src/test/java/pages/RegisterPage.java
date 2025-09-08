package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    private By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordField = By.xpath("//input[@name='Пароль']");
    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private By errorPasswordMessage = By.xpath("//p[text()='Некорректный пароль']");
    private By loginLink = By.xpath("//a[text()='Войти']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void register(String name, String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(registerButton).click();
    }

    public boolean isErrorPasswordDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorPasswordMessage)).isDisplayed();
    }

    public void goToLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }
}