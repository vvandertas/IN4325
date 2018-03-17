package integrationtests.selenium;

import com.vdtas.models.participants.ParticipantType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author vvandertas
 */
public class SkipTaskNoHintIT extends SkipTaskIT {

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
}
