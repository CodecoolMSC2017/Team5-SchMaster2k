let currentSchId;
let currentDayHourId;

function scheduleDays(info){
    const schTableEl = document.createElement('table');
    const schTableTr1El = document.createElement('tr');
    const schTableTr2El = document.createElement('tr');

    const days = info.days;
    for (let i=0 ; i<days.length ; i++) {
        const schTableThEl = document.createElement('th');
        const day = days[i];
        schTableThEl.textContent = day.name;
        schTableTr1El.appendChild(schTableThEl);

        const schTableTdEl = document.createElement('td');
        const tasks = day.tasks;
        for (let j=0 ; j<tasks.length ; j++) {
            const task = tasks[j];
            const schTableDivEl = document.createElement('div');
            schTableDivEl.textContent = task.name;
            schTableTdEl.appendChild(schTableDivEl);
        }
        schTableTr2El.appendChild(schTableTdEl);
    }
    schTableEl.appendChild(schTableTr1El);
    schTableEl.appendChild(schTableTr2El);
    return schTableEl;
}

function scheduleView(info){
    const pNameEl = document.createElement('p');
    const schedule = info.schedule;
    pNameEl.textContent = "Sch name: " + schedule.name;
    pNameEl.appendChild(document.createTextNode(`: ${schedule.content}, ${schedule.id} `));

    return pNameEl;
}

function loadSchedule() {
    const scheduleInfo = JSON.parse(this.responseText);
    const divEl = document.createElement("div");
    const liEl = document.getElementById('liEl'+scheduleInfo.schedule.id);
    if(liEl.firstChild.nextSibling.nextSibling){
        liEl.removeChild(liEl.firstChild.nextSibling.nextSibling);
        liEl.removeChild(liEl.firstChild.nextSibling.nextSibling);
    }else{
    showContents(["schedulesInfo", "goBackToMain", "scheduleInfo"]);





    const viewButton = document.createElement("button");
    viewButton.innerHTML="View";
    viewButton.addEventListener("click", getDetailedSch);
    viewButton.id=scheduleInfo.schedule.id;

    liEl.appendChild(divEl);
    liEl.appendChild(viewButton);
    divEl.appendChild(scheduleView(scheduleInfo));
    divEl.appendChild(scheduleDays(scheduleInfo));


    }
}



function getDetailedSch(){
    const params = new URLSearchParams();
    params.append("schId", this.id);
    currentSchId = this.id;
    params.append("userId", document.getElementById("actualUserId").value);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', showDetailedSch);
    xhr.open('GET', 'protected/schAllInformation?' + params);
    xhr.send();
}

function showDetailedSch(){

    if(document.getElementById("testDivForTable").firstChild){
        document.getElementById("testDivForTable").removeChild(document.getElementById("testDivForTable").firstChild);
    }else{
    const hm = JSON.parse(this.responseText);

    const days=["zero","mo","tu","we","th","fr","sa","su"];
    const fullDays=["zero","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"];
    const table = document.createElement("table"); //<table></table>
    table.border="1";
    const trHeader = document.createElement("tr");
    for(let i=0;i<8;i++){
        const tdEl=document.createElement("td");
        if(i!=0){
            tdEl.innerHTML=fullDays[i];
            tdEl.style.backgroundColor = "#4CAF50";
            tdEl.style.color = "white";
            tdEl.style.fontWeight = "bold";
        }
        trHeader.appendChild(tdEl);
    }
    table.appendChild(trHeader);
    for(let r = 0;r<24;r++){
        const tr = document.createElement("tr");

        for(let c = 0;c<8;c++){
            const day=days[c]
            const td = document.createElement("td");
            if(c==0){
                td.innerHTML = r;
                td.style.backgroundColor = "#4CAF50";
                td.style.color = "white";
                td.style.fontWeight = "bold";
                tr.appendChild(td);
            }else{

                td.id=days[c]+r;
                const addButton = document.createElement("button");
                addButton.innerHTML = "Add task";
                addButton.id=days[c]+r;
                addButton.classList.add("addTaskButton");
                addButton.classList.add("button");
                addButton.classList.add("hidden");

                addButton.addEventListener("click",addTaskToSch);
                td.addEventListener("mouseover",showAddButton);
                td.addEventListener("mouseleave",hideAddButton);
                td.appendChild(addButton);
                tr.appendChild(td);


            }
        }
        table.appendChild(tr);
    }
    const div = document.getElementById("testDivForTable");

        div.appendChild(table);

    for (let i = 0, keys = Object.keys(hm), ii = keys.length; i < ii; i++) {

        const tdAppend = document.getElementById(keys[i]);
              const p = document.createElement("p");
              p.innerHTML = keys[i];
              //console.log(keys[i]);
              tdAppend.innerHTML = hm[keys[i]];

      }

    }
    }




