function scheduleList(schedules)
    const ulEl = document.createElement('ul');

    for (let i = 0; i < schedules.length; i++) {
        const sch = schedules[i];

        // creating title
        const pNameEl = document.createElement('p');
        pNameEl.textContent = sch.name;

        // creating list item
        const liEl = document.createElement('li');
        liEl.appendChild(pNameEl);

        ulEl.appendChild(liEl);
    }
    return ulEl;


function loadSchedules() {
    //photosDivEl.style.display = 'block';

    const text = this.responseText;
    const schedules = JSON.parse(text);

    const divEl = document.getElementById('schedulesInfo')
    while(divEl.firstChild) {
        divEl.removeChild(divEl.firstChild);
    }
    divEl.appendChild(scheduleList(schedules));
}



function mainSchButton() {
    const params = new URLSearchParams();
    const id = userId;
    params.append('id', id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loadSchedules);
    xhr.open('GET', 'schServlet');
    xhr.send(params);
}
