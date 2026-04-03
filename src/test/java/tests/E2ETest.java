package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BusSearchPage;
import pages.BusListPage;
import pages.BusSeatSelectionPage;
import utils.LogHelper;

public class E2ETest extends BaseTest {
    @Test
    public void testSearchBus(){
        String targetCompany = "Ali Osman Ulusoy";
        String originTerminal = "Ankara (Aşti) Otogarı";
        String seatType = "2+1";
        String originCity = "Ankara";
        String destinationCity = "Samsun";
        String destinationTerminal = "Samsun Otogarı";
        String date = "2026-01-30";
        String gender = "F";

        BusSearchPage busSearchPage = new BusSearchPage(driver);
        BusListPage busListPage = new BusListPage(driver);
        BusSeatSelectionPage busSeatSelectionPage = new BusSeatSelectionPage(driver);

        LogHelper.info("STEP 1: Searching for bus tickets from " + originCity + " to " + destinationCity + " on " + date);
        busSearchPage.enterOrigin(originCity);
        busSearchPage.enterDestination(destinationCity);
        busSearchPage.selectDateByValue(date);
        busSearchPage.clickSearch();

        LogHelper.info("STEP 2: Applying filters for Company: " + targetCompany + " and Seat Type: " + seatType);
        busListPage.closePopupIfPresent();
        busListPage.openAllFilter();
        busListPage.selectCompany(targetCompany);
        busListPage.closePopupIfPresent();
        busListPage.selectOrigin(originTerminal);
        busListPage.selectSeatType(seatType);

        LogHelper.info("STEP 3: Verifying that all listed journeys match the selected filters.");
        Assert.assertTrue(busListPage.isListDisplayed(), "No journeys found!");
        Assert.assertTrue(busListPage.verifyFilteredResults(targetCompany, seatType, originTerminal, destinationTerminal), "Listed journeys does not match the filters.");

        LogHelper.info("STEP 4: Selecting the first available journey and a suitable seat for gender: " + gender);
        busSeatSelectionPage.selectFirstAvailableJourney();
        busSeatSelectionPage.selectSeatFlexible(gender);
        busSeatSelectionPage.handleGenderFlexible(gender);
        busSeatSelectionPage.clickConfirmButton();

        LogHelper.info("STEP 5: Verifying redirection to the checkout page.");
        busSeatSelectionPage.closePackageOption();
        String currentUrl = busSeatSelectionPage.checkCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/odeme/"), "Checkout page not loaded! Current URL: " + currentUrl);

        LogHelper.info("SUCCESS: E2E Bus Booking Flow completed successfully.");
    }
}
