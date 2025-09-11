package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    // Исправленные локаторы, основанные на структуре HTML
    // Находим label, затем поднимаемся к родительскому элементу и ищем input внутри него
    private final By emailField = By.xpath("//label[text()='Email']/../input");
    private final By passwordField = By.xpath("//label[text()='Пароль']/../input");

    // Локатор для кнопки "Войти"
    private final By loginButton = By.xpath("//button[text()='Войти']");

    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath("//a[text()='Восстановить пароль']");
    private final By loginLink = By.xpath("//a[text()='Войти']");

    // Локатор для кнопки "Войти" после регистрации/выхода (на странице /login)
    private final By loginButtonAfterRegister = By.xpath("//button[text()='Войти']");

    // Локатор для кнопки "Оформить заказ" на главной странице, используется для ожидания перехода
    private final By orderButton = By.xpath("//button[text()='Оформить заказ']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Авторизация пользователя: email={email}")
    public void login(String email, String password) {
        System.out.println("DEBUG LoginPage: Попытка входа. Email: " + email + ", Password: " + password);
        sendKeysToElement(emailField, email);
        sendKeysToElement(passwordField, password);
        clickElement(loginButton);

        // Ждём, пока URL изменится на главную страницу
        // Или ждем появления характерного элемента на главной странице
        waitForElement(orderButton); // Предпочтительный способ
        // waitForUrlContains(AppConstants.BASE_URL); // Альтернатива
    }

    @Step("Переход на страницу логина")
    public void goToLoginPage() {
        clickElement(loginLink);
    }

    @Step("Переход на страницу регистрации")
    public void goToRegisterPage() {
        clickElement(registerLink);
    }

    @Step("Переход на страницу восстановления пароля")
    public void goToForgotPasswordPage() {
        clickElement(forgotPasswordLink);
    }

    @Step("Ожидание отображения кнопки 'Войти' после регистрации/выхода")
    public void waitForLoginButtonToBeVisible() {
        waitForElement(loginButtonAfterRegister);
    }

    @Step("Проверка отображения кнопки 'Войти' после регистрации")
    public boolean isLoginButtonDisplayed() {
        try {
            return isElementDisplayed(loginButtonAfterRegister);
        } catch (Exception e) {
            return false;
        }
    }
}