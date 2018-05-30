let backButtonEl;

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function showSchContent(id) {
    const contentEls = document.getElementsByClassName('sch');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (id == contentEl.id) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function backToMain(){
    getNuOfTaskSch();
    showContents(['mainInfo','userInfo']);
}

function backToSchMenu() {
    showContents(["schedulesInfo", "goBackToMain"]);
}


function onLoad() {
    userInfo();
    mainInfo();
    backButtonEl = document.getElementById('goBackToMainButton');
    backButtonEl2 = document.getElementById('goBackToSchMenuButton');
    backButtonEl3 = document.getElementById('guestButton');
    backButtonEl.addEventListener('click', backToMain);
    backButtonEl2.addEventListener('click', mainSchButton);
    backButtonEl3.addEventListener('click', guestButton);
}

window.onload = onLoad;
