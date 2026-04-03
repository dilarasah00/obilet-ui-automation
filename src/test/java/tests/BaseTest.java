package tests;
import  org.testng.annotations.AfterMethod;
import  org.testng.annotations.BeforeMethod;
import  org.openqa.selenium.WebDriver;
import  utils.DriverFactory;
import utils.LogHelper;
import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp(Method method){
        LogHelper.startTestCase(method.getName());

        driver = DriverFactory.getDriver();

        String url = "https://www.obilet.com/";
        LogHelper.info("Navigating to URL: " + url);
        driver.get(url);
    }
    @AfterMethod
    public void tearDown(Method method){
        LogHelper.info("Finishing the test execution...");
        DriverFactory.quitDriver();

        LogHelper.endTestCase(method.getName());
    }
}