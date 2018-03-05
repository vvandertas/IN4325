package com.vdtas.controllers;

import com.google.gson.Gson;
import com.vdtas.SessionException;
import com.vdtas.helpers.SessionHelper;
import com.vdtas.models.TaskResponse;
import com.vdtas.models.Task;
import com.vdtas.models.Tasks;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.Session;
import org.jooby.mvc.GET;
import org.jooby.mvc.POST;
import org.jooby.mvc.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


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
    @Path("/next")
    public Result nextTask(Request request) {
        Session session = request.session();
        Task task = null;
        String hint = "";
        try {
            // then move to next task, if available
            task = SessionHelper.updateUserTask(session);
            hint = SessionHelper.findCurrentHint(session);
        } catch (SessionException e) {
            logger.error("Unable to retrieve next task and hint", e);
        }


        // This user is done with the experiment
        if(task == null) {
            // Redirect to home page TODO: Something else?
            Results.redirect("/");
        } else {
            // TODO: Use task question and hint in UI
        }

        return Results.ok();
    }

    @POST
    @Path("/validate")
    public Result validateAnswer(TaskResponse taskResponse) {
        Map<String, Object> result =  new HashMap<>();
        boolean success = false;
        String message = "";

        Task task = Tasks.getTasks().get(taskResponse.getTaskIndex());

        // TODO: validate url and answer text.

        result.put("success", success);
        result.put("message", message);

        return Results.json(new Gson().toJson(result));
    }
}