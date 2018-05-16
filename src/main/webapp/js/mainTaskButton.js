let taskTable;
let buttonId;

function mainTaskButton() {
    showContents(["userInfo", "tasks", "goBackToMain"]);
    const params = new URLSearchParams();
    const taskTableEl = document.getElementById("taskTable");
    taskTable = taskTableEl;
    const addTaskButton = document.getElementById("addTaskButton");
    addTaskButton.addEventListener("click",addTask);
    const username = document.getElementById("actualUsername").value;
    params.append('username', username);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', showTasks);
    xhr.open('POST', 'taskServlet');
    xhr.send(params);
}

function addTask(){
    const newTaskName=document.getElementById("newTaskName").value;
    const username = document.getElementById("actualUsername").value;
    const params = new URLSearchParams();
    params.append('taskAddingField', newTaskName);
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
    const tableHeaderRow = document.createElement("tr");
    const tdNameHeader = document.createElement("td");
    tdNameHeader.innerHTML="Name";
    tableHeaderRow.appendChild(tdNameHeader);
    table.appendChild(tableHeaderRow);

    for(let i=0;i<tasks.length;i++){
        const currentElement = tasks[i];
        const trEl = document.createElement("tr");
        const tdNameEl = document.createElement("td");
        const tdEditEl = document.createElement("td");
        const tdDeleteEl = document.createElement("td");
        const buttonEl = document.createElement("button");
        const deleteButtonEl = document.createElement("button");
        buttonEl.addEventListener("click",showEdit);
        buttonEl.innerHTML = "edit";
        buttonEl.id=currentElement.id;
        deleteButtonEl.addEventListener("click",deleteTask);
        deleteButtonEl.innerHTML = "delete";
        deleteButtonEl.id=currentElement.id;

        tdNameEl.innerHTML = currentElement.name;
        tdNameEl.id="name"+currentElement.id;


        tdEditEl.appendChild(buttonEl);
        tdDeleteEl.appendChild(deleteButtonEl);

        trEl.appendChild(tdNameEl);
        trEl.appendChild(tdEditEl);
        trEl.appendChild(tdDeleteEl);

        document.getElementById("taskTable").appendChild(trEl);

    }
}

function deleteTask(){
    const taskId=this.id;
    const username = document.getElementById("actualUsername").value;
    const params = new URLSearchParams();
    params.append('taskIdToDelete', taskId);
    params.append('username', username);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', showTasks);
    xhr.open('POST', 'taskServlet');
    xhr.send(params);
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


