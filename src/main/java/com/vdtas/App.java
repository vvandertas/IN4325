package com.vdtas;

import org.jooby.Jooby;
import org.jooby.livereload.LiveReload;
import org.jooby.json.Gzon;


/**
 * @author vvandertas
 */
public class App extends Jooby {
  {
    // live reload ftw
    use(new LiveReload());

    use(new Gzon());

    // set port to what Bing Search API wants to see
    port(9090);

    // access to css and js files
    assets("/assets/css/**");
    assets("/assets/js/**");

    // static homepage
    assets("/", "bing.html");

    use(BingSearch.class);
    // route for processing search request
//    get("/search" ,(req) -> {
//      BingSearch bingSearch = req.require(BingSearch.class);
//      return bingSearch.processRequest(req);
//    });

  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
