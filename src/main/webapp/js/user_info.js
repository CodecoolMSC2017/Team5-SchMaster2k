let userId;

function loadInformations() {


    const user = JSON.parse(this.responseText);
    userId = user.id;
    document.getElementById("userName").innerHTML ="Username: " + user.name;
    document.getElementById("userFName").innerHTML = "First name: " + user.fName;
    document.getElementById("userLName").innerHTML = "Last name: " + user.lName;
    document.getElementById("userEmail").innerHTML = "Email: " + user.email;

}

function userInfo(){
        const params = new URLSearchParams();
        const username = document.getElementById("actualUsername").value;
        params.append('username', username);
        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', loadInformations);
        xhr.open('POST', 'protected/userinfo');
        xhr.send(params);

}
