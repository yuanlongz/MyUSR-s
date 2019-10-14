package dataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.DomainObject;
import domain.Item;

public class ItemMapper implements Mapper {
	// item table: 1.item_id 2.name 3.unit_price
	private static IdentityMap<Item> map = IdentityMap.getInstance(Item.class);

	// insert item statement String
	private static final String insertItemSS = "INSERT INTO item VALUES "
			+ "(?, ?)";
	// update item price in item table statement String
	private static final String updateNSS = "UPDATE item "
			+ "SET unit_price = ? " + "WHERE name = ?";
	// find item price with name statement string
	private static final String findItemWithNSS = "SELECT unit_price "
			+ "FROM item " + "WHERE name = ?";
	// delete item from item table with item id statement String
	private static final String delItemWithNSS = "DELETE FROM item "
			+ "WHERE name = ?";

	@Override
	public void delete(String name) throws Exception {
		PreparedStatement sqlStatement = null;
		sqlStatement = DBConnection.prepare(delItemWithNSS);
		sqlStatement.setString(1, name);
		try {
			sqlStatement.execute();
		} catch (SQLException e) {
			DBConnection.rollBack();
		}
		DBConnection.closeConnection();
	}

	@Override
	public void insert(DomainObject obj) throws Exception {
		Item item = (Item) obj;
		PreparedStatement sqlStatement = null;
		sqlStatement = DBConnection.prepare(insertItemSS);
		sqlStatement.setString(1, item.getName());
		sqlStatement.setString(2, item.getUnitPrice());
		try {
			sqlStatement.execute();
		} catch (SQLException e) {
			DBConnection.rollBack();
		}
		DBConnection.closeConnection();
	}

	@Override
	public void update(DomainObject obj) throws Exception {
		Item item = (Item) obj;
		PreparedStatement sqlStatement = null;
		sqlStatement = DBConnection.prepare(updateNSS);
		sqlStatement.setString(1, item.getUnitPrice());
		sqlStatement.setString(2, item.getName());
		try {
			sqlStatement.execute();
		} catch (SQLException e) {
			DBConnection.rollBack();
		}
		DBConnection.closeConnection();
	}

	public static String findPrice(String name) throws Exception {
		Item item = map.getWithN(name);
		String result;
		if (item == null) {
			PreparedStatement sqlStatement = null;
			ResultSet rs = null;
			sqlStatement = DBConnection.prepare(findItemWithNSS);
			sqlStatement.setString(1, name);
			rs = sqlStatement.executeQuery();
			if (rs.next()) {
				result = rs.getString("unit_price");
			} else
				throw new Exception("no such item exist in the DB");
		} else {
			result = item.getUnitPrice();
		}
		DBConnection.closeConnection();
				return result;

	}
}
