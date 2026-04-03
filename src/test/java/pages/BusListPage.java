package pages;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import utils.LogHelper;

public class BusListPage extends BasePage{

    public BusListPage(WebDriver driver){
        super(driver);
    }


    @FindBy(className = "all")
    private WebElement allFiltersTab;

    @FindBy(className = "partner")
    private WebElement companyMenu;

    @FindBy(css = "div.filter.filter-partner-container ul li")
    private List<WebElement> companyOptions;


    @FindBy(className = "origin")
    private WebElement originMenu;

    @FindBy(css = "div.filter.filter-origin-container ul li")
    private List<WebElement> originOptions;

    @FindBy(className = "type")
    private WebElement seatTypeMenu;

    @FindBy(css = "div.filter.filter-type-container ul li")
    private List<WebElement> seatTypeOptions;


    @FindBy(css = ".journey.journey-domestic.item")
    private List<WebElement> journeyResults;

    @FindBy(css = ".ins-preview-wrapper")
    private WebElement adPopup;

    @FindBy(id = "close-button-1454703513200")
    private WebElement closePopupButton;


    public void openAllFilter(){
        click(allFiltersTab);
    }


    public void selectCompany(String companyName){
        LogHelper.info("Filtering by company: " + companyName);
        click(companyMenu);
        waitForElementsVisible(companyOptions);

        for (WebElement company: companyOptions){
            if(company.getText().contains(companyName)){
                click(company);
                break;
            }
        }
    }

    public void selectOrigin(String originPlace){
        LogHelper.info("Filtering by origin place: " + originPlace);
        click(originMenu);
        waitForElementsVisible(originOptions);

        for (WebElement origin: originOptions){
            if(origin.getText().contains(originPlace)){
                click(origin);
                break;
            }
        }
    }

    public void selectSeatType(String selectedType){
        LogHelper.info("Filtering by seat type: " + selectedType);
        click(seatTypeMenu);
        waitForElementsVisible(seatTypeOptions);

        for (WebElement type: seatTypeOptions){
            if(type.getText().contains(selectedType)){
                click(type);
                break;
            }
        }
    }


    public boolean isListDisplayed(){
        return !waitForElementsVisible(journeyResults).isEmpty();
    }

    public boolean verifyFilteredResults(String expectedCompany, String expectedType, String expectedOrigin, String expectedDestination) {
        LogHelper.info("Starting verification of filtered results...");
        waitForElementsVisible(journeyResults);

        int count = 1;
        for (WebElement journey : journeyResults) {
            String actualCompany = journey.findElement(By.cssSelector("div.partner-logo")).getAttribute("data-name").trim();
            String actualType = journey.findElement(By.className("type")).getText().trim();
            String actualOrigin = journey.findElement(By.cssSelector("span.origin.location")).getText().trim();
            String actualDestination = journey.findElement(By.cssSelector("span.destination.location")).getText().trim();

            boolean companyMatch = actualCompany.toLowerCase().contains(expectedCompany.toLowerCase().trim());
            boolean typeMatch = actualType.contains(expectedType.trim());
            boolean originMatch = actualOrigin.toLowerCase().contains(expectedOrigin.toLowerCase().trim());
            boolean destinationMatch = actualDestination.toLowerCase().contains(expectedDestination.toLowerCase().trim());

            LogHelper.debug("Checking Journey No: " + count + " | Company: " + actualCompany + " | Type: " + actualType);

            if (!(companyMatch && typeMatch && originMatch && destinationMatch)) {
                LogHelper.error("FILTER MISMATCH at Journey No: " + count);
                LogHelper.error("Expected: " + expectedCompany + ", " + expectedType + " | Actual: " + actualCompany + ", " + actualType);
                return false;
            }
            count++;
        }
        LogHelper.info("Successfully verified " + (count-1) + " journeys. All match the filters.");
        return true;
    }
    public void closePopupIfPresent(){
        try {
            waitForElementVisible(adPopup);
            click(closePopupButton);
            LogHelper.info("Ad popup closed.");
        }
        catch (TimeoutException | NoSuchElementException e){
            LogHelper.debug("Ad popup did not appear, skipping.");        }
    }
}