function onUserResponse(){
  if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        loadContent(user);
    } else {
        onOtherResponse(loginContentDivEl, this);
    }
}

function loadUserContents(user){
    userNameEl.innerHTML = user.name;
    userFNameEl.innerHTML = user.fName;
    userLNameEl.innerHTML = user.lName;
    userEmailEl.innerHTML = user.email;
}


function onLoad(){
    const userNameEl = document.getElementById("userName");
    const userFNameEl = document.getElementById("userFName");
    const userLNameEl = document.getElementById("userLName");
    const userEmailEl = document.getElementById("userEmail");
    const userEl = document.getElementById("userHiddenInput");
    const user = userEl.value;

    const params = new URLSearchParams();
    params.append('user', user);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUserResponse);
    xhr.open('POST', 'loadContent');
    xhr.send(params);
}


document.addEventListener('DOMContentLoaded', onLoad);
