package dataSource;

import java.sql.*;

public class DBConnection {
	// For local setup, uncomment this line or, better, set the environment
	// variable in your run configuration
	private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/myusr";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "87807981";
	private static Connection conn;
	// private static final String DB_CONNECTION
	// ="postgres://hljjogodtgfcci:fed44adf1778b0c71930ff909abd4d4e2fcaf95b02448e581d10841fdf00d8a9@ec2-54-235-96-48.compute-1.amazonaws.com:5432/dbcjvhfb6diug8";
	// private static final String DB_CONNECTION =
	// System.getenv().get("JDBC_DATABASE_URL");
	// private static final String DB_USER = "hljjogodtgfcci";
	// private static final String DB_PASSWORD =
	// "fed44adf1778b0c71930ff909abd4d4e2fcaf95b02448e581d10841fdf00d8a9";

	public static PreparedStatement prepare(String stm) {
		PreparedStatement preparedStatement = null;
		try {
			Connection dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(stm);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return preparedStatement;
	}

	public static PreparedStatement prepare(String stm,
			int returnGeneratedKeys) {
		PreparedStatement preparedStatement = null;
		try {
			Connection dbConnection = getDBConnection();
			// For roll back or undo a transaction if it is not completed
			// appropriately.
			dbConnection.setAutoCommit(false);

			preparedStatement = dbConnection.prepareStatement(stm,
					Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return preparedStatement;
	}

	private static Connection getDBConnection() {
		try {
			conn = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void rollBack() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			System.out.println("Rollback failed.");
			e.printStackTrace();
		}
	}

}
