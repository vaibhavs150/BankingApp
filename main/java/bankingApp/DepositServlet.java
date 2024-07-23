package bankingApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve parameters from the request
		String accNo = request.getParameter("AccNo");
		double depositAmount = Double.parseDouble(request.getParameter("bill"));

		String loggedInAccNo = (String) request.getSession().getAttribute("loggedInAccNo");

		if (loggedInAccNo == null || !loggedInAccNo.equals(accNo)) {
			response.getWriter().println("You are not authorized to perform this action.");
			return;
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		// Load the MySQL JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			// Log the exception
			ex.printStackTrace();

			// Send a detailed error message back to the client
			response.getWriter().println("MySQL JDBC driver not found. Please ensure it's included in your project.");
			return; // Exit the method if driver loading fails
		}

		try {
			// Connect to the database
			conn = DBUtil.getConnection();

			// Query to retrieve the existing balance
			String selectQuery = "SELECT balance FROM customer WHERE acc_no=?";
			stmt = conn.prepareStatement(selectQuery);
			stmt.setString(1, accNo);
			rs = stmt.executeQuery();

			// If account number exists
			if (rs.next()) {
				double existingBalance = rs.getDouble("balance");
				double newBalance = existingBalance + depositAmount;

				// Update balance in the database
				String updateQuery = "UPDATE customer SET balance=? WHERE acc_no=?";
				stmt = conn.prepareStatement(updateQuery);
				stmt.setDouble(1, newBalance);
				stmt.setString(2, accNo);
				int rowsAffected = stmt.executeUpdate();
				HttpSession session = request.getSession();
				session.setAttribute("balance", newBalance);

				// Insert transaction record
				String transactionQuery = "INSERT INTO transaction (acc_no, amount, type, date) VALUES (?, ?, ?, ?)";
				stmt = conn.prepareStatement(transactionQuery);
				stmt.setString(1, accNo);
				stmt.setString(2, String.valueOf(depositAmount));
				stmt.setString(3, "Deposit");
				// Get current date and time
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formattedDateTime = now.format(formatter);
				stmt.setString(4, formattedDateTime);
				stmt.executeUpdate(); // Insert transaction record

				if (rowsAffected > 0) {

					List<Transaction> recentTransactions = getRecentTransactions(conn, accNo);

					session.setAttribute("recentTransactions", recentTransactions);

					session.setAttribute("transactionType", "Withdrawal");
					session.setAttribute("transactionDate", formattedDateTime);
					session.setAttribute("transactionAmount", depositAmount);

					response.setContentType("text/html");
					PrintWriter out1 = response.getWriter();
					out1.println("<!DOCTYPE html>");
					out1.println("<html>");
					out1.println("<head>");
					out1.println("<title>Success</title>");
					out1.println("<script src=\"https://cdn.jsdelivr.net/npm/sweetalert2@11\"></script>");
					out1.println(
							"<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css\">");
					out1.println("</head>");
					out1.println("<body>");
					out1.println("<script>");
					out1.println("Swal.fire({");
					out1.println("  title: 'Success',");
					out1.println("  text: 'Deposit Successful ',");
					out1.println("  icon: 'success'");
					out1.println("}).then(() => { window.location.href='dashboard-deposit.jsp'; });");
					out1.println("</script>");
					out1.println("</body>");
					out1.println("</html>");
					out1.close();
				} else {
					response.setContentType("text/html");
					PrintWriter out1 = response.getWriter();
					out1.println("<!DOCTYPE html>");
					out1.println("<html>");
					out1.println("<head>");
					out1.println("<title>Error</title>");
					out1.println("<script src=\"https://cdn.jsdelivr.net/npm/sweetalert2@11\"></script>");
					out1.println(
							"<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css\">");
					out1.println("</head>");
					out1.println("<body>");
					out1.println("<script>");
					out1.println("Swal.fire({");
					out1.println("  title: 'error',");
					out1.println("  text: 'Deposit failed, please try again ',");
					out1.println("  icon: 'error'");
					out1.println("}).then(() => { window.location.href='dashboard-deposit.jsp'; });");
					out1.println("</script>");
					out1.println("</body>");
					out1.println("</html>");
					out1.close();
				}
			} else {
				response.getWriter().println("Account number not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println("Error: " + e.getMessage());
		}
	}

	private List<Transaction> getRecentTransactions(Connection conn, String accNo) throws SQLException {
		List<Transaction> transactions = new ArrayList<>();
		String transactionQuery = "SELECT * FROM transaction WHERE acc_no=? ORDER BY date DESC LIMIT 10";
		try (PreparedStatement stmt = conn.prepareStatement(transactionQuery)) {
			stmt.setString(1, accNo);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String type = rs.getString("type");
					String date = rs.getString("date");
					String amount = rs.getString("amount");
					transactions.add(new Transaction(type, date, amount));
				}
			}
		}
		return transactions;
	}

}
