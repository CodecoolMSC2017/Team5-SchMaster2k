let taskTable;
let buttonId;

function mainTaskButton() {
    showContents(["userInfo", "tasks", "goBackToMain"]);
    const params = new URLSearchParams();
    const taskTableEl = document.getElementById("taskTable");
    taskTable = taskTableEl;
    const username = document.getElementById("actualUsername").value;
    params.append('username', username);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', showTasks);
    xhr.open('POST', 'taskServlet');
    xhr.send(params);
}

function showTasks(){
    const tasks = JSON.parse(this.responseText);
    const table = document.getElementById("taskTable");
    while(table.firstChild) {
           table.removeChild(table.firstChild);
        }
    for(let i=0;i<tasks.length;i++){
        const currentElement = tasks[i];
        const trEl = document.createElement("tr");
        const tdNameEl = document.createElement("td");
        const tdEditEl = document.createElement("td");
        const buttonEl = document.createElement("button");
        buttonEl.addEventListener("click",showEdit);
        buttonEl.innerHTML = "edit";
        buttonEl.id=currentElement.id;
        tdNameEl.innerHTML = currentElement.name;
        tdNameEl.id="name"+currentElement.id;


        tdEditEl.appendChild(buttonEl);

        trEl.appendChild(tdNameEl);
        trEl.appendChild(tdEditEl);

        document.getElementById("taskTable").appendChild(trEl);

    }
}

function showEdit(){
    const tdTextBox = document.getElementById("name"+this.id);
    this.innerHTML="send";
    this.addEventListener("click",updateTask);
    const inputEl = document.createElement("input");
    inputEl.type="text";
    inputEl.id="taskName";
    inputEl.name="taskName";
    tdTextBox.appendChild(inputEl);
    tdTextBox.id="name"+this.id;

}

function updateTask(){
    this.innerHTML="edit";
    const params = new URLSearchParams();
    const updatedName = document.getElementById("taskName").value;

    params.append('updatedName', updatedName);
    params.append('taskId', this.id);
    buttonId=this.id;
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loadUpdatedTask);
    xhr.open('POST', 'updateTask');
    xhr.send(params);

}

function loadUpdatedTask(){
    const nameTdEl = document.getElementById("name"+buttonId);
    const updatedName = JSON.parse(this.responseText);
    nameTdEl.removeChild(nameTdEl.firstChild);
    nameTdEl.innerHTML = updatedName.message;

}


