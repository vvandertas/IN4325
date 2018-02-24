package com.vdtas;

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

    // access to css and js files
    assets("/assets/css/**");
    assets("/assets/js/**");

    // MVC routing
    use(ViewController.class);
    use(BingSearch.class);
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
