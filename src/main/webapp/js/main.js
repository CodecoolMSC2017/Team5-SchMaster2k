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

function backToMain(){
    showContents(['mainInfo']);
}


function onLoad() {
    userInfo();
    mainInfo();
    backButtonEl = document.getElementById('goBackToMainButton');
    backButtonEl.addEventListener('click', backToMain);
}

window.onload = onLoad;
