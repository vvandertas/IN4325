package com.vdtas.daos;

import com.vdtas.models.ClickCapture;
import com.vdtas.models.UserTaskData;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.UUID;

/**
 * @author vvandertas
 */
public interface UserTaskDataDao {

    /**
     * Increment query count
     *
     * @param userId user id
     * @param taskId task id
     * @return true on success
     */
    @SqlUpdate("UPDATE user_task_data SET query_count = query_count +1 WHERE user_id=:userId AND task_id=:taskId;")
    boolean incrementQueryCount(UUID userId, int taskId);


    /**
     * Increment attempts
     *
     * @param userId user id
     * @param taskId task id
     */
    @SqlUpdate("UPDATE user_task_data SET  attempts = attempts +1 WHERE user_id=:userId AND task_id=:taskId;")
    void incrementAttempts(UUID userId, int taskId);


    /**
     * Insert new userTaskData entry
     *
     * @param userTaskData the object to insert
     * @return true on success
     */
    @SqlUpdate("INSERT INTO user_task_data (user_id, task_id) VALUES(:userId, :taskId);")
    void insert(@BindBean UserTaskData userTaskData);

    /**
     * Find UserTaskData through user id and task id
     *
     * @param userId user id
     * @param taskId task id
     */
    @SqlQuery("SELECT * FROM user_task_data WHERE user_id=:userId AND task_id = :taskId;")
    @RegisterBeanMapper(UserTaskData.class)
    UserTaskData findByUserAndTask(UUID userId, int taskId);

    /**
     * Register a correct answer
     *
     * @param userId user id
     * @param taskId task id
     */
    @SqlUpdate("UPDATE user_task_data SET answer_found = TRUE, finished_at = NOW() WHERE user_id = :userId AND task_id = :taskId;")
    void registerEndOfTask(UUID userId, int taskId);


    /**
     * Register a link click for a task for a specific user
     * @param clickCapture wrapper containing all info needed to log a link click
     */
    @SqlUpdate("INSERT INTO visited_urls (user_id, task_id, url) VALUES(:userId, :taskId, :url);")
    void captureUrlClick(@BindBean ClickCapture clickCapture);

}
