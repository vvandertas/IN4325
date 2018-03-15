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

    @FindBy(css="input[type='text]")
    private List<WebElement> inputFields;

    @FindBy(id="questionnaire")
    private WebElement form;

    public void open() {
        get(Routes.QUESTIONNAIRE);
    }

    public void populateAnswers() {
        inputFields.forEach(field -> {
            field.sendKeys("This is an answer");
        });
    }

    public void submitForm() {
        form.submit();
    }

}
