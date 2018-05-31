
function guestButton() {
    showContents(["scheduleInfo", "goBackToSchMenu", "guestLink"]);
    const divEl = document.getElementById('guestLink');
    while(divEl.firstChild){
        divEl.removeChild(divEl.firstChild);
    }
    const pEL = document.createElement('p');
    pEL.innerHTML = "http://localhost:8080/sch_master_2k/guestLink?id=" + userId + "&schid=" + currentSchId;
    divEl.appendChild(pEL);
}
