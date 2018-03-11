<!DOCTYPE html>
<html>
<head>
<#include "includes/head.ftl">
</head>

<body>
    <div id="questionnaireDiv" class="col-md-8 container">
        <form id="questionnaire" enctype="application/x-www-form-urlencoded" action="/questionnaire" method="post">
            <div class="form-group row">
                <label for="q1" class="col-sm-2 col-form-label">Question 1</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="q1">
                </div>
            </div>
            <div class="form-group row">
                <label for="url" class="col-sm-2 col-form-label">URL</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="url" name="url">
                </div>
            </div>

            <input type="hidden" name="taskId" id="taskId">
            <a type="submit" class="btn btn-info">Submit</a>
        </form>
        <a href='#' id="skip" style="float:right">Skip</a>
    </div>
</body>
</html>
<#include "includes/scripts.ftl">
<script type="text/javascript" src="assets/js/questionnaire.js"></script>