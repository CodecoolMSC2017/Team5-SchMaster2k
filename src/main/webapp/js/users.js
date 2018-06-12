function createTaskList(taskList){
    const ulEl = document.createElement('ol');

    for(let i=0; i<taskList.length; i++){
        const task = taskList[i];
        const liEl = document.createElement('li');
        liEl.textContent = task.name;
        ulEl.appendChild(liEl);
    }
    return ulEl;
}

function createSchList(schList){
    const ulEl = document.createElement('ol');

    for(let i=0; i<schList.length; i++){
        const sch = schList[i];
        const liEl = document.createElement('li');
        liEl.textContent = sch.name;
        ulEl.appendChild(liEl);

    }
    return ulEl;
}

function getAllSchAndTaskPerUser(){
    const schAndTask = JSON.parse(this.responseText);
    const id = schAndTask.userId;
    const schList = schAndTask.schList;
    const taskList = schAndTask.taskList;

    console.log(schList);
    console.log(schAndTask);
    const schDivEl = document.getElementById('sch' + id);
    const taskDivEl = document.getElementById('task' + id);

    while(schDivEl.firstChild){
        schDivEl.removeChild(schDivEl.firstChild);
    }
    while(taskDivEl.firstChild){
        taskDivEl.removeChild(taskDivEl.firstChild);
    }

    schDivEl.innerHTML = 'Schedules';
    schDivEl.style.cssFloat = 'left';
    schDivEl.appendChild(createSchList(schList));
    taskDivEl.innerHTML = 'Tasks';
    taskDivEl.style.cssFloat = 'right';
    taskDivEl.appendChild(createTaskList(taskList));
}

function showUserProfile(){
    const userId = this.getAttribute("userId");
    showContents(['mainInfo','userInfo', 'users', userId]);

    const params = new URLSearchParams();
    params.append("userId", userId);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', getAllSchAndTaskPerUser);
    xhr.open('POST', 'protected/allUserInfoServlet');
    xhr.send(params);
}

function addUsersHeaderToTable(){
    const trEl = document.createElement('tr');

    const thIdEl = document.createElement('th');
    thIdEl.innerHTML = 'User ID';
    trEl.appendChild(thIdEl);
    const thNameEl = document.createElement('th');
    thNameEl.innerHTML = 'User Name';
    trEl.appendChild(thNameEl);
    const thFirstNameEl = document.createElement('th');
    thFirstNameEl.innerHTML = 'First Name';
    trEl.appendChild(thFirstNameEl);
    const thLastNameEl = document.createElement('th');
    thLastNameEl.innerHTML = 'Last Name';
    trEl.appendChild(thLastNameEl);
    const thEmailEl = document.createElement('th');
    thEmailEl.innerHTML = 'Email';
    trEl.appendChild(thEmailEl);
    const thRankEl = document.createElement('th');
    thRankEl.innerHTML = 'Rank';
    trEl.appendChild(thRankEl);

    return trEl;
}

function addUserToList(users){
     const tableEl = document.createElement('table');
     tableEl.id = 'users_table';

     tableEl.appendChild(addUsersHeaderToTable());

     for(let i=0; i<users.length; i++){
        const user = users[i];

        const trEl = document.createElement('tr');
        const divTrEl = document.createElement('tr');

        const divTdEl = document.createElement('td');
        divTrEl.appendChild(divTdEl);

        const idAtrEl = document.createAttribute('userId');
        idAtrEl.value = user.id;
        trEl.setAttributeNode(idAtrEl);
        trEl.addEventListener('click', showUserProfile)

        const tdIdEl = document.createElement('td');
        tdIdEl.innerHTML = user.id;
        trEl.appendChild(tdIdEl);

        const tdNameEl = document.createElement('td');
        tdNameEl.innerHTML = user.name;
        trEl.appendChild(tdNameEl);

        const tdFirstNameEl = document.createElement('td');
        tdFirstNameEl.innerHTML = user.fName;
        trEl.appendChild(tdFirstNameEl);

        const tdLastNameEl = document.createElement('td');
        tdLastNameEl.innerHTML = user.lName;
        trEl.appendChild(tdLastNameEl);

        const tdEmailEl = document.createElement('td');
        tdEmailEl.innerHTML = user.email;
        trEl.appendChild(tdEmailEl);

        const tdRankEl = document.createElement('td');
        tdRankEl.innerHTML = user.rank;
        trEl.appendChild(tdRankEl);

        const divSchandTaskEl = document.createElement('div');

        const divSchEl = document.createElement('div');
        divSchEl.id = 'sch' + user.id;
        divSchEl.classList.add('users_sch_task');
        divSchandTaskEl.appendChild(divSchEl);

        const divTaskEl = document.createElement('div');
        divTaskEl.id = 'task' + user.id;
        divTaskEl.classList.add('users_sch_task');
        divSchandTaskEl.appendChild(divTaskEl);
        divSchandTaskEl.classList.add('list-div-center');

        tableEl.appendChild(trEl);
        divTrEl.id = user.id;
        divTrEl.classList.add("hidden");
        divTrEl.classList.add("content");
        divTrEl.classList.add("no-td-style");
        divTdEl.colSpan = 6;
        divTdEl.appendChild(divSchandTaskEl);
        tableEl.appendChild(divTrEl);
     }

     return tableEl;
}

function loadAllUsers(){
    showContents(['mainInfo','userInfo', 'users']);

    const users = JSON.parse(this.responseText);
    const divEl = document.getElementById('usersList');

    while(divEl.firstChild){
        divEl.removeChild(divEl.firstChild);
    }
    divEl.appendChild(addUserToList(users));
}

function usersButton(){
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loadAllUsers);
    xhr.open('GET', 'protected/allUserInfoServlet');
    xhr.send();
}
