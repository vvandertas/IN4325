<div id="experiment" class="col-md-4 container" style="margin-top: 25px; display: none">
    <form id="experimentForm" enctype="application/x-www-form-urlencoded" action="/validate" method="post">
        <div class="form-group">
            <label for="question" class="form-label">Question</label>
            <p id="question"></p>
        </div>
        <div id="hints-div" class="form-group">
            <label for="hints" class="form-label">Hint</label>
            <p id="hints"></p>
        </div>

        <div class="form-group">
            <label for="answer" class="form-label">Answer</label>
            <input type="text" class="form-control" id="answer">
        </div>
        <input type="hidden" name="taskId" id="taskId">
        <button type="submit" class="btn btn-bing">Submit</button>
        <a href='#' id="skip">Skip</a>
    </form>
</div>