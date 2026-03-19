package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class BusSeatSelectionPage extends BasePage {

    public BusSeatSelectionPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.selection.open button span.ready")
    private WebElement confirmButton;


    public void selectFirstAvailableJourney(){

    }


    public void selectFirstAvailableSeat(String targetGender) {

    }

    public void clickConfirmButton(){click(confirmButton);}

    public void closePopUp(){
        By cssSelector = By.cssSelector("div.ob-modal.normal.pop.brandedfare-bus-modal.open.non-standart button.close");
        try {
            clickWithBy(cssSelector);
        }catch (TimeoutException e){}
    }

    public String checkUrl() {
        return getCurrentUrl();
    }
}

