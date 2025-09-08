package utils;

import constants.AppConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {
        WebDriver driver;

        System.setProperty("webdriver.chrome.driver", AppConstants.CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(AppConstants.BASE_URL);

        return driver;
    }
}