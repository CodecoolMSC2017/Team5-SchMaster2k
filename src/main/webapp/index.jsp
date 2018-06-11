<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
	<head>
		<link rel="stylesheet" type="text/css" href="css/login.css">
        <meta name="google-signin-client_id" content="135003808984-hnqdi8p354tcf71vf9fr0npibap8k2o3.apps.googleusercontent.com">
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script type="text/javascript" src="js/googleSignIn.js"></script>

		<title>Schedule Master 2000 - LOGIN</title>
	</head>
	<body>
		<div class="login">
			<div class="login_frame">
				<div class="logo"></div>
				<div class="title">Schedule<br>Master 2000</div>
				<div class="form">
					<form action="loginServlet" method="post">
						<input type="text" name="name_or_email" placeholder="User name or E-mail" value="">
						<input type="password" name="password" placeholder="Password" value="">
						<input type="submit" class="button" value="Login">
						<div class="g-signin2" data-onsuccess="onSignIn"></div>
						<br>
				    </form>
				    <div class="error">${error}</div>
				</div>
			</div>
		</div>
		<div class="registration">
			<div class="registration_frame">
				<h1>Sign Up</h1>
				<p style="margin-bottom: 20px">It's free and always will be.</p>
				<form action="RegistrationServlet" method="post">
					<p><input class="field" type="text" size="45" name="name" placeholder="User name" value="" required></p>
					<p><input class="field" type="text" size="19" name="first_name" placeholder="First name" value="">
					<input class="field" type="text" size="19" name="last_name" placeholder="Last name" value=""></p>
					<p><input class="field" type="email" size="45" name="email" placeholder="E-mail" value="" required></p>
					<p><input class="field" type="password" size="45" name="password" placeholder="Password" value="" required></p>
					<p>Birthday:<br>
					<input class="field" type="date" name="birthday"></p>
					<p style="font-size: 0.85em">By clicking Create Account, you agree to our Terms and that you have read our Data Policy, including our Cookie Use.</p>
					<p class="message">${message}</p>
					<p><input type="submit" class="button" value="Create Account"></p>
				</form>
			</div>
		</div>
	</body>
</html>
