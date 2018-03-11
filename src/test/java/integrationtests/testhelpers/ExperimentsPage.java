package integrationtests.testhelpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author vvandertas
 */
public class ExperimentsPage extends BasePage {

    @FindBy(id="question")
    private WebElement question;

    @FindBy(id="hints-div")
    private WebElement hintsDiv;

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

    @FindBy(css=".webPages a")
    private List<WebElement> searchResultLinks;


    public ExperimentsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open(String participantType) {
        this.get(Routes.EXPERIMENT +"?forceType=" + participantType);
        waitUntilQuestionIsVisible();
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

    public String clickFirstLink() {
        WebElement firstLink = searchResultLinks.get(0);
        firstLink.click();

        return firstLink.getAttribute("href");

    }

    public String findQuestion() {
        return question.getText();
    }

    public String findHints() {
        return hints.getText();
    }

    public boolean hintIsDisplayed() {
        return hintsDiv.isDisplayed();
    }

    public void waitUntilQuestionIsVisible() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(question));
    }
}