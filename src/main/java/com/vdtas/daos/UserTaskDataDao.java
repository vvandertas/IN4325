package com.vdtas.daos;

import com.vdtas.models.UserTaskData;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
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
     * @return
     */
    @SqlUpdate("UPDATE user_task_data SET query_count = query_count +1 WHERE user_id=:userId and task_id=:taskId")
    boolean incrementQueryCount(@Bind("userId") UUID userId, @Bind("taskId") int taskId);


    /**
     * Increment attempts
     *
     * @param userId user id
     * @param taskId task id
     * @return
     */
    @SqlUpdate("UPDATE user_task_data SET  attempts = attempts +1 where user_id=:userId and task_id=:taskId")
    boolean incrementAttempts(@Bind("userId") UUID userId, @Bind("taskId") int taskId);


    /**
     * Insert new userTaskData entry
     *
     * @param userTaskData
     * @return
     */
    @SqlUpdate("INSERT INTO user_task_data (user_id, task_id) VALUES(:userId, :taskId)")
    boolean insert(@BindBean UserTaskData userTaskData);

    @SqlQuery("SELECT * FROM user_task_data WHERE user_id=:userId AND task_id = :taskId")
    @RegisterBeanMapper(UserTaskData.class)
    UserTaskData findByUserAndTask(@Bind("userId") UUID userId, @Bind("taskId") int taskId);

}
