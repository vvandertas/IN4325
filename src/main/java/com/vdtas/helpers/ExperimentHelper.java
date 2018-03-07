package com.vdtas.helpers;

import com.google.common.collect.ImmutableList;
import com.vdtas.SessionException;
import com.vdtas.daos.HintDao;
import com.vdtas.daos.UserTaskDataDao;
import com.vdtas.models.Hint;
import com.vdtas.models.Task;
import com.vdtas.models.User;
import com.vdtas.models.UserTaskData;
import com.vdtas.models.participants.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.vdtas.models.Task.GENERIC_HINT;


/**
 * @author vvandertas
 */
@Singleton
public class ExperimentHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExperimentHelper.class);

    private static NoHintParticipant noHint = new NoHintParticipant();
    private static GenericHintParticipant genericHint = new GenericHintParticipant();
    private static SpecificHintParticipant specificHint = new SpecificHintParticipant();

    // TODO: Split this up!!

    private UserTaskDataDao dataDao;
    protected HintDao hintDao;

    @Inject
    public ExperimentHelper(UserTaskDataDao dataDao, HintDao hintDao) {
        this.dataDao = dataDao;
        this.hintDao = hintDao;
    }

    //TODO: Calculate on the fly
    public static synchronized ParticipantType selectParticipantType() {
        Participant selectedParticipant = Participant.randomMin(ImmutableList.of(noHint, genericHint, specificHint)).increment();
        return selectedParticipant.getParticipantType();
    }


    /**
     * Increment query count for user's current task
     * @param user
     */
    public void incrementQueryCount(User user) {
        logger.debug("Incrementing query count for user: {}, task {}", user.getId().toString(), user.getCurrentTaskId());
        UserTaskData taskData = dataDao.findByUserAndTask(user.getId(), user.getCurrentTaskId());
        if(user.getCurrentTaskId() > 0) {
            if(taskData == null) {
                dataDao.insert(new UserTaskData(user.getId(), user.getCurrentTaskId()));
            } else {
                dataDao.incrementQueryCount(user.getId(), user.getCurrentTaskId());
            }
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
                case GENERIC_HINT:
                    hints.add(new Hint(0, task.getId(), GENERIC_HINT));
                    break;
                case SPECIFIC_HINT:
                    // Find all hints for task
                    hints.addAll(hintDao.findByTaskId(task.getId()));
                    break;
                default:
                    break;
            }
        }

        return hints;
    }

}
