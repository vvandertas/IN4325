package integrationtests.testhelpers;

import com.vdtas.helpers.Routes;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @author vvandertas
 */
public class QuestionnairePage extends BasePage {

    public QuestionnairePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(className="form-group")
    private List<WebElement> formGroups;

    @FindBy(id="likeabilityQuestion")
    private WebElement likeabilityQuestionDiv;

    @FindBy(css=".likeabilityRow label")
    private List<WebElement> likeabilityButtons;

    @FindBy(id="difficultyQuestion")
    private WebElement difficultyQuestionDiv;

    @FindBy(css=".difficultyRow label")
    private List<WebElement> difficultyButtons;

    @FindBy(id="usefulnessQuestion")
    private WebElement hintsQuestionDiv;

    @FindBy(css=".usefulnessRow label")
    private List<WebElement> usefulnessButtons;

    @FindBy(id="questionnaire")
    private WebElement form;

    public void open() {
        get(Routes.QUESTIONNAIRE);
    }

    public boolean hintsQuestionIsDisplayed() {
        return hintsQuestionDiv == null;
    }

    public int questionsDisplayed() {
        int count =0;
        for (WebElement group : formGroups) {
            if (group.isDisplayed()) {
                count++;
            }
        }

        return count;
    }

    public void populateAnswers() {
        likeabilityButtons.get(2).click();
        difficultyButtons.get(3).click();
        if(hintsQuestionDiv.isDisplayed()) {
            usefulnessButtons.get(4).click();
        }
    }

    public void submitForm() {
        form.submit();
        waitForAjax();
    }

}
