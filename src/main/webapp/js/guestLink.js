function deleteSharedSch(){
    const params = new URLSearchParams();
    params.append("userId", userId);
    params.append("schId", currentSchId);
    const xhr = new XMLHttpRequest();
    xhr.open('DELETE', 'guestLink');
    xhr.send(params);
}

function onUnShareButton(){
    showContents(["scheduleInfo", "goBackToSchMenu"]);
    const divEl = document.getElementById('guestLink');
    while(divEl.firstChild){
        divEl.removeChild(divEl.firstChild);
    }

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
    xhr.open('PUT', 'guestLink');
    xhr.send(params);
}

function guestButton() {
    const unSButton = document.getElementById("guestButton");
    unSButton.innerHTML = "Unshare Link";
    unSButton.removeEventListener("click", guestButton);
    unSButton.addEventListener("click", onUnShareButton);

    showContents(["scheduleInfo", "goBackToSchMenu", "guestLink"]);
    const divEl = document.getElementById('guestLink');
    while(divEl.firstChild){
        divEl.removeChild(divEl.firstChild);
    }
    const pEL = document.createElement('p');
    pEL.innerHTML = "http://localhost:8080/sch_master_2k/guestLink?id=" + userId + "&schid=" + currentSchId;
    safeSharedSch();
    divEl.appendChild(pEL);
}
