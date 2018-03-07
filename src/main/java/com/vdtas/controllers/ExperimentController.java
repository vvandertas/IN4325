package com.vdtas.controllers;

import com.google.gson.Gson;
import com.vdtas.helpers.ExperimentHelper;
import com.vdtas.helpers.SessionHelper;
import com.vdtas.helpers.TaskHelper;
import com.vdtas.models.*;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.GET;
import org.jooby.mvc.POST;
import org.jooby.mvc.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author vvandertas
 */
@Path("/")
public class ExperimentController {

    private final Logger logger = LoggerFactory.getLogger(ExperimentController.class);

    private ExperimentHelper experimentHelper;
    private TaskHelper taskHelper;
    private SessionHelper sessionHelper;

    @Inject
    public ExperimentController(ExperimentHelper experimentHelper, TaskHelper taskHelper, SessionHelper sessionHelper) {
        this.experimentHelper = experimentHelper;
        this.taskHelper = taskHelper;
        this.sessionHelper = sessionHelper;
    }

    /**
     * Show the landing page
     * @param request
     * @return
     */
    @GET
    public Result index(Request request) {
        // Make sure a userSession exists
        sessionHelper.findOrCreateUser(request.session());
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
        User user = request.get("user");
        // then move to next task, if available
        Task task = taskHelper.getNextTask(user);

        // This user is done with the experiment
        if(task == null) {
            // TODO: Redirect to questionnaire instead
            return Results.redirect("/");
        }

        // Find all hints for the current task.
        List<Hint> hints = experimentHelper.findCurrentHints(user, task);
        List<String> textHints = hints.stream().map(Hint::getHint).collect(Collectors.toList());
        
        // TODO: Use task question and hint in UI
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