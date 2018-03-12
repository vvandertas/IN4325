<!DOCTYPE html>
<html>
<head>
<#include "includes/head.ftl">

</head>

<body>
    <main role="main" class="container">
        <div id="instructions">
            <h1>Welcome!</h1>
            <p class="lead">You are about to participate in our experiment. Please read the following text carefully and hit 'START' when you are ready.</p>
            <p> This experiment consists of two parts. In the first part you will be presented with a number of questions that require you to search the web through the Bing search engine. <br>
                It is important that you only use the search engine provided and do not use any external sources to find the answer. <br>
                Additionally we urge you to really try and find the answer. In case you really get stuck you are provided with the option to skip a task, however the effort put into solving a question will be evaluated.
            </p>
            <p>
                Once you have answered the last question, you will move on to the second and last part of the experiment. All you have to do for this part is fill out a questionnaire and you are all set!
            </p>
            <a target="_self" href="/experiment" id="offset" class="btn btn-bing">Start</a>
        </div>

    </main>

   <#include "includes/scripts.ftl">
</body>
</html>

