package com.qts.servicelogic;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	Connection connection = null;
	
	public Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/iCAM_SSP_Migration_22062018",
		            "postgres", "postgres");
			System.out.println("Opened database successfully");
		}catch (Exception e) {
			e.printStackTrace();
		}
        return connection;
	}
}
