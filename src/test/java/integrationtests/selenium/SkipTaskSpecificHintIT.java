package integrationtests.selenium;

import com.vdtas.models.participants.ParticipantType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author vvandertas
 */
public class SkipTaskSpecificHintIT extends SkipTaskIT {

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
}
