function deleteSharedSch(){
    const params = new URLSearchParams();
    params.append("userId", userId);
    params.append("schId", currentSchId);
    const xhr = new XMLHttpRequest();
    xhr.open('DELETE', 'guestLink?' + params);
    xhr.send();
}

function onUnShareButton(){
    showContents(["scheduleInfo", "goBackToSchMenu"]);
    const divEl = document.getElementById('guestLink');
    while(divEl.firstChild){
        divEl.removeChild(divEl.firstChild);
    }
    deleteSharedSch();
    const sButton = document.getElementById("guestButton");
    sButton.innerHTML = "Share Link";
    sButton.removeEventListener("click", onUnShareButton);
    sButton.addEventListener("click", guestButton);
}

function safeSharedSch(){
    const params = new URLSearchParams();
    params.append("userId", userId);
    params.append("schId", currentSchId);
    const xhr = new XMLHttpRequest();
    xhr.open('PUT', 'guestLink?' + params);
    xhr.send();
}

function guestButton() {
    const unSButton = document.getElementById("guestButton");
    unSButton.innerHTML = "Unshare Link";
    unSButton.removeEventListener("click", guestButton);
    unSButton.addEventListener("click", onUnShareButton);
    if(wentFromAdmin==false){
    showContents(["scheduleInfo", "goBackToSchMenu", "guestLink"]);
    }
    const divEl = document.getElementById('guestLink');
    while(divEl.firstChild){
        divEl.removeChild(divEl.firstChild);
    }

    const encryptedUserId = btoa(userId);
    const encryptedCurrentSchId = btoa(currentSchId);

    const pEL = document.createElement('p');
    pEL.innerHTML = "http://localhost:8080/sch_master_2k/guestLink?id=" + encryptedUserId + "&schid=" + encryptedCurrentSchId;
    safeSharedSch();
    divEl.appendChild(pEL);
}
