package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.MainPage;
import utils.DriverFactory;

public class ConstructorTest {

    private WebDriver driver;
    private MainPage mainPage;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.createDriver(browser);
        mainPage = new MainPage(driver);
    }

    @Test(description = "Проверка переключения на вкладку 'Булки'")
    public void testNavigateToBuns() {
        // Переключимся на Соусы
        mainPage.navigateToSection("Соусы");
        Assert.assertTrue(mainPage.isSectionActive("Соусы"), "Вкладка 'Соусы' не стала активной");

        // Вернёмся обратно к Булкам
        mainPage.navigateToSection("Булки");
        Assert.assertTrue(mainPage.isSectionActive("Булки"), "Вкладка 'Булки' не стала активной после переключения");
    }

    @Test(description = "Проверка переключения на вкладку 'Соусы'")
    @DisplayName("Переключение на Соусы")
    public void testNavigateToSauces() {
        mainPage.navigateToSection("Соусы");
        Assert.assertTrue(mainPage.isSectionActive("Соусы"), "Вкладка 'Соусы' не стала активной");
    }

    @Test(description = "Проверка переключения на вкладку 'Начинки'")
    @DisplayName("Переключение на Начинки")
    public void testNavigateToFillings() {
        mainPage.navigateToSection("Начинки");
        Assert.assertTrue(mainPage.isSectionActive("Начинки"), "Вкладка 'Начинки' не стала активной");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}