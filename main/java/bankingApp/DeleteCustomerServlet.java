package bankingApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Extract account number from the request parameter
		String accNo = request.getParameter("search");

		// Database connection
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establish database connection
			conn = DBUtil.getConnection();

			// SQL query to delete customer account
			String sql = "DELETE FROM customer WHERE acc_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, accNo);

			// Execute the delete query
			int rowsAffected = pstmt.executeUpdate();

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
				out1.println("  text: 'Customer account has been deleted successfully.',");
				out1.println("  icon: 'success'");
				out1.println("}).then(() => { window.location.href='adminDelete.jsp'; });");
				out1.println("</script>");
				out1.println("</body>");
				out1.println("</html>");
				out1.close();
				response.getWriter()
						.println("Customer account with account number " + accNo + " has been deleted successfully.");
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
				out1.println("  text: 'Customer account not found or deletion failed',");
				out1.println("  icon: 'error'");
				out1.println("}).then(() => { window.location.href='adminDelete.jsp'; });");
				out1.println("</script>");
				out1.println("</body>");
				out1.println("</html>");
				out1.close();
			}
		} catch (ClassNotFoundException ex) {
			// Log the exception
			ex.printStackTrace();

			// Send a detailed error message back to the client
			response.getWriter().println("MySQL JDBC driver not found. Please ensure it's included in your project.");
		} catch (SQLException ex) {
			// Database error
			ex.printStackTrace();
			response.getWriter()
					.println("An error occurred while deleting the customer account. Please try again. Error: "
							+ ex.getMessage());
		} finally {
			// Close resources
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
