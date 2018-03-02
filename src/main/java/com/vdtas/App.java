package com.vdtas;

import com.vdtas.controllers.BingSearch;
import com.vdtas.controllers.ExperimentController;
import com.vdtas.models.UserSession;
import org.jooby.Jooby;
import org.jooby.ftl.Ftl;
import org.jooby.livereload.LiveReload;
import org.jooby.json.Gzon;

/**
 * @author vvandertas
 */
public class App extends Jooby {

    {
        // template engine
        use(new Ftl());

        // live reload
        use(new LiveReload());

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
        use(UserSession.class);

        //TODO: Add selenium test

    }

    public static void main(final String[] args) {
        run(App::new, args);
    }
}
