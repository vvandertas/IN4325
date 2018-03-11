package com.vdtas.controllers;

import com.google.common.collect.ImmutableMap;
import com.vdtas.helpers.ExperimentHelper;
import com.vdtas.helpers.SessionHelper;
import com.vdtas.helpers.TaskHelper;
import com.vdtas.models.*;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.Session;
import org.jooby.mvc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    // TODO: Make sure we always have task data, even though user skipped without querying.

    /**
     * Show the landing page
     * @param request
     * @return
     */
    @GET
    public Result index(Request request) {
        return Results.html("landing"); // TODO: Update landing page
    }

    @GET
    @Path("/experiment")
    public Result start(Session session, Optional<String> forceType) {
        // Make sure a userId in the current session and an accompanying user
        sessionHelper.findOrCreateUser(session, forceType);
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
            return Results.json(ImmutableMap.of("hasNext", false));
        }

        // Find all hints for the current task.
        List<Hint> hints = experimentHelper.findCurrentHints(user, task);
        List<String> textHints = hints.stream().map(Hint::getHint).collect(Collectors.toList());

        Map<String, Object> results = ImmutableMap.of("hasNext",true, "task", task, "hints", hints);
        return Results.json(results);
    }

    @POST
    @Path("/validate")
    public Result validateAnswer(@Body TaskResponse taskResponse, @Local User user) {
        Map<String, Object> result = new HashMap<>();
        boolean success = false;
        String message = "";

        if(taskHelper.validateTask(taskResponse)) {
            success = true;
        } else {
            message = "Unfortunately your answer is incorrect or incomplete. Please keep searching until you find the correct answer";
        }

        result.put("success", success);
        result.put("message", message);

        return Results.json(result);
    }

    @POST
    @Path("/capture")
    @Consumes("application/json")
    public Result captureLinkClick(@Body ClickCapture clickCapture, @Local User user) {

        // set the userId for this click and insert it into the db
        clickCapture.setUserId(user.getId());
        experimentHelper.captureClick(clickCapture);

        return Results.json(ImmutableMap.of("success", true));
    }
}