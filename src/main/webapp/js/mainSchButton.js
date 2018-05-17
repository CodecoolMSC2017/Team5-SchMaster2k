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
    showContents(['scheduleInfo']);

    const scheduleInfo = JSON.parse(this.responseText);

    const divEl = document.getElementById('scheduleInfo');
    while(divEl.firstChild) {
        divEl.removeChild(divEl.firstChild);
    }
    divEl.appendChild(scheduleView(scheduleInfo));
    divEl.appendChild(scheduleDays(scheduleInfo));
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
        buttonNameEl.textContent = sch.name;
        buttonNameEl.setAttributeNode(schIdAttr);
        buttonNameEl.addEventListener('click', onLoadSchedule);

        //sch content
        const pConEl = document.createElement('p');
        pConEl.textContent = "Sch content: " + sch.content;

        const liEl = document.createElement('li');
        liEl.appendChild(buttonNameEl);
        liEl.appendChild(pConEl);

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
    buttonAddSchEl.textContent = "Add Sch";
    buttonAddSchEl.addEventListener('click', onLoadSchedule);

    divEl.appendChild(scheduleList(schedules));
    divEl.appendChild(buttonAddSchEl);
}



function mainSchButton() {
    const Id = userId;

    const params = new URLSearchParams();
    params.append('id', userId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loadSchedules);
    xhr.open('GET', 'schServlets?' + params);
    xhr.send();
}
