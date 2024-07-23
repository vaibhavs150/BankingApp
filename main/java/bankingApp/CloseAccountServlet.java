package bankingApp;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/CloseAccountServlet")
public class CloseAccountServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Retrieve account number from the form
		String accNoStr = request.getParameter("AccNo");
		int accNo = Integer.parseInt(accNoStr);

		String loggedInAccNo = (String) request.getSession().getAttribute("loggedInAccNo");

		if (loggedInAccNo == null || !loggedInAccNo.equals(accNoStr)) {
			out.println("You are not authorized to perform this action.");
			return;
		}

		// JDBC variables
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

			// Establish connection to the database
			conn = DBUtil.getConnection();

			// Retrieve balance from the customer table
			String retrieveQuery = "SELECT balance FROM customer WHERE acc_no = ?";
			stmt = conn.prepareStatement(retrieveQuery);
			stmt.setInt(1, accNo);
			rs = stmt.executeQuery();

			int balance = 0;
			if (rs.next()) {
				balance = rs.getInt("balance");
			}

			if (balance == 0) {
				// Delete the customer from the customer table
				String deleteQuery = "DELETE FROM customer WHERE acc_no = ?";
				stmt = conn.prepareStatement(deleteQuery);
				stmt.setInt(1, accNo);
				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
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
					out1.println("  text: 'Account closed successful ',");
					out1.println("  icon: 'success'");
					out1.println("}).then(() => { window.location.href='index.jsp'; });");
					out1.println("</script>");
					out1.println("</body>");
					out1.println("</html>");
					out1.close();
				} else {
					out.println("<h2>Account does not exist or could not be closed.</h2>");
				}
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
				out1.println("  text: 'Account could not be closed, please withdraw the remaining amount',");
				out1.println("  icon: 'error'");
				out1.println("}).then(() => { window.location.href='dashboard-closeAcc.jsp'; });");
				out1.println("</script>");
				out1.println("</body>");
				out1.println("</html>");
				out1.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			out.println("<h2>Error occurred: " + e.getMessage() + "</h2>");
		} finally {
			// Close JDBC objects
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
