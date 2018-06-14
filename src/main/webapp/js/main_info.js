let loadButtonEl;

function createMessageList(message){
    const ulEl = document.createElement('ul');

    for(let i=0; i < message.length; i++){
        const oneMessage = message[i];
        const liEl = document.createElement('li');
        liEl.innerHTML = "<Strong>Your task:</Strong> " + oneMessage[0]+ "<br>" + "<Strong>Deleted by Admin:</Strong> " + oneMessage[2]+ "<br>" +
        "<Strong>On:</Strong> " + oneMessage[1]+ "<br>" + "<Strong>Cause:</Strong> " + oneMessage[3];
        ulEl.appendChild(liEl);
    }
    return ulEl;
}

function loadAdminMessages(){
    const message = JSON.parse(this.responseText);
    const divMessageEl = document.getElementById('admin-messages');

    while(divMessageEl.firstChild){
        divMessageEl.removeChild(divMessageEl.firstChild);
    }
    divMessageEl.appendChild(createMessageList(message));
}

function loadAdminMessagesForUser(){
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loadAdminMessages);
    xhr.open('GET', 'protected/adminMessage');
    xhr.send();
}

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
    loadAdminMessagesForUser();

    loadButtonEl = document.getElementById('mainSchButton');
    loadButtonEl.addEventListener('click', mainSchButton);

    loadButtonEl = document.getElementById('mainTaskButton');
    loadButtonEl.addEventListener('click', mainTaskButton);

    if(document.getElementById("actualUserRank").value == "Admin"){
        const usersButtonEl = document.getElementById('usersButton');
        usersButtonEl.addEventListener('click', usersButton);
    }
}
