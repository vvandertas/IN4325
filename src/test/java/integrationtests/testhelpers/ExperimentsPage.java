package integrationtests.testhelpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @author vvandertas
 */
public class ExperimentsPage extends BasePage {

    @FindBy(id="question")
    private WebElement question;

    @FindBy(id="hints")
    private WebElement hints;

    @FindBy(id="skip")
    private WebElement skipButton;

    @FindBy(id="answer")
    private WebElement answerField;

    @FindBy(id="url")
    private WebElement urlField;


    @FindBy(name="searchQuery")
    private WebElement queryField;

    @FindBy(id="noresults")
    private WebElement noresults;

    @FindBy(className="paging")
    private List<WebElement> pagingElements;

    @FindBy(className="webPages")
    private List<WebElement> searchResults;


    public ExperimentsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open(String participantType) {
        this.get(Routes.EXPERIMENT +"?forceType=" + participantType);
    }

    public void submitTestQuery() {
        queryField.sendKeys("Test search");
        queryField.submit();
        this.waitForAjax();
    }

    public void skipQuestion() {
        skipButton.click();
        this.waitForAjax();
    }

    public boolean noresultsIsDisplayed() {
        return noresults.isDisplayed();
    }

    public int countDisplayedPaging() {
        return (int) pagingElements.stream().filter(WebElement::isDisplayed).count();
    }

    public int countDisplayedSearchResults() {
        return (int) searchResults.stream().filter(WebElement::isDisplayed).count();
    }
}