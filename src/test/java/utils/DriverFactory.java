package utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver getDriver(){
        if(driver == null){
            LogHelper.info("Driver instance is null. Creating a new session...");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            LogHelper.info("ChromeDriver has been initialized successfully.");

            driver.manage().window().maximize();
            LogHelper.info("Browser window maximized.");
        }
        return driver;
    }

    public static void quitDriver(){
        if(driver != null){
            LogHelper.info("Quitting the driver and closing all windows.");
            driver.quit();
            driver = null;
        }
        else {
            LogHelper.warn("Attempted to quit driver, but it was already null.");
        }
    }
}