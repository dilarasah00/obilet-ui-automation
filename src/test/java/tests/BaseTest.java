package tests;
import  org.testng.annotations.AfterMethod;
import  org.testng.annotations.BeforeMethod;
import  org.openqa.selenium.WebDriver;
import  utils.DriverFactory;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = DriverFactory.getDriver();
        driver.get("https://www.obilet.com/");
    }
    @AfterMethod
    public void tearDown(){
        DriverFactory.quitDriver();
    }
}