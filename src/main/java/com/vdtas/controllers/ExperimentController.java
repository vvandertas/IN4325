package com.vdtas.controllers;

import com.google.common.collect.ImmutableList;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.vdtas.helpers.Routes.*;
import static com.vdtas.helpers.SessionHelper.USER_ID;


/**
 * @author vvandertas
 */
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
     */
    @GET
    @Path(LANDING)
    public Result index() {
        return Results.html("landing"); // TODO: Update landing page
    }

    @GET
    @Path(EXPERIMENT)
    public Result start(Session session, Optional<String> forceType) {
        // Make sure a userId in the current session and an accompanying user
        boolean createdUser = sessionHelper.findOrCreateUser(session, forceType);

        // Make sure we always have task data, even though user skipped without querying.
        if(createdUser) {
            experimentHelper.createUserTaskData(UUID.fromString(session.get(USER_ID).value()), 1);
        }

        return Results.html("bing");
    }

    /**
     * Find current task info for a user
     * @param user
     * @return
     */
    @GET
    @Path(TASKDATA)
    public Result getTaskData(@Local User user) {
        Task task = taskHelper.findTask(user.getCurrentTaskId());

        // This user is done with the experiment
        if(task == null) {
            return Results.json(ImmutableMap.of("task", "null"));
        }

        // Get the total number of tasks
        int taskCount = taskHelper.getTaskCount();

        // Find all hints for the current task.
        List<Hint> hints = experimentHelper.findCurrentHints(user, task);

        Map<String, Object> results = ImmutableMap.of("task", task, "hints", hints, "task_count", taskCount);
        return Results.json(results);
    }

    /**
     * Assign next task to the user
     */
    @GET
    @Path(NEXT)
    public Result nextTask(@Local User user) {
        // Increment task id for user, if next task is available
        boolean hasNext = taskHelper.getNextTask(user);
        Map<String, Object> results = ImmutableMap.of("hasNext",hasNext);

        // Insert new object to capture data for next task.
        if(hasNext) {
            experimentHelper.createUserTaskData(user);
        }

        return Results.json(results);
    }

    /**
     * Validate a users task response
     *
     * @param taskResponse object containing users answer and submitted url
     * @param user current user
     * @return
     */
    @POST
    @Path(VALIDATE)
    public Result validateAnswer(@Body TaskResponse taskResponse, @Local User user) {
        Map<String, Object> result = new HashMap<>();
        boolean success = false;
        String message = "";

        // increment number of attempts for this task
        experimentHelper.incrementAttempts(user);

        // and validate the answer
        if(taskHelper.validateTask(taskResponse)) {
            success = true;

            // register the end of this task for the user
            experimentHelper.logAnswerFound(user);
        } else {
            message = "Unfortunately your answer is incorrect or incomplete. Please keep searching until you find the correct answer";
        }

        result.put("success", success);
        result.put("message", message);

        return Results.json(result);
    }

    /**
     * Capture a users search result click, used for post processing of the data.
     * @param clickCapture wrapper for userId, taskId and url clicked
     * @param user current user
     * @return
     */
    @POST
    @Path(CAPTURE)
    @Consumes("application/json")
    public Result captureLinkClick(@Body ClickCapture clickCapture, @Local User user) {

        // set the userId for this click and insert it into the db
        clickCapture.setUserId(user.getId());
        experimentHelper.captureClick(clickCapture);

        return Results.json(ImmutableMap.of("success", true));
    }

    @GET
    @Path(QUESTIONNAIRE)
    public Result showQuestionnaire(@Local User user) {
        logger.debug("Getting questionnaire");
        sessionHelper.registerEndOfExperiment(user); // TODO: Possible do this after finishing questionnaire.

        return Results.html("questionnaire").put("participantType", user.getParticipantType().toString());
    }

    @POST
    @Path(QUESTIONNAIRE)
    public Result saveAnswers(@Body Map<String, String> answers, @Local User user) {
        sessionHelper.saveQuestionnaireData(user, answers);

        return Results.json(ImmutableMap.of("success", true));
    }
}
