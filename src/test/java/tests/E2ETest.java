package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BusSearchPage;
import pages.BusListPage;



public class E2ETest extends BaseTest {
    @Test
    public void testSearchBus(){
        String targetCompany = "Ali Osman Ulusoy";
        String originTerminal = "Ankara (Aşti) Otogarı";
        String seatType = "2+1";
        String originCity = "Ankara";
        String destinationCity = "Samsun";
        String destinationTerminal = "Samsun Otogarı";
        String date = "2026-03-30";

        BusSearchPage busSearchPage = new BusSearchPage(driver);
        BusListPage busListPage = new BusListPage(driver);


        busSearchPage.enterOrigin(originCity);
        busSearchPage.enterDestination(destinationCity);
        busSearchPage.selectDateByValue(date);
        busSearchPage.clickSearch();
        busListPage.closePopupIfPresent();

        busListPage.openAllFilter();
        busListPage.selectCompany(targetCompany);
        busListPage.selectOrigin(originTerminal);
        busListPage.selectSeatType(seatType);

        Assert.assertTrue(busListPage.isListDisplayed());
        Assert.assertTrue(busListPage.verifyFilteredResults(targetCompany, seatType, originTerminal, destinationTerminal), "Listed journeys does not match the filters.");


    }
}
