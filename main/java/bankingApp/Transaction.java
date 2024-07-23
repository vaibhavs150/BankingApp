package bankingApp;

public class Transaction {
	private String type;
	private String amount;
	private String date;

	public Transaction(String type, String date, String amount) {
		this.type = type;
		this.date = date;
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public String getAmount() {
		return amount;
	}

	public String getDate() {
		return date;
	}

}
