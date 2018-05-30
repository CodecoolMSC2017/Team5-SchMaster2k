<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
	<head>
		<title>Schedule Master 2000</title>
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<script type="text/javascript" src="js/guest.js"></script>
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
                    <input type="hidden" id="actualUserId" value="${sessionScope.user.getId()}">
                    <span id="userName"></span><br>
                    <span id="userFName" style="display: none"></span>
                    <span id="userLName" style="display: none"></span>
                    <span id="userEmail"></span>
                    <form action="loginServlet" method="GET">
                        <input type="submit" class="button" value="Logout">
                    </form>
				</div>
			</div>
		</header>

		<div id="schModal" class="modal">
            <div class="modal-content">
                <span id="closeModal">&times;</span>
                <table id="tasksForSch">
                </table>

                <button id="deleteTaskFromSch" class = "hidden">Delete</button>

            </div>
		</div>
		<div class="data_frame">

            <div id="goBackToMain" class="hidden content">
                <button id="goBackToMainButton" class="button">Go Back</button>
            </div>

            <div id="goBackToSchMenu" class="hidden content">
                <button id="goBackToSchMenuButton" class="button">Go Back</button>
                <button id="guestButton" class="button">Sharable Link</button>
            </div>

            <div id="guestLink" class="hidden content">

            </div>

            <div id="mainInfo" class="content">
                <p>In this wonderful web-application users can create multiple schedules for themselves in which they can create tasks which can contain text or pictures sharing it with others as they like! Huge breakthrough ideas fellas!</p>
                <p id="schNumber"></p>
                <p id="taskNumber"></p>
                <button id="mainSchButton"  class="button">Schedule</button>
                <button id="mainTaskButton"  class="button">Task</button>
            </div>

            <div id="schedulesInfo" class="hidden content">
                <h2>Schedules</h2>

            </div>

            <div id="scheduleInfo" class="hidden content">
                <h3>Schedule</h3>
                <div id="testDivForTable">
                </div>
            </div>

            <div id="tasks" class="hidden content">
                <table id="taskTable" >
                    <tr>
                        <td>Task Name</td><td></td>
                    </tr>
                <table>
                <input id = "newTaskName" type="text">
                <button id="addTaskButton" class="button">Add Task</button>
            </div>

	</body>
</html>
