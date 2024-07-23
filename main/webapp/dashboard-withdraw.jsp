<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Withdrawal</title>
<link rel="icon" type="image/png"
	href="pics/Graphicloads-Flat-Finance-Bank.256.png">
<link rel="stylesheet" type="text/css" href="dashboard-styles.css">
</head>
<body>
	<main>
		<section>
			<div class="container">
				<div class="ham-menu">
					<img src="pics/hamburgur.svg" id="btn" alt=""
						onclick="myFunction()" />
				</div>
				<div class="home-button">
					<img src="pics/home.svg" id="btn" alt=""
						onclick="window.location.href='custDashboard.jsp'" />
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
														<span><%=session.getAttribute("customerName")%></span>
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="balance-container">
									<div class="balance-text">
										<p>
											<span>Your balance is:</span>
										</p>
										<p>
											<span class="money"><%=session.getAttribute("balance")%>
												<span style="color: green; font-size: 30px">Rs.</span> </span>
										</p>
									</div>
								</div>
								<div class="account-info">
									<div class="info-text">
										<p style="margin-bottom: 10px">
											<span style="font-size: 20px">Account Information</span>
										</p>
										<p>
											<span>Account Type: <%=session.getAttribute("accountType")%></span>
										</p>
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
												<span>Withdraw Money</span>
											</p>
										</div>
										<div class="trans-info">
											<div class="container">
												<form action="WithDrawServlet" method="post">
													<label for="accNo">Account number</label> <input
														type="number" id="AccNo" name="AccNo"
														value="<%=session.getAttribute("loggedInAccNo")%>"
														required readonly /> <label for="bill">Enter
														Withdrawal amount</label> <input type="number" id="bill"
														name="bill" required />
													<button type="submit">Withdraw</button>
												</form>
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
												<span>Card & Tools</span>
											</p>
										</div>
										<div class="card-visual">
											<div class="card-pic">
												<div class="card-brand">
													<p>
														<span>Veltech Bank</span>
													</p>
												</div>
												<div class="chip">
													<img src="pics/chip.png" alt="" />
												</div>
												<div class="name">
													<p>
														<span><%=session.getAttribute("customerName")%></span>
													</p>
												</div>
												<div class="card-num" id="card-number">
													<p>
														<span>9988 </span><span> 7766 </span><span> 5544 </span><span>
															3322</span>
													</p>
												</div>
												<div class="card-exp-cvv">
													<p>
														<span style="float: left">exp: 2024/07</span><span
															style="float: right">cvv2: 999</span>
													</p>
												</div>
											</div>
										</div>
										<div class="tools-container">
											<div class="tools-row">
												<div class="tools-column">
													<div class="option"
														onclick="window.location.href='dashboard-withdraw.jsp'">
														<div class="container">
															<div class="logo">
																<img src="pics/transfer.svg" alt="" />
															</div>
															<div class="text">
																<p>Withdraw</p>
															</div>
														</div>
													</div>
													<div class="option"
														onclick="window.location.href='dashboard-deposit.jsp'">
														<div class="container">
															<div class="logo">
																<img src="pics/bill.svg" alt="">
															</div>
															<div class="text">
																<p>Deposit</p>
															</div>
														</div>
													</div>
													<div class="option"
														onclick="window.location.href='dashboard-closeAcc.jsp'">
														<div class="container">
															<div class="logo">
																<img src="pics/disable.svg" alt="">
															</div>
															<div class="text">
																<p>Close Account</p>
															</div>
														</div>
													</div>
												</div>
												<div class="tools-column">
													<div class="option" id="copy-card">
														<div class="container">
															<div class="logo" style="height: 46px; width: 25px;">
																<img src="pics/copy.svg" alt=""
																	style="height: 20px; width: 20px;">
															</div>
															<div class="text">
																<p>Copy Card Number</p>
															</div>
														</div>
													</div>

													<div class="option"
														onclick="window.location.href='dashboard-changepass.jsp'">
														<div class="container">
															<div class="logo">
																<img src="pics/password.svg" alt="">
															</div>
															<div class="text">
																<p>Change Password</p>
															</div>
														</div>
													</div>
													<div class="option"
														onclick="window.location.href='dashboard-logout.jsp'">
														<div class="container">
															<div class="logo">
																<img src="pics/password.svg" alt="">
															</div>
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
	<footer></footer>
	<script src="js/dashboard-js.js"></script>
</body>
</html>