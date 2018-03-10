package integrationtests.selenium;

import static org.junit.Assert.*;

import com.vdtas.models.participants.ParticipantType;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import integrationtests.testhelpers.ExperimentsPage;
import integrationtests.testhelpers.SeleniumTest;

/**
 * @author vvandertas
 */
public class ExperimentTest extends SeleniumTest {

    ExperimentsPage experimentsPage;

    @Before
    public void setup() {
        // Go to the start of the experiment
        experimentsPage = new ExperimentsPage(driver);
        experimentsPage.open(ParticipantType.GENERICHINT.toString());
    }

    @Test
    public void searchResults() {
        // TODO: Make sure the first hint and question are shown

        // Submit the test query
        experimentsPage.submitTestQuery();

        // Make sure the noresults tag is not displayed
        assertFalse(experimentsPage.noresultsIsDisplayed());

        // Validate paging is present twice
        assertEquals(2, experimentsPage.countDisplayedPaging());

        // Validate we have 20 results displayed
       assertEquals(20, experimentsPage.countDisplayedSearchResults());
    }


    /**
     * After a first search request skip task.
     *
     * Expecting next hint to be displayed and the search results to be removed.
     */
    @Test
    public void skipTask() {
        // TODO: Make sure we start at task 1

        // First make sure we have search results
        experimentsPage.submitTestQuery();

        // Skip the task
        experimentsPage.skipQuestion();

        // Make sure the noresults tag is displayed
        assertTrue(experimentsPage.noresultsIsDisplayed());

        // also check the experiment elements
        driver.findElement(By.id("question"));

    }

}
