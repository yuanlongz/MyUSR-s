package dataSource;

import java.sql.*;

public class DBConnection {
	// For local setup, uncomment this line or, better, set the environment
	// variable in your run configuration
	private static String DB_CONNECTION = null;
	private static String DB_USER = null;
	private static String DB_PASSWORD = null;
	private static Connection conn = null;
	private static Boolean isHerokuDB = false;

	private static void ini() {
		
		if (isHerokuDB) {
			DB_CONNECTION = "jdbc:postgres://ec2-54-235-96-48.compute-1.amazonaws.com:5432/dbcjvhfb6diug8";
			DB_USER = "hljjogodtgfcci";
			DB_PASSWORD = "fed44adf1778b0c71930ff909abd4d4e2fcaf95b02448e581d10841fdf00d8a9";
		} else {
			DB_CONNECTION = "jdbc:postgresql://localhost:5432/myusr";
			DB_USER = "postgres";
			DB_PASSWORD = "87807981";
		}
	}

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

			getDBConnection();
			conn.setAutoCommit(false);
			preparedStatement = dbConnection.prepareStatement(stm,
					Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return preparedStatement;
	}

	private static Connection getDBConnection() {
		try {
			ini();
			if (isHerokuDB) {
				String dbUrl = System.getenv("JDBC_DATABASE_URL");
				conn = DriverManager.getConnection(dbUrl);
			}else {
				conn = DriverManager.getConnection(DB_CONNECTION, DB_USER,
						DB_PASSWORD);
			}
			
			// For roll back or undo a transaction if it is not completed
			// appropriately.
			conn.setAutoCommit(false);
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
