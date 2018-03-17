package integrationtests.selenium;

import com.vdtas.daos.UserDao;
import com.vdtas.models.User;
import com.vdtas.models.participants.ParticipantType;
import org.jdbi.v3.core.Jdbi;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author vvandertas
 */
public class QuestionnaireWithHintsIT extends QuestionnaireIT {

    @Test
    public void loadQuestionnaire_hint() {
        // Make sure we have a user
        ePage.open(ParticipantType.GENERICHINT.toString());

        qPage.open();

        // Make sure all three questions are displayed
        assertEquals(3, qPage.questionsDisplayed());
        qPage.populateAnswers();

        qPage.submitForm();

        //  assert answers
        app.require(Jdbi.class).useExtension(UserDao.class, dao -> {
            assertEquals("Expecting just 1 user", 1, dao.listUsers().size());
            User user = dao.listUsers().get(0);
            Map<String, String> questionnaire = user.getQuestionnaire();
            assertNotNull(questionnaire);

            // assert answers
            String likeability = questionnaire.get("likeability");
            assertNotNull(likeability);
            assertEquals("2", likeability);

            String difficulty = questionnaire.get("difficulty");
            assertNotNull(likeability);
            assertEquals("3", difficulty);

            String usefulness = questionnaire.get("usefulness");
            assertNotNull(usefulness);
            assertEquals("4", usefulness);
        });
    }
}
