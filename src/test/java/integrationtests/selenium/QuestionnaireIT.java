package integrationtests.selenium;

import integrationtests.testhelpers.ExperimentsPage;
import integrationtests.testhelpers.QuestionnairePage;
import integrationtests.testhelpers.SeleniumTest;
import org.junit.Before;

/**
 * @author vvandertas
 */
abstract class QuestionnaireIT extends SeleniumTest {
    protected ExperimentsPage ePage;
    protected QuestionnairePage qPage;

    @Before
    public void setup() {
        qPage = new QuestionnairePage(driver);
        ePage = new ExperimentsPage(driver);
    }
}
