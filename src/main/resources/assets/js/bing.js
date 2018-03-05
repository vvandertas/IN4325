$('document').ready(function () {

    // Handler for search submit
    $("#searchForm").submit(function(e) {
        e.preventDefault();
        search()
    });


    // Click handler for next and previous links
    $(".paging a").on("click", function(e) {
        e.preventDefault();

        // Determine if we want previous or next results
        var isPrev = $(this).hasClass("prev");
        var offsetField =$("[name=offset]");

        var offset = parseInt(offsetField.val(), 10);
        var count = parseInt($("[name=count]").val(), 10);

        if(isPrev) {
            if (offset && offset > 0)  {
                offset -= count;
                if (offset < 0) offset = 0;
            } else {
                alert("You're already at the beginning!");
            }
        } else {
            offset += count;
        }

        // Update the offset value and search again
        offsetField.val(offset);
        search()
    });

    // Perform Bing WebSearch
    function search() {
        console.log("Performing web search");

        $.ajax({
            url: '/search',
            method: 'get',
            data: $("#searchForm").serialize(),
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log("")
                // use the handleOnLoad method to display the search results
                showResults(data);

            },
            error: function (errorData) {
                alert("error: " + JSON.stringify(errorData));
            }
        });
    }

    // show or hide request json
    $("#json").on("click", function(e) {
        e.preventDefault();

        if($("#_json").css('display') === 'none') {
            $("#_json").show('slow');
        } else {
            $("#_json").hide('slow');
        }
    });



    // Keep track of cookies/localStorage
    try {
        localStorage.getItem;   // try localStorage

        window.retrieveValue = function (name) {
            return localStorage.getItem(name) || "";
        }
        window.storeValue = function (name, value) {
            localStorage.setItem(name, value);
        }
    } catch (e) {
        window.retrieveValue = function (name) {
            var cookies = document.cookie.split(";");
            for (var i = 0; i < cookies.length; i++) {
                var keyvalue = cookies[i].split("=");
                if (keyvalue[0].trim() === name) return keyvalue[1];
            }
            return "";
        }
        window.storeValue = function (name, value) {
            var expiry = new Date();
            expiry.setFullYear(expiry.getFullYear() + 1);
            document.cookie = name + "=" + value.trim() + "; expires=" + expiry.toUTCString();
        }
    }


    // get the host portion of a URL, strpping out search result formatting and www too
    function getHost(url) {
        return url.replace(/<\/?b>/g, "").replace(/^https?:\/\//, "").split("/")[0].replace(/^www\./, "");
    }

    // format plain text for display as an HTML <pre> element
    function preFormat(text) {
        text = "" + text;
        return "<pre>" + text.replace(/&/g, "&amp;").replace(/</g, "&lt;") + "</pre>"
    }


    // put HTML markup into a <div> and reveal it
    function showDiv(id, html) {
        var content = document.getElementById("_" + id)
        if (content) content.innerHTML = html;
        var wrapper = document.getElementById(id);
        if (wrapper) wrapper.style.display = html.trim() ? "block" : "none";
    }


    // hides the specified <div>s
    function hideDivs() {
        for (var i = 0; i < arguments.length; i++) {
            var element = document.getElementById(arguments[i])
            if (element) element.style.display = "none";
        }
    }

    // render functions for various types of search results
    var searchItemRenderers = {
        // render Web page result
        webPages: function (item) {
            var html = [];
            html.push("<p class='webPages'><a href='" + item.url + "'>" + item.name + "</a>");
            html.push(" (" + getHost(item.displayUrl) + ")");
            html.push("<br>" + item.snippet);
            if ("deepLinks" in item) {
                var links = [];
                for (var i = 0; i < item.deepLinks.length; i++) {
                    links.push("<a href='" + item.deepLinks[i].url + "'>" +
                        item.deepLinks[i].name + "</a>");
                }
                html.push("<br>" + links.join(" - "));
            }
            return html.join("");
        }
    }

    // render search results from rankingResponse object in specified order
    function renderResultsItems(section, results) {

        var items = results.rankingResponse[section].items;
        var html = [];
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            // collection name has lowercase first letter while answerType has uppercase
            // e.g. `WebPages` rankingResult type is in the `webPages` top-level collection
            var type = item.answerType[0].toLowerCase() + item.answerType.slice(1);
            // must have results of the given type AND a renderer for it
            if (type in results && type in searchItemRenderers) {
                var render = searchItemRenderers[type];
                // this ranking item refers to ONE result of the specified type
                if ("resultIndex" in item) {
                    html.push(render(results[type].value[item.resultIndex], section));
                    // this ranking item refers to ALL results of the specified type
                } else {
                    var len = results[type].value.length;
                    for (var j = 0; j < len; j++) {
                        html.push(render(results[type].value[j], section, j, len));
                    }
                }
            }
        }
        return html.join("\n\n");
    }

    // render the search results given the parsed JSON response
    function renderSearchResults(results) {

        // if spelling was corrected, update search field
        if (results.queryContext.alteredQuery)
            document.forms.bing.query.value = results.queryContext.alteredQuery;

        // add Prev / Next links with result count
        var pagingLinks = updateResultInfo(results);
        $("#paging1").show();
        $("#paging2").show();

        // for each possible section, render the resuts from that section
        for (section in {pole: 0, mainline: 0, sidebar: 0}) {
            if (results.rankingResponse[section])
                showDiv(section, renderResultsItems(section, results));
        }
    }

    function renderErrorMessage(message) {
        showDiv("error", preFormat(message));
        showDiv("noresults", "No results.");
    }

    // show the Bing search results and update the paging info.
    function showResults(data) {
        hideDivs("noresults");

        console.log("In the handle load method");
        var json = data.jsonResponse.trim();
        var jsobj = {};

        // try to parse JSON results
        try {
            if (json.length) jsobj = JSON.parse(json);
        } catch (e) {
            renderErrorMessage("Invalid JSON response");
        }

        // show raw JSON div
        showDiv("json", preFormat(JSON.stringify(jsobj, null, 2)));

        // try to render the search results
        if (json.length) {
            if (jsobj._type === "SearchResponse" && "rankingResponse" in jsobj) {
                console.log("rendering search results");
                renderSearchResults(jsobj);
            } else {
                renderErrorMessage("No search results in JSON response");
            }
        } else {
            renderErrorMessage("Empty response (are you sending too many requests too quickly?)");
        }
        return false;
    }

    // Update the paging result info
    function updateResultInfo(results) {
        console.log("Updating paging info");

        var offset = parseInt($("[name=offset]").val(), 10);
        var count = parseInt($("[name=count]").val(), 10);

        // Only update the text here
        $(".resultInfo").html("Results " + (offset + 1) + " to " + (offset + count) + " of about " + results.webPages.totalEstimatedMatches + ".");
        console.log("Finished updating paging info");
    }
});