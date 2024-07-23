<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="bankingApp.CustomerDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Login</title>
<link rel="icon" type="image/png" href="pics/Graphicloads-Flat-Finance-Bank.256.png">
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
					<form action="CustLogin" method="post">
						<label for="accNo">Account Number</label> <input type="text"
							id="accNo" name="accNo" required /> <label for="pin">PIN</label>
						<input type="password" id="pin" name="pin" required />
						<button type="submit">Login</button>
					</form>

				</div>
			</div>
		</section>
	</main>
</body>
</html>
