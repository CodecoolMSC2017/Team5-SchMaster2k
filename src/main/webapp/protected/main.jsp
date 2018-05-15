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
		<script type="text/javascript" src="js/mainSchButton.js"></script>
		<script type="text/javascript" src="js/mainTaskButton.js"></script>

		<script type="text/javascript" src="js/main.js"></script>
	</head>
	<body>
		<header>
			<div class="header_frame">
				<div class="logo"></div>
				<div class="title">Schedule<br>Master 2000</div>
				<div class="user_info profile" id="userInfo">
                    <input type="hidden" id="actualUsername" value="${sessionScope.user.getName()}">
                    <span id="userName"></span><br>
                    <span id="userFName" style="display: none"></span>
                    <span id="userLName" style="display: none"></span>
                    <span id="userEmail"></span>
                    <form action="loginServlet" method="GET">
                        <input type="submit" value="Logout">
                    </form>
				</div>
			</div>
		</header>
		<div class="data_frame">

            <div id="goBackToMain" class="hidden content">
                <button id="goBackToMainButton">Go Back</button>
            </div>

            <div id="mainInfo" class="content">
                <p>Introduction</p>
                <p id="schNumber"></p>
                <p id="taskNumber"></p>
                <button id="mainSchButton">Schedule</button>
                <button id="mainTaskButton">Task</button>
            </div>

            <div id="schedulesInfo" class="hidden content">
                <h2>Schedules</h2>
            </div>

            <div id="scheduleInfo" class="hidden content">
                <h3>Schedule</h3>
            </div>

            <div id="tasks" class="hidden content">
                <table id="taskTable" border='1'>
                    <tr>
                        <td>Task Name</td><td></td>
                    </tr>
                <table>
            </div>

        </div>

        <div id="tasks" class="hidden content">
            <table id="taskTable" border='1'>
                <tr>
                    <td>Task Name</td><td></td>
                </tr>
            <table>
        </div>

	</body>
</html>
