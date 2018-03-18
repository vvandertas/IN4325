package integrationtests.selenium;

import com.vdtas.models.participants.ParticipantType;
import integrationtests.testhelpers.ExperimentsPage;
import integrationtests.testhelpers.SeleniumTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author vvandertas
 */
public class ValidateAnswerIT extends SeleniumTest {

    private ExperimentsPage experimentsPage;

    @Before
    public void setup() {
        // initiate experimentsPage
        experimentsPage = new ExperimentsPage(driver);
    }

    @Test
    public void validateAnswer() {
        // First make sure we have search results
        experimentsPage.open(ParticipantType.GENERICHINT.toString());
        experimentsPage.submitTestQuery();

        // first submit an incorrect answer
        experimentsPage.submitAnswer("This really isn't correct", true);
        assertTrue("Expecting the failure modal",experimentsPage.failureModalVisible());
        experimentsPage.closeFailureModal();

        // then submit a partial answer
        experimentsPage.submitAnswer("The first part of the answer is atlas", true);
        assertTrue("Expecting the failure modal",experimentsPage.failureModalVisible());
        experimentsPage.closeFailureModal();

        // and finally submit the correct answer
        experimentsPage.submitAnswer("Atlas and mercury", false);
        assertFalse("Failure modal should not be present",experimentsPage.failureModalVisible());

        // validate that we have moved to the next task by confirming the search results are no longer displayed
        // Make sure the noresults tag is displayed
        assertTrue(experimentsPage.noresultsIsDisplayed());

        // make sure no search results are displayed
        assertEquals(0, experimentsPage.countDisplayedSearchResults());

        // and no paging elements are displayed
        assertEquals(0, experimentsPage.countDisplayedPaging());

        // Make sure the answer fields have been cleared
        assertTrue(StringUtils.isEmpty(experimentsPage.getTextAnswer()));
    }
}