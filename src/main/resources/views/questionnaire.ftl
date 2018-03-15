<!DOCTYPE html>
<html>
<head>
<#include "includes/head.ftl">
</head>

<body>
<div class="container" style="margin-top: 25px;">
    <div class="row">
        <div id="questionnaireDiv" class="offset-md-2 col-md-8">
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

                <a type="submit" class="btn btn-bing" style="float: right;">Submit</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<#include "includes/scripts.ftl">
<script type="text/javascript" src="assets/js/questionnaire.js"></script>