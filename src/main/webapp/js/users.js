function addUserToList(users){
    const ulEl = document.createElement('ul');

    for(let i=0; users.length > i; i++){
        const user = users[i];
        console.log(user.name);
        const liEl = document.createElement('li');
        liEl.innerHTML = user.name;
        ulEl.appendChild(liEl);
    }
    return ulEl;
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
