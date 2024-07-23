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

@WebServlet("/CustLogin")
public class CustLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String SELECT_QUERY = "SELECT name, balance, acc_no,acc_type FROM customer WHERE acc_no=? AND pin=?";

	public CustLogin() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String username = request.getParameter("accNo");
		String password = request.getParameter("pin");

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingapp", "root", "Iamgroot@15");
			stmt = conn.prepareStatement(SELECT_QUERY);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();

			if (rs.next()) {
				String name = rs.getString("name");
				double balance = rs.getDouble("balance");
				String acc_no = rs.getString("acc_no");
				String acc_type = rs.getString("acc_type");

				HttpSession session = request.getSession();
				session.setAttribute("customerName", name);
				session.setAttribute("balance", balance);
				session.setAttribute("accountType", acc_type);
				session.setAttribute("loggedInAccNo", acc_no);

				response.sendRedirect("custDashboard.jsp");
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
				out1.println("  text: 'Invalid username or password',");
				out1.println("  icon: 'error'");
				out1.println("}).then(() => { window.location.href='custLogin.jsp'; });");
				out1.println("</script>");
				out1.println("</body>");
				out1.println("</html>");
				out1.close();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			out.println("Error: " + e.getMessage());
		} finally {
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
