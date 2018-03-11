package integrationtests.selenium;

import com.vdtas.models.participants.ParticipantType;
import integrationtests.testhelpers.ExperimentsPage;
import integrationtests.testhelpers.LandingPage;
import integrationtests.testhelpers.SeleniumTest;
import org.junit.Before;
import org.junit.Test;

import static com.vdtas.models.Task.GENERIC_HINT;
import static org.junit.Assert.*;

/**
 * @author vvandertas
 */
public class SkipTaskTest extends SeleniumTest {
    private ExperimentsPage experimentsPage;

    @Before
    public void setup() {
        // initiate experimentsPage
        experimentsPage = new ExperimentsPage(driver);
    }

    /**
     * After a first search request skip task.
     *
     * Expecting next hint to be displayed and the search results to be removed.
     */
    @Test
    public void skipTask_genericHint() {
        initiateTest(ParticipantType.GENERICHINT);

        // Make sure the search results, paging are hidden and noresults is shown
        validateHidingSearchResults();

        // also check the experiment elements
        validateQuestion();

        // Make sure the hints element is displayed
        assertTrue(experimentsPage.hintIsDisplayed());

        // and validate its content
        String hints = experimentsPage.findHints();
        assertNotNull(hints);
        assertEquals(GENERIC_HINT, hints);
    }

    /**
     * After a first search request skip task.
     *
     * Expecting next hint to be displayed and the search results to be removed.
     */
    @Test
    public void skipTask_specificHint() {
        initiateTest(ParticipantType.SPECIFICHINT);

        // Make sure the search results, paging are hidden and noresults is shown
        validateHidingSearchResults();

        // also check the experiment elements
        validateQuestion();

        // Make sure the hints element is displayed
        assertTrue(experimentsPage.hintIsDisplayed());

        // and validate its content
        String hints = experimentsPage.findHints();
        assertNotNull(hints);
        assertEquals("A lonesome hint for this task", hints);
    }

    /**
     * After a first search request skip task.
     *
     * Expecting next hint to be displayed and the search results to be removed.
     */
    @Test
    public void skipTask_noHint() {
        initiateTest(ParticipantType.NOHINT);

        // Make sure the search results, paging are hidden and noresults is shown
        validateHidingSearchResults();

        // also check the experiment elements
        validateQuestion();

        assertFalse("Expecting hints element to be hidden", experimentsPage.hintIsDisplayed());
    }

    /**
     * Start the experiment for a user of type @participant type,
     * submit a test query to make sure we start with search results shown
     * and click the skip question link.
     *
     * @param participantType participant type for this test
     */
    private void initiateTest(ParticipantType participantType) {
        // Load the experiments page for a participant of type participantType
        experimentsPage.open(participantType.toString());

        // First make sure we have search results
        experimentsPage.submitTestQuery();

        // Skip the task
        experimentsPage.skipQuestion();
    }


    /**
     * Assert that all elements relating to search results are hidden and the no results section is shown
     */
    private void validateHidingSearchResults() {
        // Make sure the noresults tag is displayed
        assertTrue(experimentsPage.noresultsIsDisplayed());

        // make sure no search results are displayed
        assertEquals(0, experimentsPage.countDisplayedSearchResults());

        // and no paging elements are displayed
        assertEquals(0, experimentsPage.countDisplayedPaging());
    }

    /**
     * Validate the question that is shown after skipping the first question
     */
    private void validateQuestion() {
        String question = experimentsPage.findQuestion();
        assertNotNull(question);
        assertEquals("expecting the question of task 2", "This is another test question", question);
    }
}
