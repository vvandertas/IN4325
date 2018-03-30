$('document').ready(function () {
    $("#questionnaire").submit(function (e) {
        e.preventDefault();

        var data = {
            likeability: $("input[name='likeability']:checked").attr('value'),
            difficulty: $("input[name='difficulty']:checked").attr('value')
        };

        // Add the hint usefulness question if it is present
        if ($("#hintquestion").is(':visible')) {
            console.log("Adding usefulness");
            data["usefulness"] = $("input[name='usefulness']:checked").attr('value');
        }

        console.log("Submitting request with data: " + JSON.stringify(data));

        $.ajax({
            url: '/questionnaire',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log("Finished posting questionnaire answers");
                window.location.replace("/end");
            }, error: function (errorData) {
                alert("error: " + JSON.stringify(errorData));
            }
        })

    })

});