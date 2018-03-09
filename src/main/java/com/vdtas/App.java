package com.vdtas;

import com.vdtas.controllers.BingSearch;
import com.vdtas.controllers.ExperimentController;
import com.vdtas.daos.*;
import com.vdtas.helpers.PostgresSessionStore;
import com.vdtas.models.User;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jooby.Jooby;
import org.jooby.flyway.Flywaydb;
import org.jooby.ftl.Ftl;
import org.jooby.jdbc.Jdbc;
import org.jooby.jdbi.Jdbi3;
import org.jooby.jdbi.TransactionalRequest;
import org.jooby.livereload.LiveReload;
import org.jooby.json.Gzon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author vvandertas
 */
public class App extends Jooby {
    private final Logger logger = LoggerFactory.getLogger(App.class);

    {
        // template engine
        use(new Ftl());

        // live reload
        use(new LiveReload());

        use(new Jdbc());

        use(new Flywaydb());

        use(new Jdbi3()
                // Install SqlObjectPlugin
                .doWith(jdbi -> {
                    jdbi.installPlugin(new SqlObjectPlugin());
                })
                // Create a transaction per request and attach all dao's
                .transactionPerRequest(
                    new TransactionalRequest()
                        .attach(SessionDao.class)
                        .attach(UserDao.class)
                        .attach(TaskDao.class)
                        .attach(UserTaskDataDao.class)
                        .attach(HintDao.class)
                )
        );

        before((req, rsp) -> {
            if (!req.path().equals("/")) {
                logger.debug("Not in path /, so finding user");
                if (req.session().isSet("userId")) {
                    UserDao userDao = req.require(UserDao.class);

                    User user = userDao.findById(UUID.fromString(req.session().get("userId").value()));
                    logger.debug("Found user: " + user.toString());
                    req.set("user", user);
                } else {
                    logger.debug("userId was not set on session.");
                }
            }
        });

        // allowing automatic storage of sessions
        session(PostgresSessionStore.class);

        // Jooby gson
        use(new Gzon());

        // set port to what Bing Search API wants to see
        port(9090);

        // enable cookie sessions
        cookieSession();

        // access to css and js files
        assets("/assets/**/**");

        // MVC routing
        use(ExperimentController.class);
        use(BingSearch.class);

        //TODO: Add selenium test

    }

    public static void main(final String[] args) {
        run(App::new, args);
    }
}
