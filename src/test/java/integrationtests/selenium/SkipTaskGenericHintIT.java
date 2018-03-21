package integrationtests.selenium;

import com.vdtas.models.participants.ParticipantType;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static com.vdtas.helpers.Routes.QUESTIONNAIRE;
import static com.vdtas.models.Task.GENERIC_HINTS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author vvandertas
 */
public class SkipTaskGenericHintIT extends SkipTaskIT {
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
        assertEquals(StringUtils.join(GENERIC_HINTS, "\n"), hints);
    }

    @Test
    public void skipAllTasks() {
        // Load the experiments page for a participant of type participantType
        experimentsPage.open(ParticipantType.GENERICHINT.toString());

        // First make sure we have search results
        experimentsPage.submitTestQuery();

        // Skip first question
        experimentsPage.skipQuestion();

        // as well as the second question
        experimentsPage.skipQuestion();

        // now validate that we move to the questionnaire
        experimentsPage.shouldBeOnPage(QUESTIONNAIRE);
    }
}
