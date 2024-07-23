<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
<link rel="icon" type="image/png"
	href="pics/Graphicloads-Flat-Finance-Bank.256.png">
<link rel="stylesheet" type="text/css" href="dashboard-styles.css">
</head>
<body>
	<header></header>
	<main>
		<section>
			<div class="container">
				<div class="ham-menu">
					<img src="pics/hamburgur.svg" id="btn" alt=""
						onclick="myFunction()" />
				</div>
				<div class="home-button">
					<img src="pics/home.svg" id="btn" alt=""
						onclick="window.location.href='adminDashboard.jsp'" />
				</div>
				<div class="row">
					<div class="row">
						<div class="column-left" id="column-left">
							<section>
								<div class="profile">
									<div class="container-2">
										<div class="photo">
											<div class="img">
												<img src="pics/user.png" alt="" />
											</div>
										</div>
										<div class="name">
											<div class="container-3">
												<div class="text">
													<p class="hello">
														<span>Welcome back,</span>
													</p>
													<p class="fname">
														<span>Admin</span>
													</p>
												</div>
											</div>
										</div>
										<hr>

										<div class="button-container" style="text-align: center;">
											<div onclick="window.location.href='index.jsp'"
												style="cursor: pointer; display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; border: none; border-radius: 5px; text-align: center; text-decoration: none;">
												<p style="margin: 0;">Home</p>
											</div>
										</div>
									</div>
								</div>
							</section>
						</div>
						<div class="column-middle">
							<section>
								<div class="transaction">
									<div class="trans-container">
										<div class="title">
											<p>
												<span>Customer Details</span>
											</p>
										</div>
										<div class="trans-info">
											<div class="container">
												<form action="SearchCustomerServlet" method="post">
													<label for="search">Enter Account Number: </label> <input
														type="text" id="search" name="search" required />
													<button type="submit">See account details</button>
												</form>
											</div>
											<div class="Detailcontainer">
												<table border="1">
													<tr>
														<th>Name</th>
														<th>Address</th>
														<th>Mobile Number</th>
														<th>Email</th>
														<th>Account Type</th>
														<th>Date of Birth</th>
														<th>ID Proof</th>
													</tr>
													<!-- Customer details will be inserted here -->
													<%
													if (request.getAttribute("customerDetails") != null) {
														String[] details = (String[]) request.getAttribute("customerDetails");
													%>
													<tr>
														<td><%=details[0]%></td>
														<td><%=details[1]%></td>
														<td><%=details[2]%></td>
														<td><%=details[3]%></td>
														<td><%=details[4]%></td>
														<td><%=details[5]%></td>
														<td><%=details[6]%></td>
													</tr>
													<%
													} else {
													%>
													<tr>
														<td colspan="7">No customer found with the provided
															account number</td>
													</tr>
													<%
													}
													%>
												</table>
											</div>
										</div>
									</div>
								</div>
							</section>
						</div>
						<div class="column-right">
							<section>
								<div class="card">
									<div class="card-container">
										<div class="title">
											<p>
												<span>Operations</span>
											</p>
										</div>
										<div class="tools-container">
											<div class="tools-row">
												<div class="tools-column">
													<div class="option"
														onclick="window.location.href='adminDashboard.jsp'">
														<div class="container">
															<div class="text">
																<p>Add Customer</p>
															</div>
														</div>
													</div>
													<div class="option"
														onclick="window.location.href='adminDelete.jsp'">
														<div class="container">
															<div class="text">
																<p>Delete Customer</p>
															</div>
														</div>
													</div>
													<div class="option"
														onclick="window.location.href='accModify.jsp'">
														<div class="container">
															<div class="text">
																<p>Modify Account</p>
															</div>
														</div>
													</div>
												</div>
												<div class="tools-column">

													<div class="option" id="copy-card"
														onclick="window.location.href='custDetails.jsp'">
														<div class="container">
															<div class="text">
																<p>See Details</p>
															</div>
														</div>
													</div>
													<div class="option"
														onclick="window.location.href='adminLogout.jsp'">
														<div class="container">
															<div class="text">
																<p>Logout</p>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>
				</div>
			</div>
		</section>
	</main>
	<script src="js/dashboard-js.js"></script>
</body>
</html>
