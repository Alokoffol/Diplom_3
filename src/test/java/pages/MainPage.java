package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    // Локаторы для РОДИТЕЛЬСКИХ div-элементов вкладок
    private By bunsTab = By.xpath("//div[./span[text()='Булки']]");
    private By saucesTab = By.xpath("//div[./span[text()='Соусы']]");
    private By fillingsTab = By.xpath("//div[./span[text()='Начинки']]");

    // Локаторы для кнопок
    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By personalCabinetButton = By.xpath("//p[text()='Личный Кабинет']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void clickPersonalCabinet() {
        wait.until(ExpectedConditions.elementToBeClickable(personalCabinetButton)).click();
    }

    public void navigateToSection(String section) {
        By tabLocator = getTabLocator(section);
        WebElement tabElement = wait.until(ExpectedConditions.elementToBeClickable(tabLocator));

        // Скроллим к элементу
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", tabElement);

        // КЛИКАЕМ ЧЕРЕЗ JAVASCRIPT, чтобы обойти ElementClickInterceptedException
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tabElement);

        // Ждем, пока РОДИТЕЛЬСКИЙ div станет активным
        wait.until(ExpectedConditions.attributeContains(tabLocator, "class", "tab_tab_type_current__"));
    }

    public boolean isSectionActive(String section) {
        By tabLocator = getTabLocator(section);
        String activeClass = "tab_tab_type_current__";

        return wait.until(ExpectedConditions.presenceOfElementLocated(tabLocator))
                .getAttribute("class").contains(activeClass);
    }

    private By getTabLocator(String section) {
        // Возвращаем локатор для РОДИТЕЛЬСКОГО div
        By locator;
        switch (section) {
            case "Булки":
                locator = bunsTab;
                break;
            case "Соусы":
                locator = saucesTab;
                break;
            case "Начинки":
                locator = fillingsTab;
                break;
            default:
                throw new IllegalArgumentException("Unknown section: " + section);
        }
        return locator;
    }
}