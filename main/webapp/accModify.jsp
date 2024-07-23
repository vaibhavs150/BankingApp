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
								<div class="balance-container"></div>
							</section>
						</div>
						<div class="column-middle">
							<section>
								<div class="transaction">
									<div class="trans-container">
										<div class="title">
											<p>
												<span>Modify Customer Account</span>
											</p>
										</div>
										<div class="trans-info">
											<div class="container">
												<form action="Search1cutomer" method="post">
													<label for="search">Enter Account Number: </label> <input
														type="text" id="search" name="search" required />
													<button type="submit">Search account</button>
												</form>
												<hr
													style="margin-top: 20px; margin-bottom: 20px; background-color: black;">
												<h1
													style="font-size: 24px; font-weight: bold; text-align: center;">Modify</h1>
												<%
												if (request.getAttribute("customerDetails") != null) {
													String[] details = (String[]) request.getAttribute("customerDetails");
												%>
												<form id="modificationForm" action="Modification"
													method="post">

													<label for="fullname">Full Name:</label><br> <input
														type="text" id="fullname" name="fullname"
														value="<%=details[0]%>"><br> <br> <label
														for="address">Address:</label><br>
													<textarea id="address" name="address"
														value="<%=details[2]%>"></textarea>
													<br> <br> <label for="mobile">Mobile No:</label><br>
													<input type="text" id="mobile" name="mobile"
														value="<%=details[2]%>"><br> <br> <label
														for="email">Email id:</label><br> <input type="email"
														id="email" name="email" value="<%=details[3]%>"><br>
													<label for="accNo">Account Number:</label><br> <input
														type="text" id="accNo" name="accNo"
														value="<%=details[7]%>"><br> <br> <label
														for="dob">Date of Birth:</label><br> <input
														type="date" id="dob" name="dob" value="<%=details[5]%>"><br>
													<br> <label for="idProof">ID Proof:</label><br> <input
														type="text" id="idProof" name="idProof"
														value="<%=details[6]%>"><br> <br> <input
														type="submit" value="Submit">
												</form>
												<%
												}
												%>

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
