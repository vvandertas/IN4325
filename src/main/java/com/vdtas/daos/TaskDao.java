package com.vdtas.daos;

import com.vdtas.models.Keyword;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import com.vdtas.models.Task;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

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
    Task findById(@Bind("id") int id);

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
     * Find all keywords for a task
     *
     * @param taskId the id of the task
     * @return list of keywords
     */
    @SqlQuery("SELECT keyword FROM keywords WHERE task_id = :taskId")
    List<String> findKeywordsForTask(@Bind("taskId") int taskId);

}
