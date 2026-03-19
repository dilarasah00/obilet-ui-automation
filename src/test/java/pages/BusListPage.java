package pages;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;

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

            System.out.println("\n--- Checking Journey No: " + count + " ---");
            System.out.println("COMPANY     -> Expected: [" + expectedCompany + "] | Actual: [" + actualCompany + "] -> Match: " + companyMatch);
            System.out.println("SEAT TYPE   -> Expected: [" + expectedType + "] | Actual: [" + actualType + "] -> Match: " + typeMatch);
            System.out.println("ORIGIN      -> Expected: [" + expectedOrigin + "] | Actual: [" + actualOrigin + "] -> Match: " + originMatch);
            System.out.println("DESTINATION -> Expected: [" + expectedDestination + "] | Actual: [" + actualDestination + "] -> Match: " + destinationMatch);

            if (!(companyMatch && typeMatch && originMatch && destinationMatch)) {
                System.err.println("!!! ERROR: This journey does not match the filters. Stopping test.");
                return false;
            }
            count++;
        }
        System.out.println("\n✅ SUCCESS: All journeys match the selected filters!");
        return true;
    }
    public void closePopupIfPresent(){
        try {/*
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.visibilityOf(adPopup));*/
            waitForElementVisible(adPopup);
            click(closePopupButton);
        }
        catch (TimeoutException | NoSuchElementException e){
            System.out.println("Info: Ad popup did not appear, continuing test.");
        }
    }
}