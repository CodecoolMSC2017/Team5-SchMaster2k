<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
	<head>
		<script type="text/javascript" src=""></script>
		<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
		<title>Schedule Master 2000 - LOGIN</title>
	</head>
	<body style="background: url(images/background_bw.jpg);">
		<div class="login">
			<div class="login_frame">
				<div class="logo"></div>
				<div class="title">Schedule<br>Master 2000</div>
				<div class="form">
					<form action="loginServlet" method="post">
						<input type="text" name="name_or_email" placeholder="User name or E-mail" value="">
						<input type="password" name="password" placeholder="Password" value="">
						<input type="submit" class="submit" value="Login">
					</form>
				</div>
			</div>
		    <div class="errormsg">"${error}"</div>
		</div>
		<div class="registration">
			<div class="registration_frame">
				<h1>Sign Up</h1>
				<p style="margin-bottom: 20px">It's free and always will be.</p>
				<form action="registrationServlet" method="post">
					<p><input class="field" type="text" size="45" name="name" placeholder="User name" value="" required></p>
					<p><input class="field" type="text" size="19" name="first_name" placeholder="First name" value="">
					<input class="field" type="text" size="19" name="last_name" placeholder="Last name" value=""></p>
					<p><input class="field" type="email" size="45" name="email" placeholder="E-mail" value="" required></p>
					<p><input class="field" type="password" size="45" name="password" placeholder="Password" value="" required></p>
					<p>Birthday:<br>
					<input class="field" type="date" name="birthday"></p>
					<p style="font-size: 0.85em">By clicking Create Account, you agree to our Terms and that you have read our Data Policy, including our Cookie Use.</p>
					<p style="font-size: 0.85em; color: #ff0000">${message}</p>
					<p><input type="submit" class="submit" value="Create Account"></p>
				</form>
			</div>
		</div>
	</body>
</html>
