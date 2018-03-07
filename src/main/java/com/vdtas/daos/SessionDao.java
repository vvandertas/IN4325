package com.vdtas.daos;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Date;
import java.util.Map;

/**
 * @author vvandertas
 */
public interface SessionDao {

    /**
     * @param sessionId  id of the session
     * @param attributes map of attributes
     * @param savedAt    date the session was saved
     * @param accessedAt date the session was accessed
     * @return true on success
     */
    @SqlUpdate("INSERT INTO sessions (id, attributes) VALUES(:id, :attributes) ON CONFLICT(id) DO UPDATE SET attributes = :attributes, saved_at = :savedAt, accessed_at = :accessedAt, expiry_at = NOW() + INTERVAL '30 days'")
    boolean upsert(@Bind("id") String sessionId, @Bind("attributes") Map<String, String> attributes, @Bind("savedAt") Date savedAt, @Bind("accessedAt") Date accessedAt);

    /**
     * @param id session id
     * @return true on success
     */
    @SqlUpdate("DELETE FROM sessions WHERE id = ?")
    boolean delete(String id);
}
