package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
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

    @Test
    public void testNavigateToBuns() {
        mainPage.navigateToSection("Булки");
        Assert.assertTrue(mainPage.isSectionActive("Булки"), "Вкладка 'Булки' не стала активной");
    }

    @Test
    public void testNavigateToSauces() {
        mainPage.navigateToSection("Соусы");
        Assert.assertTrue(mainPage.isSectionActive("Соусы"), "Вкладка 'Соусы' не стала активной");
    }

    @Test
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