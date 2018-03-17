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
abstract class SkipTaskIT extends SeleniumTest {
    protected ExperimentsPage experimentsPage;

    @Before
    public void setup() {
        // initiate experimentsPage
        experimentsPage = new ExperimentsPage(driver);
    }

    /**
     * Start the experiment for a user of type @participant type,
     * submit a test query to make sure we start with search results shown
     * and click the skip question link.
     *
     * @param participantType participant type for this test
     */
    protected void initiateTest(ParticipantType participantType) {
        // Load the experiments page for a participant of type participantType
        experimentsPage.open(participantType.toString());

        // First make sure we have search results
        experimentsPage.submitTestQuery();

        // Skip the task
        experimentsPage.skipQuestion();
    }


    /**
     * Validate the question that is shown after skipping the first question
     */
    protected void validateQuestion() {
        String question = experimentsPage.findQuestion();
        assertNotNull(question);
        assertEquals("expecting the question of task 2", "This is another test question", question);
    }

    /**
     * Assert that all elements relating to search results are hidden and the no results section is shown
     */
    protected void validateHidingSearchResults() {
        // Make sure the noresults tag is displayed
        assertTrue(experimentsPage.noresultsIsDisplayed());

        // make sure no search results are displayed
        assertEquals(0, experimentsPage.countDisplayedSearchResults());

        // and no paging elements are displayed
        assertEquals(0, experimentsPage.countDisplayedPaging());
    }
}
