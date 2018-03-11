package integrationtests.selenium;

import com.vdtas.models.participants.ParticipantType;
import integrationtests.testhelpers.ExperimentsPage;
import integrationtests.testhelpers.QuestionnairePage;
import integrationtests.testhelpers.SeleniumTest;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vvandertas
 */
public class QuestionnaireTest extends SeleniumTest{

    private ExperimentsPage ePage;
    private QuestionnairePage qPage;

    @Before
    public void setup() {
        qPage = new QuestionnairePage(driver);
        ePage = new ExperimentsPage(driver);
    }

    @Test
    public void loadQuestionnaire() {
        // Make sure we have a user
        ePage.open(ParticipantType.NOHINT.toString());

        qPage.open();
    }


}
