package bankingApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/GetUpdatedBalanceServlet")
public class GetUpdatedBalanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get customer details from session
		HttpSession session = request.getSession();
		String accNo = (String) session.getAttribute("accNo");

		// Logic to retrieve updated balance from the database based on accNo
		String updatedBalance = retrieveUpdatedBalanceFromDatabase(accNo);

		// Send the updated balance as the response
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(updatedBalance);
		out.flush();
	}

	// Method to retrieve updated balance from the database
	private String retrieveUpdatedBalanceFromDatabase(String accNo) {
		String updatedBalance = ""; // Updated balance variable

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// Connect to the database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingapp", "root", "password");

			// Prepare SQL query
			String query = "SELECT balance FROM customer WHERE acc_no=?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, accNo);

			// Execute query
			rs = stmt.executeQuery();

			// Process result set
			if (rs.next()) {
				updatedBalance = rs.getString("balance");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Handle exceptions (print stack trace, log errors, etc.)
			e.printStackTrace();
		} finally {
			// Close connections and resources
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// Handle exceptions (print stack trace, log errors, etc.)
				e.printStackTrace();
			}
		}

		return updatedBalance;
	}
}
