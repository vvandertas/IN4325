$('document').ready(function () {
    //
    getNextQuestion();

    // Handler for search submit
    $("#experimentForm").submit(function (e) {
        e.preventDefault();
        //TODO: Validate answer, show feedback and move to next question (if possible)
        validate()
    });

    function validate() {

        $.ajax({
            url: '/validate',
            method: 'get',
            data: $("#experimentForm").serialize(),
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log("Validated answer!");

            },
            error: function (errorData) {
                alert("error: " + JSON.stringify(errorData));
            }
        });
    }

    function getNextQuestion() {
        $.ajax({
            url: '/next',
            method: 'get',
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log("data: ", data);

                if (data.hasNext) {
                    showTaskInfo(data.task, data.hints);
                } else {
                    // TODO: Error or go to questionnaire
                    console.log("NO NEXT")
                }
                return null;
            },
            error: function (errorData) {
                alert("error: " + JSON.stringify(errorData));
            }
        });
    }

    function showTaskInfo(task, hints) {
        // Show no results yet.
        $("#mainline, #paging1, #paging2").hide();
        $("#noresults").show();

        $("#question").html(task.question);
        $("#taskId").val(task.id);

        switch (hints.length) {
            case 0:
                $("#hints").hide();
                break;
            case 1:
                $("#hints").show();
                $("#hints").html(hints[0].hint);
                break;
            default:
                $("#hints").show();
                var html = "<ul>";
                $.each(hints, function (i) {
                    html += "<li>" + hints[i].hint + "</li>";
                });

                html += "</ul>";

                $("#hints").html(html);
                break;
        }
    }

    $("#skip").on("click", function (e) {
        e.preventDefault();

        // Move to next question
        getNextQuestion();
    });


});
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