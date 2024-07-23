package bankingApp;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		int accNo = Integer.parseInt(request.getParameter("AccNo"));
		int newPin = Integer.parseInt(request.getParameter("pin"));
		int confirmedPin = Integer.parseInt(request.getParameter("pin1"));

		String loggedInAccNo = (String) request.getSession().getAttribute("loggedInAccNo");

		if (loggedInAccNo == null || !loggedInAccNo.equals(String.valueOf(accNo))) {
			response.getWriter().println("You are not authorized to perform this action.");
			return;
		}

		if (newPin == confirmedPin) {
			// JDBC variables
			Connection conn = null;
			PreparedStatement stmt = null;
			// Load the MySQL JDBC driver
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException ex) {
				// Log the exception
				ex.printStackTrace();

				// Send a detailed error message back to the client
				response.getWriter()
						.println("MySQL JDBC driver not found. Please ensure it's included in your project.");
				return; // Exit the method if driver loading fails
			}

			try {
				// Connect to the database
				conn = DBUtil.getConnection();

				// Update the password (pin) in the customer table
				String updateQuery = "UPDATE customer SET pin = ? WHERE acc_no = ?";
				stmt = conn.prepareStatement(updateQuery);
				stmt.setInt(1, newPin);
				stmt.setInt(2, accNo);
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
					out1.println("  text: 'Password changed successful ',");
					out1.println("  icon: 'success'");
					out1.println("}).then(() => { window.location.href='custDashboard.jsp'; });");
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
					out1.println("  title: 'Error',");
					out1.println("  text: 'Account does not exist or password could not be changed ',");
					out1.println("  icon: 'error'");
					out1.println("}).then(() => { window.location.href='dashboard-changepass.jsp'; });");
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
					if (stmt != null)
						stmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
			out1.println("  title: 'Error',");
			out1.println("  text: 'passwords do not match ',");
			out1.println("  icon: 'error'");
			out1.println("}).then(() => { window.location.href='dashboard-changepass.jsp'; });");
			out1.println("</script>");
			out1.println("</body>");
			out1.println("</html>");
			out1.close();
		}
	}
}
