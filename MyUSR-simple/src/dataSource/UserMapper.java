package dataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.Admin;
import domain.Customer;
import domain.DomainObject;
import domain.Role;
import domain.User;

public class UserMapper implements Mapper {
	// users table: 1.user_id, 2.name, 3.account, 4.password, 5.role
	// insert customer statement String
	private static IdentityMap<User> map = IdentityMap.getInstance(User.class);
	// find user With account Statement String
	private static final String findWithAccount = "SELECT * " + "FROM users "
			+ "WHERE account = ?";
	// find user With account Statement String
	private static final String findWithID = "SELECT * " + "FROM users "
			+ "WHERE user_id = ?";
	// delete user from users table with user_id statement String
	private static final String delWithID = "DELETE FROM users "
			+ "WHERE user_id = ?";
	// insert user statement String
	private static final String insertSS = "INSERT INTO users VALUES "
			+ "(?, ?, ?, ?, ?)";
	// update customer profile in user table statement String
	private static final String updateSS = "UPDATE users "
			+ "SET name = ?, password = ? " + "WHERE user_id = ?";

	public void delete(String id) throws Exception {
		PreparedStatement sqlStatement;
		sqlStatement = null;
		sqlStatement = DBConnection.prepare(delWithID);
		sqlStatement.setString(1, id);
		sqlStatement.execute();
	}
	
	@Override
	public void insert(DomainObject obj) throws Exception {
		User user = (User) obj;
		PreparedStatement sqlStatement = null;

		sqlStatement = DBConnection.prepare(insertSS);
		sqlStatement.setString(1, user.getId());
		sqlStatement.setString(2, user.getName());
		sqlStatement.setString(3, user.getAccount());
		sqlStatement.setString(4, user.getPassword());
		sqlStatement.setString(5, user.getRole().name());
		sqlStatement.execute();
	}
	
	@Override
	public void update(DomainObject obj) throws Exception {
		User user = (User) obj;
		PreparedStatement sqlStatement = null;

		sqlStatement = DBConnection.prepare(updateSS);
		sqlStatement.setString(1, user.getName());
		sqlStatement.setString(2, user.getPassword());
		sqlStatement.setString(3, user.getId());
		sqlStatement.execute();
	}

	public static User findWithAccountEmail(String email) throws Exception {
		// check from id_map first
		User user = map.getWithAE(email);
		if (user == null) {
			PreparedStatement sqlStatement;
			ResultSet rs = null;
			sqlStatement = DBConnection.prepare(findWithAccount);
			sqlStatement.setString(1, email);
			rs = sqlStatement.executeQuery();
			if (rs.next()) {
				String role = rs.getString("role");
				if (role.equals(Role.ADMIN.name())) {
					// lazy load only load id
					user = new Admin(rs.getString("user_id"));
				} else if (role.equals(Role.CUSTOMER.name())) {
					// lazy load: only load id
					user = new Customer(rs.getString("user_id"));
				} else
					throw new Exception("undefined user role");

				// update ID_map
				map.putWithID(user.getId(), user);
				return user;
			} else
				throw new Exception("no user with this account find");
		} else {
			// TODO: test marker
			System.out.println("ID map used to find user with account");
			return user;
		}
	}

	public static User findWithID(String id) throws Exception {
		// check from id_map
		User user = map.getWithID(id);
		if (user == null) {
			PreparedStatement sqlStatement;
			ResultSet rs = null;
			sqlStatement = DBConnection.prepare(findWithID);
			sqlStatement.setString(1, id);
			rs = sqlStatement.executeQuery();
			if (rs.next()) {
				String role = rs.getString("role");
				if (role.equals(Role.ADMIN.name())) {
					user = new Admin(rs.getString("name"),
							rs.getString("user_id"), rs.getString("account"),
							rs.getString("password"), rs.getString("role"));
				} else if (role.equals(Role.CUSTOMER.name())) {
					user = new Customer(rs.getString("name"),
							rs.getString("user_id"), rs.getString("account"),
							rs.getString("password"), rs.getString("role"));
				} else
					throw new Exception("undefined user role");
				// update ID_map
				map.putWithID(id, user);
				return user;
			} else
				throw new Exception("no user with this account find");
		} else {
			// TODO: test marker
			System.out.println("ID map used to find user with id");
		}
		return user;
	}

}
