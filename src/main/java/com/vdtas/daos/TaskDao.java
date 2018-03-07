package com.vdtas.daos;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import com.vdtas.models.Task;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * @author vvandertas
 */
public interface TaskDao {

    /**
     * Find task by id
     *
     * @param id task id
     * @return Task or null
     */
    @SqlQuery("SELECT * FROM tasks WHERE id=:id")
    @RegisterBeanMapper(Task.class)
    Task findById(int id);

    /**
     * Insert a task and returns the generated PK.
     *
     * @param task Task to insert.
     * @return Primary key.
     */
    @SqlUpdate("INSERT INTO tasks(question, name) VALUES(:question,:name) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    int insert(@BindBean Task task);


    /**
     * Find the
     * @return
     */
    @SqlQuery("SELECT MAX(id) FROM tasks GROUP BY id")
    int findMaxId();

}