function showAddButton(){

    this.firstChild.classList.remove("hidden");


}

function hideAddButton(){
       this.firstChild.classList.add("hidden");
}

function addTaskToSch(){
    const username = document.getElementById("actualUsername").value;
    currentDayHourId = this.id;

    const params = new URLSearchParams();
    params.append('username', username);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', showAvailableTasks);
    xhr.open('POST', 'taskServlet');
    xhr.send(params);


}

function showAvailableTasks(){
    const tasks = JSON.parse(this.responseText);
    const table = document.getElementById("tasksForSch");


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
        const buttonEl = document.createElement("button");
        buttonEl.classList.add("button");

        buttonEl.innerHTML = currentElement.name;
        buttonEl.id=tasks[i].id;
        buttonEl.addEventListener("click",insertTaskToSch);
        trEl.appendChild(tdNameEl);
        tdNameEl.appendChild(buttonEl);
        table.appendChild(trEl);

    }

    const modalDiv =document.getElementById("schModal");
    const close = document.getElementById("closeModal");
    close.addEventListener("click",closeModal);
    modalDiv.style.display = "block";
}

function insertTaskToSch(){
    const params = new URLSearchParams();
    params.append('schId', currentSchId);
    params.append('dayHour',currentDayHourId);
    params.append('taskId',this.id);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', showDetailedSch);
    xhr.open('POST', 'taskToSch');
    xhr.send(params);

}

function closeModal(){
    const modalDiv = document.getElementById("schModal");
    modalDiv.style.display = "none";
}


function onLoadSchedule() {
    const el = this;
    const schId = el.getAttribute('sch-id-info');

    const params = new URLSearchParams();
    params.append('id', schId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loadSchedule);
    xhr.open('GET', 'schServlet?' + params);
    xhr.send();
}

function scheduleList(schedules) {
    const ulEl = document.createElement('ul');

    for (let i = 0; i < schedules.length; i++) {
        const sch = schedules[i];

        //sch button to view the sch
        const schIdAttr = document.createAttribute('sch-id-info');
        schIdAttr.value = sch.id;
        const buttonNameEl = document.createElement('button');
        buttonNameEl.classList.add("button");
        buttonNameEl.textContent = sch.name;
        buttonNameEl.setAttributeNode(schIdAttr);
        buttonNameEl.addEventListener('click', onLoadSchedule);

        //sch content
        const pConEl = document.createElement('p');
        pConEl.textContent = "Sch content: " + sch.content;

        const liEl = document.createElement('li');
        liEl.appendChild(buttonNameEl);
        liEl.appendChild(pConEl);
        liEl.id="liEl"+sch.id;

        ulEl.appendChild(liEl);
    }
    return ulEl;
}

function loadSchedules() {
    showContents(["schedulesInfo", "goBackToMain"]);

    const schedules = JSON.parse(this.responseText);

    const divEl = document.getElementById('schedulesInfo');
    while(divEl.firstChild) {
        divEl.removeChild(divEl.firstChild);
    }
    //sch button to ADd sch
    const buttonAddSchEl = document.createElement('button');
    buttonAddSchEl.classList.add("button");
    buttonAddSchEl.textContent = "Add Sch";
    buttonAddSchEl.addEventListener('click', addSchedule);
    const schTitleEl = document.createElement("input");
    schTitleEl.id="schTitle";
    schTitleEl.placeholder="Schedule Title";
    const schContentEl = document.createElement("input");
    schContentEl.id="schContent";
    schContentEl.placeholder="Schedule Content";
    divEl.appendChild(scheduleList(schedules));
    divEl.appendChild(buttonAddSchEl);
    divEl.appendChild(schTitleEl);
    divEl.appendChild(schContentEl);
}

function addSchedule() {
    if(document.getElementById('schTitle').value!=""){
    const el = this;
    const schTitle = document.getElementById('schTitle').value;
    const schContent = document.getElementById('schContent').value;
    const userId = document.getElementById('actualUserId').value;
    const params = new URLSearchParams();
    params.append('schTitle', schTitle);
    params.append('schContent', schContent);
    params.append('userId', userId);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', mainSchButton);
    xhr.open('POST', 'schServlet');
    xhr.send(params);
    }else{
        alert("Title can't be empty");
    }
}




function mainSchButton() {
    const Id = userId;

    const params = new URLSearchParams();
    params.append('id', userId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loadSchedules);
    xhr.open('GET', 'protected/schServlets?' + params);
    xhr.send();
}
