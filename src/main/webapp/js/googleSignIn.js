

function onSignIn(){
    const googleUser = gapi.auth2.getAuthInstance().currentUser.get();
    const profile = googleUser.getBasicProfile();

    const form = document.createElement("form");
    form.method="POST";
    form.action="googleLoginServlet";
    form.classList.add("hidden");
    const emailInput = document.createElement("input");
    const nameInput = document.createElement("input");
    emailInput.name="name_or_email";
    nameInput.name="full_name";
    emailInput.value=profile.getEmail();
    nameInput.value=profile.getName();
    form.appendChild(emailInput);
    form.appendChild(nameInput);

    document.getElementsByTagName("body")[0].appendChild(form);


    form.submit();





   /* const params = new URLSearchParams();
    params.append("name_or_email", profile.getEmail());
    params.append("full_name", profile.getName());
    const xhr = new XMLHttpRequest();
    xhr.addEventListener("load",onLoggedIn);
    xhr.open('POST', 'googleLoginServlet',false);
    xhr.send(params);*/


}

/*function onLoggedIn(){
    const url = JSON.parse(this.responseText);
    window.location.href = url.message;

}*/



