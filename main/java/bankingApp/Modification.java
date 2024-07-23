package bankingApp;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/Modification")
public class Modification extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Extract data from the request parameters
		String name = request.getParameter("fullname");
		String address = request.getParameter("address");
		String mobileNo = request.getParameter("mobile");
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");
		String idProof = request.getParameter("idProof");
		String accNo = request.getParameter("accNo"); // Assuming you have a hidden input field in the form to pass
														// accNo

		Connection connection = null;

		// Load the MySQL JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			response.getWriter().println("MySQL JDBC driver not found. Please ensure it's included in your project.");
			return;
		}

		// SQL query to update customer details
		String sql = "UPDATE customer SET name=?, address=?, mobile_no=?, email=?, dob=?, id_proof=? WHERE acc_no=?";

		try {
			// Create connection to the database
			connection = DBUtil.getConnection();

			// Create prepared statement
			PreparedStatement pstmt = connection.prepareStatement(sql);

			// Set parameters for the prepared statement
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, mobileNo);
			pstmt.setString(4, email);
			pstmt.setString(5, dob);
			pstmt.setString(6, idProof);
			pstmt.setString(7, accNo); // Assuming accNo is passed in the request

			// Execute the update
			int rowsAffected = pstmt.executeUpdate();

			// Close the resources
			pstmt.close();
			connection.close();

			// Set alert message based on update success
			String alertMessage = "";
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
				out1.println("  text: 'Customer details updated successfully',");
				out1.println("  icon: 'success'");
				out1.println("}).then(() => { window.location.href='accModify.jsp'; });");
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
				out1.println("  text: 'Failed to update customer details ',");
				out1.println("  icon: 'error'");
				out1.println("}).then(() => { window.location.href='accModify.jsp'; });");
				out1.println("</script>");
				out1.println("</body>");
				out1.println("</html>");
				out1.close();
			}

			// Send alert message back to client
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('" + alertMessage + "');");
			out.println("window.location.href='accModify.jsp';"); // Redirect to modification form JSP
			out.println("</script>");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
