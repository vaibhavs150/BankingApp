package bankingApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Search1cutomer")
public class Search1cutomer extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Extract account number from the request parameter
		String accNo = request.getParameter("search");

		// Database connection
		Connection conn = null;
		PreparedStatement pstmt = null;
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
			conn = DBUtil.getConnection();

			// SQL query to retrieve customer details by account number
			String sql = "SELECT name, address, mobile_no, email, acc_type, dob, id_proof,acc_no FROM customer WHERE acc_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, accNo);

			// Execute the query
			rs = pstmt.executeQuery();

			// Check if customer details are found
			if (rs.next()) {
				// Extract customer details
				String name = rs.getString("name");
				String address = rs.getString("address");
				String mobileNo = rs.getString("mobile_no");
				String email = rs.getString("email");
				String accountType = rs.getString("acc_type");
				String dob = rs.getString("dob");
				String idProof = rs.getString("id_proof");
				String acc_no = rs.getString("acc_no");

				// Set customer details as request attribute
				request.setAttribute("customerDetails",
						new String[] { name, address, mobileNo, email, accountType, dob, idProof, acc_no });
			} else {
				// No customer found with the provided account number
				request.setAttribute("customerDetails", null);
			}
		} catch (SQLException ex) {
			// Database error
			ex.printStackTrace();
			request.setAttribute("customerDetails", null);
		} finally {
			// Close resources
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		// Forward the request to the JSP page for displaying customer details
		request.getRequestDispatcher("accModify.jsp").forward(request, response);
	}
}
