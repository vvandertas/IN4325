package com.vdtas.daos;

import com.vdtas.models.Hint;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * @author vvandertas
 */
public interface HintDao {
    /**
     * Insert new hint
     *
     * @param hint
     */
    @SqlUpdate("INSERT INTO hints(task_id, hint) VALUES(:taskId, :hint)")
    void insertBean(@BindBean Hint hint);

    @SqlUpdate("INSERT INTO hints(task_id, hint) VALUES(:taskId, :hint)")
    void insert(int taskId, String hint);


    /**
     * Find hint by id
     *
     * @param id hint id
     * @return Hint or null
     */
    @SqlQuery("SELECT * FROM hints WHERE id=:id")
    @RegisterBeanMapper(Hint.class)
    Hint findById(int id);


    /**
     * Find all hints for a task
     * @param taskId task id
     * @return List of hints
     */
    @SqlQuery("SELECT * FROM hints WHERE task_id=:taskId ORDER BY id ASC")
    @RegisterBeanMapper(Hint.class)
    List<Hint> findByTaskId(int taskId);
}
