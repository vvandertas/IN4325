package com.vdtas.controllers;

import com.vdtas.SessionException;
import com.vdtas.helpers.SessionHelper;
import com.vdtas.models.Task;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.Session;
import org.jooby.mvc.GET;
import org.jooby.mvc.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author vvandertas
 */
@Path("/")
public class ExperimentController {

    private final Logger logger = LoggerFactory.getLogger(ExperimentController.class);

    // TODO: Make sure experiment stops after last task is skipped or answered correctly

    /**
     * Show the landing page
     * @param request
     * @return
     */
    @GET
    public Result index(Request request) {
        // Make sure a userSession exists
        SessionHelper.findOrCreateUserSession(request.session());

        return Results.html("bing");
    }

    /**
     * Provide the user with a task
     * @param request
     * @return
     */
    @GET
    public Result nextTask(Request request) {
        Session session = request.session();
        Task task = null;
        String hint = "";
        try {
            task = SessionHelper.updateUserTask(session);
            hint = SessionHelper.findCurrentHint(session);
        } catch (SessionException e) {
            logger.error("Unable to retrieve next task and hint", e);
        }


        // This user is done with the experiment
        if(task == null) {
            // TODO: Redirect to home page or something
        } else {
            // TODO: Use task question and hint in UI
        }

        return Results.ok();
    }
}