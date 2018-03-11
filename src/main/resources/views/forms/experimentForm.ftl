<div id="experiment" class="col-md-4 container" style="display: none">
    <form id="experimentForm" enctype="application/x-www-form-urlencoded" action="/validate" method="post">
        <div class="form-group row">
            <label for="question" class="col-sm-2 col-form-label">Question</label>
            <div class="col-sm-10">
                <p id="question"></p>
            </div>
        </div>
        <div id="hints-div" class="form-group row">
            <label for="hints" class="col-sm-2 col-form-label">Hint</label>
            <div class="col-sm-10">
                <p id="hints"></p>
            </div>
        </div>

        <div class="form-group row">
            <label for="answer" class="col-sm-2 col-form-label">Answer</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="answer">
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