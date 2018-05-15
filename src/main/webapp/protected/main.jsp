<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
	<head>
		<title>Schedule Master 2000</title>
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<script type="text/javascript" src="js/user_info.js"></script>
		<script type="text/javascript" src="js/main_info.js"></script>

		<script type="text/javascript" src="js/main.js"></script>
	</head>
	<body>

        <div id="userInfo" class="content">
            <input type="hidden" id="actualUsername" value="${sessionScope.user.getName()}">
            <p id="userName"></p>
            <p id="userFName"></p>
            <p id="userLName"></p>
            <p id="userEmail"></p>
            <form action="loginServlet" method="GET">
                <input type="submit" value="Logout">
            </form>
        </div>

        <div id="mainInfo" class="content">
            <p>Introduction</p>
            <p id="schNumber"></p>
            <p id="taskNumber"></p>
            <button id="mainSchButton">Schedule</button>
            <button id="mainTaskButton">Task</button>
        </div>

	</body>
</html>