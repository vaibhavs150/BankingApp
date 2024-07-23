package bankingApp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/DownloadRecentTransactionsServlet")
public class DownloadRecentTransactionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"recent_transactions.pdf\"");

		// Retrieve recent transactions from session
		@SuppressWarnings("unchecked")
		List<Transaction> recentTransactions = (List<Transaction>) request.getSession()
				.getAttribute("recentTransactions");

		// Generate PDF document
		try {
			Document document = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);

			document.open();
			document.add(new Paragraph("Recent Transactions\n\n"));

			if (recentTransactions != null && !recentTransactions.isEmpty()) {
				for (Transaction transaction : recentTransactions) {
					document.add(new Paragraph("Type: " + transaction.getType()));
					document.add(new Paragraph("Date: " + transaction.getDate()));
					document.add(new Paragraph("Amount: " + transaction.getAmount() + " Rs.\n"));
				}
			} else {
				document.add(new Paragraph("No recent transactions available."));
			}

			document.close();

			// Write PDF content to response output stream
			response.getOutputStream().write(baos.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
			response.getWriter().println("Error generating PDF: " + e.getMessage());
		}
	}
}
