package bankingApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CustomerDAO {
	public boolean authenticateCustomer(String accNo, String pin) {
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM customer WHERE acc_no = ? AND pin = ?");) {
			statement.setString(1, accNo);
			statement.setString(2, pin);

			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next(); // If the query returns a row, authentication is successful
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public HashMap<String, String> getCustomerInfo(String accNo) {
		HashMap<String, String> customerInfo = new HashMap<>();
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT name, balance FROM customer WHERE acc_no = ?");) {
			statement.setString(1, accNo);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					customerInfo.put("name", resultSet.getString("name"));
					customerInfo.put("balance", resultSet.getString("balance"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}
}
