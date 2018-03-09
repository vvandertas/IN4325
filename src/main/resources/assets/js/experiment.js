$('document').ready(function () {

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

    $("#skip").on("click", function (e) {
        e.preventDefault();

        // Move to next question
        nextQuestion();
    });

    function nextQuestion() {
        $.ajax({
            url: '/next',
            method: 'get',
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log("data: ", data);
                var parsedData = JSON.parse(data);

                if(parsedData.hasNext) {
                    // Show no results yet.
                    $("#mainline, #paging1, #paging2").hide();
                    $("#noresults").show();

                    $("#question").html(parsedData.task.question);
                    var hints = parsedData.hints;
                    switch(hints.length) {
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
                            hints.each(function(i) {
                                html += "<li>" + hints[i].hint + "</li>";
                            });

                            html += "</ul>";

                            $("#hints").html(html);
                            break;
                    }
                } else {
                    // TODO: Error or go to questionnaire
                    console.log("NO NEXT")
                }
            },
            error: function (errorData) {
                alert("error: " + JSON.stringify(errorData));
            }
        });
    }
});