package integrationtests.testhelpers;

import com.vdtas.helpers.Routes;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author vvandertas
 */
public class QuestionnairePage extends BasePage {

    public QuestionnairePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        get(Routes.QUESTIONNAIRE);
    }


}
