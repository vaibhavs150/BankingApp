package bankingApp;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.mail.Session;

import javax.servlet.annotation.WebServlet;

@WebServlet("/RegisterCustomerServlet")
public class RegisterCustomerServlet extends HttpServlet {

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
		String accountType = request.getParameter("accountType");
		double balance = Double.parseDouble(request.getParameter("balance"));
		String dob = request.getParameter("dob");
		String idProof = request.getParameter("idProof");
		String accNo = generateAccountNumber(); // Generate account number

		String pin = generateRandomPin();

		// Database connection parameters
		String url = "jdbc:mysql://localhost:3306/bankingapp";
		String username = "root";
		String password = "Iamgroot@15";

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

		// SQL query to insert data into the database
		String sql = "INSERT INTO customer (name, address, mobile_no, email, acc_type, balance, dob, id_proof, acc_no, pin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set parameters for the PreparedStatement
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, mobileNo);
			pstmt.setString(4, email);
			pstmt.setString(5, accountType);
			pstmt.setDouble(6, balance);
			pstmt.setString(7, dob);
			pstmt.setString(8, idProof);
			pstmt.setString(9, accNo);
			pstmt.setString(10, pin);

			// Execute the insert query
			int rowsAffected = pstmt.executeUpdate();

			// Check if the insertion was successful
			if (rowsAffected > 0) {
				// Send a success message back to the client
				sendEmail(email, accNo, pin);
				response.setContentType("text/html");
				PrintWriter out1 = response.getWriter();
				// Modified success message with account number and PIN
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
				out1.println("  html: 'Customer registered successfully!<br>Account Number: " + accNo + "<br>PIN: "
						+ pin + "',");
				out1.println("  icon: 'success'");
				out1.println("}).then(() => { window.location.href='adminDashboard.jsp'; });");
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
				out1.println("  text: 'An error occured while registering the customer, please try again',");
				out1.println("  icon: 'error'");
				out1.println("}).then(() => { window.location.href='adminDashboard.jsp'; });");
				out1.println("</script>");
				out1.println("</body>");
				out1.println("</html>");
				out1.close();
			}
		} catch (SQLException ex) {
			// Log the exception
			ex.printStackTrace();

			// Send a detailed error message back to the client
			response.getWriter().println(
					"An error occurred while registering the customer. Please try again. Error: " + ex.getMessage());
		} catch (NumberFormatException ex) {
			// Log the exception
			ex.printStackTrace();

			// Send a detailed error message back to the client
			response.getWriter().println("Invalid balance value. Please provide a valid number.");
		} catch (Exception ex) {
			// Log the exception
			ex.printStackTrace();

			// Send a generic error message back to the client
			response.getWriter().println("An unexpected error occurred. Please try again later.");
		}

	}

	// Method to generate a random 10-digit account number
	private String generateAccountNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			sb.append(random.nextInt(5));
		}
		return sb.toString();
	}

	private String generateRandomPin() {
		Random random = new Random();
		int pin = random.nextInt(10000); // Generate a random integer between 0 and 9999
		return String.format("%04d", pin); // Format the pin to ensure it has leading zeros if necessary
	}

	  private void sendEmail(String recipientEmail, String accNo, String pin) {
	        // Sender's email and password
	        final String senderEmail = "bankingapp2024@gmail.com";
	        final String senderPassword = "lodz muud lpkm wpun";

	        // Setup mail server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "587");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        

	        // Get the Session object
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(senderEmail, senderPassword);
	            }
	        });

	        try {
	            // Create a default MimeMessage object
	            Message message = new MimeMessage(session);

	            // Set From: header field
	            message.setFrom(new InternetAddress(senderEmail));

	            // Set To: header field
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

	            // Set Subject: header field
	            message.setSubject("Welcome to Veltech Bank");

	            // Set the email message
	            message.setText("Dear Customer,\n\nCongratulations! Your bank account has been created successfully.\n\n"
	                    + "Account Number: " + accNo + "\nPIN: " + pin + "\n\nWelcome to Veltech Bank!");

	            // Send the email
	            Transport.send(message);

	            System.out.println("Email sent successfully to " + recipientEmail);
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }
	    }
	}

