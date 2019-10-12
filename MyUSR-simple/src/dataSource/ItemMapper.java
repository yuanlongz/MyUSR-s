package dataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		sqlStatement.execute();
	}

	public void insert(Item item) throws Exception {
		PreparedStatement sqlStatement = null;
		sqlStatement = DBConnection.prepare(insertItemSS);
		sqlStatement.setString(1, item.getName());
		sqlStatement.setString(2, item.getUnitPrice());
		sqlStatement.execute();
	}

	public void update(Item item) throws Exception {
		PreparedStatement sqlStatement = null;
		sqlStatement = DBConnection.prepare(updateNSS);
		sqlStatement.setString(1, item.getUnitPrice());
		sqlStatement.setString(2, item.getName());
		sqlStatement.execute();
	}

	public static String findPrice(String name) throws Exception {
		Item result = map.getWithN(name);
		if (result == null) {
			PreparedStatement sqlStatement = null;
			ResultSet rs = null;
			sqlStatement = DBConnection.prepare(findItemWithNSS);
			sqlStatement.setString(1, name);
			rs = sqlStatement.executeQuery();
			if (rs.next()) {
				return rs.getString("unit_price");
			} else
				throw new Exception("no such item exist in the DB");
		} else {
			// TODO: test marker
			System.out.println("ID map used to find item with name");
		}

		return result.getUnitPrice();
	}
}
