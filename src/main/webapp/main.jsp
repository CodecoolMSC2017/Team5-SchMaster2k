<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
	<head>
		<script type="text/javascript" src=""></script>
		<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
		<title>Schedule Master 2000</title>
	</head>
	<body style="background: url(images/background.jpg);">
        <div id="userInfo" class="content">
            <p id="userName"></p>
            <p id="userFName"></p>
            <p id="userLName"></p>
            <p id="userEmail"></p>
        </div>

        <div id="mainInfo" class="content">
            <p>Introduction</p>
            <p id="schNumber"></p>
            <p id="taskNumber"></p>
            <button id="mainSchButton">Schedule</button>
            <button id="mainTaskButton">Task</button>
        </div>



        <form action="loginServlet" method="GET">
            <input type="submit" value="logout">
        </form>
	</body>
</html>
