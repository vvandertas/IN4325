package integrationtests.selenium;

import com.vdtas.daos.UserDao;
import com.vdtas.daos.UserTaskDataDao;
import com.vdtas.models.ClickCapture;
import com.vdtas.models.User;
import com.vdtas.models.participants.ParticipantType;
import org.jdbi.v3.core.Jdbi;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author vvandertas
 */
public class CaptureClickIT extends ExperimentIT {

    @Test
    public void captureClicksTest() {
        initiateExperiment(ParticipantType.GENERICHINT);

        // Submit the test query
        experimentsPage.submitTestQuery();

        // Click first link
        String href = experimentsPage.clickFirstLink();

        // Validate the registered link click in the db
        User user = app.require(Jdbi.class).withExtension(UserDao.class, dao -> {
            assertEquals("Expecting just 1 user", 1, dao.listUsers().size());
            return dao.listUsers().get(0);
        });

        app.require(Jdbi.class).useExtension(UserTaskDataDao.class, taskDao -> {
            List<String> visitedPages = taskDao.findAllPagesForUserTask(user.getId(), user.getCurrentTaskId());
            assertNotNull(visitedPages);
            assertEquals(1, visitedPages.size());
            assertEquals(href, visitedPages.get(0));
        });

        // Ensure we are still on the same page
        // Make sure the noresults tag is not displayed
        assertFalse(experimentsPage.noresultsIsDisplayed());

        // Validate paging is present twice
        assertEquals(2, experimentsPage.countDisplayedPaging());

        // Validate we have 20 results displayed
        assertEquals(20, experimentsPage.countDisplayedSearchResults());
    }
}
