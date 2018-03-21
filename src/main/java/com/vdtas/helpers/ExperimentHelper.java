package com.vdtas.helpers;

import com.vdtas.daos.HintDao;
import com.vdtas.daos.UserTaskDataDao;
import com.vdtas.models.*;
import com.vdtas.models.participants.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.vdtas.models.Task.GENERIC_HINTS;


/**
 * @author vvandertas
 */
public class ExperimentHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExperimentHelper.class);

    private UserTaskDataDao dataDao;
    protected HintDao hintDao;

    @Inject
    public ExperimentHelper(UserTaskDataDao dataDao, HintDao hintDao) {
        this.dataDao = dataDao;
        this.hintDao = hintDao;
    }

    /**
     * Increment query count for user's current task
     * @param user
     */
    public void incrementQueryCount(User user) {
        logger.debug("Incrementing query count for user: {}, task {}", user.getId(), user.getCurrentTaskId());
        UserTaskData taskData = dataDao.findByUserAndTask(user.getId(), user.getCurrentTaskId());
        if(user.getCurrentTaskId() > 0) {
            if(taskData == null) {
                dataDao.insert(new UserTaskData(user.getId(), user.getCurrentTaskId()));
            }

            dataDao.incrementQueryCount(user.getId(), user.getCurrentTaskId());
        }
    }

    public void incrementAttempts(User user) {
        if(user.getCurrentTaskId() > 0) {
            dataDao.incrementAttempts(user.getId(), user.getCurrentTaskId());
        }

    }

    /**
     * Retrieve the correct hint from the current task for a user session.
     *
     * @param user current user
     * @param task current task for @user
     * @return
     */
    public List<Hint> findCurrentHints(User user, Task task) {
        ParticipantType participantType = user.getParticipantType();

        List<Hint> hints = new ArrayList<>();

        if (task != null) {
            // Find the correct hint to show
            switch (participantType) {
                case GENERICHINT:
                    GENERIC_HINTS.forEach(hint -> {
                        hints.add(new Hint(task.getId(), hint, HintType.GENERIC));
                    });
                    break;
                case SPECIFICHINT:
                    // Find all specific hints for the task
                    hints.addAll(hintDao.findByTaskIdAndType(task.getId(), HintType.SPECIFIC.toString()));
                    break;
                case ADVERSARIALHINT:
                    // Find all adversarial hints for the task
                    hints.addAll(hintDao.findByTaskIdAndType(task.getId(), HintType.ADVERSARIAL.toString()));
                    break;
                default:
                    break;
            }
        }

        return hints;
    }

    public void captureClick(ClickCapture clickCapture) {
        dataDao.captureUrlClick(clickCapture);
    }


    public void createUserTaskData(User user) {
        dataDao.insert(new UserTaskData(user.getId(), user.getCurrentTaskId()));
    }

    public void createUserTaskData(UUID userId, int taskId) {
        dataDao.insert(new UserTaskData(userId, taskId));
    }

    public void logAnswerFound(User user) {
        dataDao.registerEndOfTask(user.getId(), user.getCurrentTaskId());
    }
}
