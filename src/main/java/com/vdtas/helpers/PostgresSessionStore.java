package com.vdtas.helpers;

import com.google.inject.Inject;
import com.vdtas.daos.SessionDao;
import org.jdbi.v3.core.Jdbi;
import org.jooby.Session;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @author vvandertas
 */
public class PostgresSessionStore implements Session.Store {

    private final Jdbi jdbi;

    @Inject
    public PostgresSessionStore(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Session get(Session.Builder builder) {
        return jdbi.withHandle((handle) -> handle.createQuery("UPDATE sessions SET accessed_at = NOW() WHERE id = :id AND expiry_at > NOW() RETURNING *")
                .bind("id", builder.sessionId())
                .mapToMap().findFirst()
                // manually set the output as we cannot simply serialize to a Session object
                .map((rs) -> {
                    builder.savedAt(((Timestamp) rs.get("saved_at")).getTime());
                    builder.accessedAt(((Timestamp) rs.get("accessed_at")).getTime());
                    builder.createdAt(((Timestamp) rs.get("created_at")).getTime());
                    builder.set((Map<String, String>) rs.get("attributes"));
                    return builder.build();
                }).orElse(null));
    }

    @Override
    public void save(Session session) {
        jdbi.withExtension(SessionDao.class, (dao) -> dao.upsert(session.id(), session.attributes(), new Date(session.savedAt()), new Date(session.accessedAt())));
    }

    @Override
    public void create(Session session) {
        save(session);
    }

    @Override
    public void delete(String id) {
        jdbi.withExtension(SessionDao.class, (dao) -> dao.delete(id));
    }
}
