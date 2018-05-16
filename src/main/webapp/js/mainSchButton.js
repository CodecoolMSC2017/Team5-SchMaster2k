function scheduleView(schedule){
    const pNameEl = document.createElement('p');
    pNameEl.textContent = "Sch name: " + schedule.name;
    pNameEl.appendChild(document.createTextNode(`: ${schedule.content}, ${schedule.id} `));

    return pNameEl;
}

function loadSchedule() {
    showContents(['scheduleInfo']);

    const schedule = JSON.parse(this.responseText);

    const divEl = document.getElementById('scheduleInfo');
    while(divEl.firstChild) {
        divEl.removeChild(divEl.firstChild);
    }
    divEl.appendChild(scheduleView(schedule));
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
