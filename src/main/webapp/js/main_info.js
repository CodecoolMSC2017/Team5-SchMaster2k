let loadButtonEl;

function loadTaskSchNo(){
    const text = JSON.parse(this.responseText);
    document.getElementById("schNumber").innerHTML = '<i class="fa fa-calendar fa-1x"></i> Number of your schedules: ' + text.schedule;
    document.getElementById("schNumber").style.textAlign = "center";
    document.getElementById("taskNumber").innerHTML = '<i class="fa fa-check-square-o fa-1x"></i> Number of your tasks: ' + text.task;
    document.getElementById("taskNumber").style.textAlign = "center";
    if(document.getElementById("actualUserRank").value == "Admin"){
        document.getElementById("onlineNumber").innerHTML = '<i class="fa fa-users fa-1x"></i> Online members: ' + text.onlineNumber;
        document.getElementById("onlineNumber").style.textAlign = "center";
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
