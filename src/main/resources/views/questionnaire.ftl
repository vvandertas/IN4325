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
                <div class="form-group row likeabilityRow">
                    <label id="likeabilityQuestion" class="col-sm-2 col-form-label">How did you like the game?</label>
                    <div class="btn-group btn-group-toggle col-sm-10" data-toggle="buttons">
                        <label for="l1" class="btn btn-secondary active">
                            <input type="radio" name="likeability" id="l1" value="1" autocomplete="off" checked> Liked a Lot
                        </label>
                        <label for="l2" class="btn btn-secondary">
                            <input type="radio" name="likeability" id="l2" value="2" autocomplete="off"> Liked a little
                        </label>
                        <label for="l3" class="btn btn-secondary">
                            <input type="radio" name="likeability" id="l3" value="3" autocomplete="off"> Neutral
                        </label>
                        <label for="l4" class="btn btn-secondary">
                            <input type="radio" name="likeability" id="l4" value="4" autocomplete="off"> Disliked a little
                        </label>
                        <label for="l5" class="btn btn-secondary">
                            <input type="radio" name="likeability" id="l5" value="5" autocomplete="off"> Disliked a lot
                        </label>
                    </div>
                </div>

                <div class="form-group row difficultyRow">
                    <label id="difficultyQuestion" class="col-sm-2 col-form-label">How difficult was the game?</label>
                    <div class="btn-group btn-group-toggle col-sm-10" data-toggle="buttons">
                        <label for="d1" class="btn btn-secondary active">
                            <input type="radio" name="difficulty" id="d1" value="1" autocomplete="off" checked> Extremely easy
                        </label>
                        <label for="d2" class="btn btn-secondary">
                            <input type="radio" name="difficulty" id="d2" value="2" autocomplete="off"> Easy
                        </label>
                        <label for="d3" class="btn btn-secondary">
                            <input type="radio" name="difficulty" id="d3" value="3" autocomplete="off"> So and so
                        </label>
                        <label for="d4" class="btn btn-secondary">
                            <input type="radio" name="difficulty" id="d4" value="4" autocomplete="off"> Difficult
                        </label>
                        <label for="d5" class="btn btn-secondary">
                            <input type="radio" name="difficulty" id="d5" value="5" autocomplete="off"> Very difficult
                        </label>
                    </div>
                </div>

                <div id="hintquestion" class="form-group row usefulnessRow" <#if "nohint"?matches(participantType?lower_case)>style="display:none" </#if> >
                    <label id="usefulnessQuestion" class="col-sm-2 col-form-label">Were search hints useful to you?</label>
                    <div class="btn-group btn-group-toggle col-sm-10" data-toggle="buttons">
                        <label for="u1" class="btn btn-secondary active">
                            <input type="radio" name="usefulness" id="u1" value="1" autocomplete="off" checked> Extremely useful
                        </label>
                        <label for="u2" class="btn btn-secondary">
                            <input type="radio" name="usefulness" id="u2" value="2" autocomplete="off"> Useful
                        </label>
                        <label for="u3" class="btn btn-secondary">
                            <input type="radio" name="usefulness" id="u3" value="3" autocomplete="off"> Not really
                        </label>
                        <label for="u4" class="btn btn-secondary">
                            <input type="radio" name="usefulness" id="u4" value="4" autocomplete="off"> Distracting
                        </label>
                        <label for="u5" class="btn btn-secondary">
                            <input type="radio" name="usefulness" id="u5" value="5" autocomplete="off"> Detrimental
                        </label>
                    </div>
                </div>
                <button type="submit" class="btn btn-bing" style="float: right;">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<#include "includes/scripts.ftl">
<script type="text/javascript" src="assets/js/questionnaire.js"></script>