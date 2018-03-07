package com.vdtas.daos;

import com.vdtas.models.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
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

    @SqlUpdate("UPDATE users SET task_id=:taskId WHERE id=:userId;")
    void updateTaskId(@Bind("userId") UUID userId, @Bind("taskId") int taskId);
}