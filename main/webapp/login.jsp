<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="bankingApp.CustomerDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Login</title>
<link rel="stylesheet" type="text/css" href="css/login-styles.css">
</head>
<body>
	<header>
		<h1 onclick="window.location.href='index.jsp'">bank</h1>
	</header>
	<main>
		<section>
			<div class="container">
				<div class="login">
					<p class="title">
						<span>Login to your Account</span>
					</p>
					<form action="AdminLogin" method="post">
						<label for="username">Username</label> <input type="text"
							id="username" name="username" required /> <label for="password">Password</label>
						<input type="password" id="password" name="password" required />
						<button type="submit">Login</button>
					</form>

				</div>
			</div>
		</section>
	</main>
</body>
</html>
