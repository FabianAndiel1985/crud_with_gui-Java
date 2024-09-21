package crudWithGui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
	
	private static final String MYSQL_JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	
	private static final String JDBC_MYSQL_HOST = "jdbc:mysql://localhost:3306/";
	private static final String DB_NAME = "crud_with_gui";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	private DBconnection() {
	}
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(JDBC_MYSQL_HOST + DB_NAME, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
			}
	}
		

//	public static void main(String[] argv) {
//
//		System.out.println("-------- MySQL JDBC Connection Testing ------------");
//
//		try (Connection connection = DriverManager.getConnection(JDBC_MYSQL_HOST + DB_NAME, USERNAME, PASSWORD);) {
//
//			if (connection != null) {
//				System.out.println("You made it, take control your database now!");
//			} else {
//				System.out.println("Failed to make connection!");
//			}
//
//		} catch (SQLException e) {
//			System.out.println("Connection Failed! Check output console");
//			e.printStackTrace();
//			return;
//		}
//
//	}

}