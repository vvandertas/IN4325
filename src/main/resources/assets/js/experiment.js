$('document').ready(function () {
    // Get the first question
    getTaskData();

    /**
     * Get the next task for the user and show the appropriate question + hint(s).
     * Redirect to questionnaire if there are no next questions
     */
    function getTaskData() {
        $.ajax({
            url: '/taskData',
            method: 'get',
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log("data: ", data);

                if (data.task !== "null") {
                    showTaskInfo(data.task, data.hints);
                    clearAnswerForm();
                } else {
                    // TODO: Error or go to questionnaire
                    console.log("NO TASK DATA")
                }
                return null;
            },
            error: function (errorData) {
                alert("error: " + JSON.stringify(errorData));
            }
        });
    }

    /**
     * Show the question and hints for the current task and user
     *
     * @param task
     * @param hints
     */
    function showTaskInfo(task, hints) {
        // Show no results yet.
        $("#mainline, #paging1, #paging2").hide();
        $("#noresults").show();

        // Show experiments div
        $("#experiment").show();

        $("#question").html(task.question);
        $("#taskId").val(task.id);

        switch (hints.length) {
            case 0:
                $("#hints-div").hide();
                break;
            case 1:
                $("#hints-div").show();
                $("#hints").html(hints[0].hint);
                break;
            default:
                $("#hints-div").show();
                var html = "<ul>";
                $.each(hints, function (i) {
                    html += "<li>" + hints[i].hint + "</li>";
                });

                html += "</ul>";

                $("#hints").html(html);
                break;
        }
    }

    function clearAnswerForm() {
        $("#url").val("");
        $("#answer").val("");
    }

    $("#skip").on("click", function (e) {
        e.preventDefault();

        // Move to next question
        nextTask();
    });

    // Handler for search submit
    $("#experimentForm").submit(function (e) {
        e.preventDefault();

        // only validate if there is data
        if($("#answer").val() !== "") {
            validate();
        }

    });

    function nextTask() {
        $.ajax({
            url: '/next',
            method: 'get',
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log("data: ", data);
                if(data.hasNext) {
                    getTaskData();
                } else {
                    // Redirect to questionnaire
                    window.location.replace("/questionnaire");

                }
            },
            error: function (errorData) {
                alert("error: " + JSON.stringify(errorData));
            }
        });
    }

    /**
     * Validate answer.
     * If correct move to the next question (or questionnaire if this was the last question)
     * else show feedback
     */
    function validate() {
        $.ajax({
            url: '/validate',
            method: 'post',
            data: JSON.stringify({text: $('#answer').val(), taskId: $('#taskId').val(), url: $('#url').val()}),
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log("Validated answer!");

                if(data.success) {
                    nextTask();
                } else {
                    // TODO: Update modal and potential hard code message in the ftl file
                    $("#failureAlert .modal-body p").html(data.message);
                    $("#failureAlert").modal('show');
                }
            },
            error: function (errorData) {
                alert("error: " + errorData);
            }
        });
    }


});

    /**
     *  Only after displaying the search results the link clicks can be bound.
     *  On click we open the clicked page as normal but also capture what link was clicked
     */
    function bindCaptureCallback() {
        $(".webPages a").on("click", function (e) {

            console.log("Clicked on link: " + this.href);
            var link = this.href;

            $.ajax({
                url: '/capture',
                method: 'post',
                data: JSON.stringify({url: link, taskId: $('#taskId').val()}),
                contentType: 'application/json',
                dataType:'json',
                success: function(data) {
                    console.log("Done. Opening link");
                }, error: function (errorData) {
                    alert("error: " + JSON.stringify(errorData));
                }
            })

        });
    }