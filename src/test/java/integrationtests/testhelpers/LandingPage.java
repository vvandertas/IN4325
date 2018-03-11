package integrationtests.testhelpers;

import com.vdtas.helpers.Routes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

/**
 * @author vvandertas
 */
public class LandingPage extends BasePage {
    public LandingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="instructions")
    private WebElement instructionsDiv;

    @FindBy(id="start")
    private WebElement startButton;

    public void open() {
        this.get(Routes.LANDING);
    }

    public void clickStartButton() {
        startButton.click();
    }

    public void validateInstructions() {
        assertTrue(instructionsDiv.isDisplayed());
    }
}
