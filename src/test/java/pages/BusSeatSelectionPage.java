package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.LogHelper;
import java.util.List;
import java.util.NoSuchElementException;

public class BusSeatSelectionPage extends BasePage {

    public BusSeatSelectionPage(WebDriver driver) {
        super(driver);
    }
    private String selectedSeatAvailability = "all";


    @FindBy(css = "li.journey.journey-domestic.item:not(.full)")
    private List<WebElement> availableJourneys;


    public void selectFirstAvailableJourney(){
        LogHelper.info("Selecting the first available journey from the list.");
        List<WebElement> journeys = waitForElementsVisible(availableJourneys);
        journeys.getFirst().click();
    }

    public void selectSeatFlexible(String targetGender) {
        LogHelper.info("Looking for a suitable seat for gender: " + targetGender);
        By allSeatLocator = By.cssSelector("a.available.active.ins-init-condition-tracking");
        List <WebElement> seats = waitForElementsLocated(allSeatLocator);

        WebElement seatToSelect = null;
        for (WebElement seat: seats){
            String availability = seat.getAttribute("obilet:available");
            LogHelper.debug("Checking seat with availability: " + availability);

            if(availability.equalsIgnoreCase(targetGender) || availability.equalsIgnoreCase("all")){
                seatToSelect = seat;
                this.selectedSeatAvailability = availability;
                break;
            }
        }
        if (seatToSelect == null && !seats.isEmpty()){
            LogHelper.warn("No specific seat found for " + targetGender + ". Selecting the first available seat as a fallback.");
            seatToSelect = seats.getFirst();
        }

        if(seatToSelect != null){
            LogHelper.info("Clicking on the selected seat. Seat availability type: " + selectedSeatAvailability);
            seatToSelect.click();
        }else {
            LogHelper.error("No available seats found on this bus!");
            throw new NoSuchElementException("Seat not found");
        }
    }

    public void handleGenderFlexible(String targetGender) {
        By genderPopup = By.cssSelector("div.drop.genders");

        try {
            LogHelper.info("Waiting for gender selection popup...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(genderPopup));

            String genderWord = selectedSeatAvailability.toLowerCase();
            if (genderWord.equals("f") || genderWord.equals("female")) {
                genderWord = "female";
            } else if (genderWord.equals("m") || genderWord.equals("male")) {
                genderWord = "male";
            } else {
                genderWord = targetGender.toLowerCase().startsWith("f") ? "female" : "male";
            }

            LogHelper.info("Handling gender popup with selection: " + genderWord);
            By targetBtnLocator = By.cssSelector("button." + genderWord);
            clickWithBy(targetBtnLocator);

        } catch (Exception e) {
            LogHelper.warn("Gender popup could not be handled or did not appear: " + e.getMessage());        }
    }

    public void clickConfirmButton(){
        LogHelper.info("Clicking confirm button to proceed to checkout.");
        By activeSelection = By.cssSelector("li.journey.open span.ready");
        clickWithBy(activeSelection);
    }

    public void closePopUp(){
        By popup = By.cssSelector(".ins-preview-wrapper");
        By closeBtn = By.cssSelector(".ins-element-close-button");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(popup));
            clickWithBy(closeBtn);
            LogHelper.info("Campaign popup closed.");
        } catch (TimeoutException e) {
            LogHelper.debug("No campaign popup appeared.");
        }
    }

    public String checkCurrentUrl(){
        LogHelper.info("Checking current URL to verify redirection to checkout page.");
        By form = By.id("form");
        wait.until(ExpectedConditions.visibilityOfElementLocated(form));
        return getCurrentUrl();
    }

    public void closePackageOption(){
        LogHelper.info("Closing additional package options if present.");
        By popUp = By.className("bf-non-selected");
        clickWithBy(popUp);
    }
}