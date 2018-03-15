package com.vdtas;

import com.vdtas.controllers.BingSearch;
import com.vdtas.controllers.ExperimentController;
import com.vdtas.daos.*;
import com.vdtas.models.User;
import org.jdbi.v3.postgres.PostgresPlugin;
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

import static com.vdtas.helpers.Routes.EXPERIMENT;

/**
 * @author vvandertas
 */
public class App extends Jooby {
    private final Logger logger = LoggerFactory.getLogger(App.class);

    {
        // template engine
        use(new Ftl("views/", ".ftl"));

        // live reload
        use(new LiveReload());

        use(new Jdbc());

        use(new Flywaydb());

        use(new Jdbi3()
                // Install SqlObjectPlugin
                .doWith(jdbi -> {
                    jdbi.installPlugin(new SqlObjectPlugin());
                    jdbi.installPlugin(new PostgresPlugin());
                })
                // Create a transaction per request and attach all dao's
                .transactionPerRequest(
                        new TransactionalRequest()
                                .attach(UserDao.class)
                                .attach(TaskDao.class)
                                .attach(UserTaskDataDao.class)
                                .attach(HintDao.class)
                                .attach(CacheDao.class)
                )
        );


        before((req, rsp) -> {
            if (!req.path().equals("/") && !req.path().equals(EXPERIMENT)) {
                logger.debug("Finding user for path " + req.path());
                if (req.session().isSet("userId")) {
                    UserDao userDao = req.require(UserDao.class);

                    User user = userDao.findById(UUID.fromString(req.session().get("userId").value()));
                    if (user != null) {
                        logger.debug("Found user: " + user.toString());
                        req.set("user", user);
                    }
                } else {
                    // TODO: Redirect to landing page
                    logger.debug("userId was not set on session.");
                }
            }
        });

        // Jooby gson
        use(new Gzon());

        // set port to what Bing Search API wants to see
        port(9090);

        // enable cookie sessions
        cookieSession();

        // access to css and js files
        assets("/assets/css/**");
        assets("/assets/js/**");

        // MVC routing
        use(ExperimentController.class);
        use(BingSearch.class);
    }

    public static void main(final String[] args) {
        run(App::new, args);
    }
}
