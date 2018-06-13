let loadButtonEl;

function loadTaskSchNo(){
    const text = JSON.parse(this.responseText);
    document.getElementById("schNumber").innerHTML = 'Number of your schedules: ' + text.schedule;
    document.getElementById("taskNumber").innerHTML = 'Number of your tasks: ' + text.task;
    if(document.getElementById("actualUserRank").value == "Admin"){
        document.getElementById("onlineNumber").innerHTML = 'Online members: ' + text.onlineNumber;
    }
}

function getNuOfTaskSch(){
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loadTaskSchNo);
    xhr.open('GET', 'protected/taskSchNoServlet');
    xhr.send();
}

function mainInfo() {
    getNuOfTaskSch();

    loadButtonEl = document.getElementById('mainSchButton');
    loadButtonEl.addEventListener('click', mainSchButton);

    loadButtonEl = document.getElementById('mainTaskButton');
    loadButtonEl.addEventListener('click', mainTaskButton);

    if(document.getElementById("actualUserRank").value == "Admin"){
        const usersButtonEl = document.getElementById('usersButton');
        usersButtonEl.addEventListener('click', usersButton);
    }
}
