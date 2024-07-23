package bankingApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
	public boolean authenticateAdmin(String username, String password) {
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?");) {
			statement.setString(1, username);
			statement.setString(2, password);

			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next(); // If the query returns a row, authentication is successful
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}