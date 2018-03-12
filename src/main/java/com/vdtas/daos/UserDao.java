package com.vdtas.daos;

import com.vdtas.models.User;
import com.vdtas.models.participants.Participant;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author vvandertas
 */
public interface UserDao {

    /**
     * Insert new user
     *
     * @param id
     * @param participantType
     */
    @SqlQuery("INSERT INTO users(id, participant_type) VALUES (:id, :participantType) RETURNING *;")
    @RegisterBeanMapper(User.class)
    User insert(@Bind("id") UUID id, @Bind("participantType") String participantType);

    /**
     * Find user by id
     *
     * @param id user id
     * @return User or null
     */
    @SqlQuery("SELECT * FROM users where id=:id;")
    @RegisterBeanMapper(User.class)
    User findById(@Bind("id") UUID id);


    /**
     * Find all users
     *
     * @return List of users ordered by participant type
     */
    @SqlQuery("SELECT * FROM users ORDER BY participant_type;")
    @RegisterBeanMapper(User.class)
    List<User> listUsers();


    /**
     * Update the current task id for a user
     *
     * @param userId the user's id
     * @param taskId the new task id
     */
    @SqlUpdate("UPDATE users SET current_task_id=:taskId WHERE id=:userId;")
    void updateTaskId(@Bind("userId") UUID userId, @Bind("taskId") int taskId);


    /**
     * Find the current number of participants for each participant count
     *
     * @return a mapping from participantType to count
     */
    @SqlQuery("SELECT COUNT(u.participant_type) FROM users u GROUP BY participant_type;")
    @RegisterBeanMapper(Participant.class)
    Map<String, Integer> getParticipantCounts();

    /**
     * Updated the finished_at time for a user
     *
     * @param userId the user's id
     */
    @SqlUpdate("UPDATE users SET finished_at = NOW() WHERE id = :userId;")
    void setFinished(@Bind("userId") UUID userId);
}