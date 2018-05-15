let loadButtonEl;

function mainSchButton() {
    alert("Schedule...");
}

function mainTaskButton() {
    alert("Tasks...");
}

function mainInfo() {
    document.getElementById("schNumber").innerHTML = "Schedules: 12345";
    document.getElementById("taskNumber").innerHTML = "Tasks: 12";

    loadButtonEl = document.getElementById('mainSchButton');
    loadButtonEl.addEventListener('click', mainSchButton);

    loadButtonEl = document.getElementById('mainTaskButton');
    loadButtonEl.addEventListener('click', mainTaskButton);
}
