function userInfo() {
    const userName = document.getElementById("actualUser");
    const user = userName.value;
    usern = user.name;
    document.getElementById("userName").innerHTML = usern;
    document.getElementById("userFName").innerHTML = "First name: XXX";
    document.getElementById("userLName").innerHTML = "Last name: YYY";
    document.getElementById("userEmail").innerHTML = "Email: XXX@YYY.hu";
}
