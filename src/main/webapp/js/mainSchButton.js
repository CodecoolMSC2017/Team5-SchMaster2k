let currentSchId;
let currentDayHourId;
let previousText;
let hashMap;
let taskLength;


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
    divEl.id=scheduleInfo.schedule.id;

    divEl.classList.add('sch');
    showSchContent(scheduleInfo.schedule.id);
    const liEl = document.getElementById('liEl'+scheduleInfo.schedule.id);
    while(liEl.firstChild.nextSibling){
        liEl.removeChild(liEl.firstChild.nextSibling);
    }

    showContents(["schedulesInfo", "goBackToMain"]);

    const viewButton = document.createElement("button");
    viewButton.innerHTML="View";

    viewButton.addEventListener("click", getDetailedSch);
    viewButton.id=scheduleInfo.schedule.id;

    liEl.appendChild(divEl);
    divEl.appendChild(viewButton);
    divEl.appendChild(scheduleView(scheduleInfo));
    divEl.appendChild(scheduleDays(scheduleInfo));

}



function getDetailedSch(){
    const params = new URLSearchParams();
    if(this.innerHTML=="View"){
        currentSchId=this.id;
    }
    params.append("schId", currentSchId);

    params.append("userId", document.getElementById("actualUserId").value);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', showDetailedSch);
    xhr.open('GET', 'protected/schAllInformation?' + params);
    xhr.send();

}

function showDetailedSch(){

    showContents(["scheduleInfo", "goBackToSchMenu"]);


    if(document.getElementById("testDivForTable").firstChild){
        document.getElementById("testDivForTable").removeChild(document.getElementById("testDivForTable").firstChild);
    }

    const hm = JSON.parse(this.responseText);
    hashMap=hm;
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
                td.innerHTML = r + '-' + ++r;
                --r;
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

              //console.log(keys[i]);
              tdAppend.innerHTML = hm[keys[i]].name;
              tdAppend.style.backgroundColor = "#93daf9";


      }

    for(let k = 1;k<8;k++){
        for(let l = 0;l<23;l++){

            if(document.getElementById(days[k]+String(l)).innerHTML == document.getElementById(days[k]+String(l+1)).innerHTML){
               document.getElementById(days[k]+String(l)).style.backgroundColor="#c6f993";
               document.getElementById(days[k]+String(l+1)).style.backgroundColor="#c6f993";

            }
        }
    }

    }



function showAddButton(){
    if(this.firstChild.nodeName=="BUTTON"){
        this.firstChild.classList.remove("hidden");
    }else{
        previousText = this.innerHTML;
        this.innerHTML = "";
        const deleteButton = document.createElement("button");
        deleteButton.innerHTML = "Delete";
        deleteButton.classList.add("button");
        deleteButton.addEventListener("click", deleteTaskFromSch);
        this.appendChild(deleteButton);


    }
}

function deleteTaskFromSch(){

    const params = new URLSearchParams();
    params.append('schId', currentSchId);
    params.append('dayHour',this.parentElement.id);
    params.append('taskId', hashMap[this.parentElement.id].id);
    params.append('userId',document.getElementById("actualUserId").value);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', getDetailedSch);
    xhr.open('POST', 'protected/taskFromSchServlet');
    xhr.send(params);

  }


function hideAddButton(){
        if(this.firstChild.nodeName=="BUTTON" && this.firstChild.innerHTML != "Delete"){
               this.firstChild.classList.add("hidden");
           }else if(this.firstChild.nodeName=="BUTTON" && this.firstChild.innerHTML == "Delete"){
                this.removeChild(this.firstChild);
                this.innerHTML = previousText;
          }
}

function addTaskToSch(){
    const username = document.getElementById("actualUsername").value;
    currentDayHourId = this.id;

    const params = new URLSearchParams();
    params.append('username', username);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', showAvailableTasks);
    xhr.open('POST', 'protected/taskServlet');
    xhr.send(params);


}

function showAvailableTasks(){
    const tasks = JSON.parse(this.responseText);
    const table = document.getElementById("tasksForSch");


   while(table.firstChild) {
           table.removeChild(table.firstChild);
   }


   const hourTr = document.createElement("tr");
   const hourTextTd = document.createElement("td");
   const textField = document.createElement("input");
   textField.id="hourText";
   textField.placeholder="Length of task(Hr)";
   hourTextTd.appendChild(textField);
   hourTr.appendChild(hourTextTd);
   table.appendChild(hourTr);


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

    if(document.getElementById("hourText").value == null || document.getElementById("hourText").value == ""){
        taskLength = 1;
    }else{
        taskLength = parseInt(document.getElementById("hourText").value);
    }

    const startingHour = parseInt(currentDayHourId.substring(2,currentDayHourId.length));
    const endHour =startingHour + parseInt(document.getElementById("hourText").value);
    const day = currentDayHourId.substring(0,2);
    let canAppend = true;

    if(!(parseInt(document.getElementById("hourText").value)>23-startingHour)){

        for(let i = startingHour; i<endHour;i++){
                if(document.getElementById(day+String(i)).firstChild.nodeName != "BUTTON" && document.getElementById(day+String(i)).innerHTML!=null){
                    alert("This task would overlap an other one. Chose an other place, or length for this task");
                    canAppend=false;
                    break;
                }
        }

        if(canAppend==true){
            const modalDiv = document.getElementById("schModal");
            modalDiv.style.display = "none";
            const params = new URLSearchParams();
            params.append('schId', currentSchId);
            params.append('dayHour',currentDayHourId);
            params.append('taskLength', taskLength);
            params.append('taskId',this.id);
            params.append("userId", document.getElementById("actualUserId").value);
            document.getElementById("schModal").display = "none";
            const xhr = new XMLHttpRequest();
            xhr.addEventListener('load', getDetailedSch);
            xhr.open('POST', 'protected/taskToSchServlet');
            xhr.send(params);
        }

    }else{
        alert("That task is too long for a day with a starting hour of " + startingHour);
    }


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
    xhr.open('GET', 'protected/schServlet?' + params);
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

        const liEl = document.createElement('li');
        liEl.appendChild(buttonNameEl);
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
    xhr.open('POST', 'protected/schServlet');
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
