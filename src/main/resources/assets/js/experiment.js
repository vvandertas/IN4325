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
                console.log("Nexting");

                // Show no results yet.
                $("#mainline, #paging1, #paging2").hide();
                $("#noresults").show();

            },
            error: function (errorData) {
                alert("error: " + JSON.stringify(errorData));
            }
        });
    }


});