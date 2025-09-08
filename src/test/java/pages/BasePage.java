package pages;

import constants.AppConstants;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.TIMEOUT));
    }

    @Step("Ожидание видимости элемента: {locator}")
    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Ожидание кликабельности элемента: {locator}")
    protected WebElement waitForClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Step("Клик по элементу: {locator}")
    protected void clickElement(By locator) {
        WebElement element = waitForClickableElement(locator);
        element.click();
    }

    @Step("Ввод текста '{text}' в элемент: {locator}")
    protected void sendKeysToElement(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    @Step("Получение текста элемента: {locator}")
    protected String getElementText(By locator) {
        WebElement element = waitForElement(locator);
        return element.getText();
    }

    @Step("Проверка отображения элемента: {locator}")
    protected boolean isElementDisplayed(By locator) {
        try {
            WebElement element = waitForElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Ожидание URL содержит: {urlPart}")
    protected void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    @Step("Получение текущего URL")
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}