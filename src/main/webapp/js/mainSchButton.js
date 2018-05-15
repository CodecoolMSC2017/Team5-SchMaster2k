function scheduleList(schedules) {
    const ulEl = document.createElement('ul');

    for (let i = 0; i < schedules.length; i++) {
        const sch = schedules[i];

        const pNameEl = document.createElement('p');
        pNameEl.textContent = "Sch name: " + sch.name;
        const pIdEl = document.createElement('p');
        pIdEl.textContent = "Sch id: " + sch.id;
        const pConEl = document.createElement('p');
        pConEl.textContent = "Sch content: " + sch.content;

        const liEl = document.createElement('li');
        liEl.appendChild(pNameEl);
        liEl.appendChild(pIdEl);
        liEl.appendChild(pConEl);

        ulEl.appendChild(liEl);
    }
    return ulEl;
}

function loadSchedules() {
    showContents(["schedulesInfo"]);

    const schedules = JSON.parse(this.responseText);

    const divEl = document.getElementById('schedulesInfo');
    while(divEl.firstChild) {
        divEl.removeChild(divEl.firstChild);
    }
    divEl.appendChild(scheduleList(schedules));
}



function mainSchButton() {
    const params = new URLSearchParams();
    params.append('id', userId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loadSchedules);
    xhr.open('POST', 'schServlet');
    xhr.send(params);
}
