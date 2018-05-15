let taskTable;

function mainTaskButton() {
    showContents(["userInfo", "tasks"]);
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

    for(let i=0;i<tasks.length;i++){
        const currentElement = tasks[i];
        const trEl = document.createElement("tr");
        const tdNameEl = document.createElement("td");
        const tdEditEl = document.createElement("td");
        const buttonEl = document.createElement("button");
        buttonEl.value="Edit";
        tdNameEl.innerHTML = currentElement.name;


        tdEditEl.appendChild(buttonEl);

        trEl.appendChild(tdNameEl);
        trEl.appendChild(tdEditEl);

        document.getElementById("taskTable").appendChild(trEl);

    }
}


