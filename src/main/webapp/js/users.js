function showUserProfile(){
    const id = this.getAttribute("userId");
    console.log("Ittvagyook " + id);

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

        tableEl.appendChild(trEl);
     }

     return tableEl;
}

function loadAllUsers(){
    showContents(['mainInfo','userInfo', 'users'])

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
