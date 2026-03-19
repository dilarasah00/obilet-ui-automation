package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }


    public WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }



    public List<WebElement> waitForElementsVisible(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }


    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void clickWithBy(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }


    public void type(WebElement element, String text) {
        waitForElementVisible(element);
        new Actions(driver).moveToElement(element).click().sendKeys(text).perform();
    }


    public String getText(WebElement element) {
        return waitForElementVisible(element).getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /*public void scrollToElement(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public void clickWithJS(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "var evObj = document.createEvent('MouseEvents');" +
                        "evObj.initEvent('click', true, true);" +
                        "arguments[0].dispatchEvent(evObj);", element);
    }*/
}