package com.vdtas;

import com.vdtas.helpers.SessionHelper;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.GET;
import org.jooby.mvc.Path;

/**
 * @author vvandertas
 */
@Path("/")
public class ViewController {

  @GET
  public Result index(Request request) {
    // Make sure a userSession exists
    SessionHelper.validateUserSession(request.session());
    return Results.html("bing");
  }
}