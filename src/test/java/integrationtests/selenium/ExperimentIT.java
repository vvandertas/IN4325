package integrationtests.selenium;

import static com.vdtas.models.Task.GENERIC_HINTS;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

import com.vdtas.models.participants.ParticipantType;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import integrationtests.testhelpers.ExperimentsPage;
import integrationtests.testhelpers.SeleniumTest;

/**
 * @author vvandertas
 */
public class ExperimentIT extends SeleniumTest {

    protected ExperimentsPage experimentsPage;

    @Before
    public void setup() {
        // provide the experimentsPage helper
        experimentsPage = new ExperimentsPage(driver);
    }

    @Test
    public void initialTaskData_noHint() {
        initiateExperiment(ParticipantType.NOHINT);

        validateInitialQuestion();

        assertFalse("Expecting hints element to be hidden", experimentsPage.hintIsDisplayed());

        // refresh page and validate we still have the same question
        experimentsPage.open(ParticipantType.NOHINT.toString());

        validateInitialQuestion();
    }

    @Test
    public void initialTaskData_specificHint() {
        initiateExperiment(ParticipantType.SPECIFICHINT);

        validateInitialQuestion();

        // Make sure the hints element is displayed
        assertTrue(experimentsPage.hintIsDisplayed());

        // and validate its content
        String hints = experimentsPage.findHints();
        assertNotNull(hints);
        assertEquals("This is a very special first hint\n" +
                "Together with another hint for this task", hints);
    }

    @Test
    public void initialTaskData_genericHint() {
        initiateExperiment(ParticipantType.GENERICHINT);

        validateInitialQuestion();

        // Make sure the hints element is displayed
        assertTrue(experimentsPage.hintIsDisplayed());

        // and validate its content
        String hints = experimentsPage.findHints();
        assertNotNull(hints);
        assertEquals(StringUtils.join(GENERIC_HINTS, "\n"), hints);
    }

    @Test
    public void searchResults() {
        experimentsPage.open(ParticipantType.GENERICHINT.toString());

        // Submit the test query
        experimentsPage.submitTestQuery();

        // Make sure the noresults tag is not displayed
        assertFalse(experimentsPage.noresultsIsDisplayed());

        // Validate paging is present twice
        assertEquals(2, experimentsPage.countDisplayedPaging());

        // Validate we have 20 results displayed
        assertEquals(20, experimentsPage.countDisplayedSearchResults());
    }


    protected void initiateExperiment(ParticipantType participantType) {
        experimentsPage.open(participantType.toString());
    }

    protected void validateInitialQuestion() {
        String question = experimentsPage.findQuestion();
        assertNotNull(question);
        assertEquals("expecting the question for task 1", "This is a test question", question);
    }

}
