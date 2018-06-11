function onSignIn(){
    const googleUser = gapi.auth2.getAuthInstance().currentUser.get();
    const profile = googleUser.getBasicProfile();


    const params = new URLSearchParams();
    params.append("name_or_email", profile.getEmail());
    params.append("full_name", profile.getName());
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'loginServlet');
    xhr.send(params);


}
