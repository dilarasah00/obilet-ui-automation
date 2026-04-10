package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import java.time.LocalDate;

import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.LogHelper;

public class BusSearchPage extends BasePage{

    public BusSearchPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "origin-input")
    private WebElement originInput;

    @FindBy(id = "destination-input")
    private WebElement destinationInput;

    @FindBy(id = "tomorrow")
    private WebElement tomorrowDateButton;

    @FindBy(id = "search-button")
    private WebElement searchButton;

    @FindBy(css = "div.departure.group")
    private WebElement calendar;


    public void enterOrigin(String originLocation) {
        LogHelper.info("Entering origin: " + originLocation);
        type(originInput, originLocation);

        By originFirstItem = By.cssSelector("ob-select#origin div.results ul li.item span.location");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(originFirstItem, originLocation));

        originInput.sendKeys(Keys.ENTER);
    }

    public void enterDestination(String destinationLocation){
        LogHelper.info("Entering destination: " + destinationLocation);
        type(destinationInput, destinationLocation);

        By destFirstItem = By.cssSelector("ob-select#destination div.results ul li.item span.location");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(destFirstItem, destinationLocation));

        destinationInput.sendKeys(Keys.ENTER);

    }

   public void selectDateByValue(String dateValue) {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = LocalDate.parse(dateValue);
        LocalDate finalDateToSelect;

        LogHelper.info("Opening calendar...");
        click(calendar);

       if(targetDate.isBefore(currentDate)){
           finalDateToSelect = currentDate.plusDays(3);
            LogHelper.warn("Target date is in the past. Adjusted to default: " + finalDateToSelect);
       } else {
           finalDateToSelect = targetDate;
       }

       String xpath = String.format("//button[@data-date='%s']", finalDateToSelect);
       By byForDate = By.xpath(xpath);
       try {
           LogHelper.info("Selecting journey date: " + finalDateToSelect);
           clickWithBy(byForDate);
       } catch (NoSuchElementException e) {
           LogHelper.error("The specified date could not be found: " + finalDateToSelect + " | Original requested: " + dateValue);
       }
       waitForElementToDisappear(byForDate);

   }

    public void clickSearch(){
        LogHelper.info("Clicking the search button to list journeys.");
        click(searchButton);
    }
}