package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

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
        type(originInput, originLocation);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        originInput.sendKeys(Keys.ENTER);
    }

    public void enterDestination(String destinationLocation){
        type(destinationInput, destinationLocation);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        destinationInput.sendKeys(Keys.ENTER);

    }


   public void selectDateByValue(String dateValue) {
       click(calendar);
       String xpath = String.format("//button[@data-date='%s']", dateValue);
       By byForDate = By.xpath(xpath);
       try {
           clickWithBy(byForDate);

       } catch (NoSuchElementException e) {
           System.err.println("The specified date could not be found: " + dateValue);
       }
       try { Thread.sleep(1000); } catch (InterruptedException e) {}

   }

    public void clickSearch(){
        click(searchButton);
    }
}