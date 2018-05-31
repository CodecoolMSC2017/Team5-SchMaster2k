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
    divEl.appendChild(pEL);
}
