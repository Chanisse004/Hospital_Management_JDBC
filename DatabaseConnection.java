package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DatabaseConnection {

        String url = "jdbc:postgresql://localhost:5432/HospitalManagement";
        String username = "postgres";
        String password = "oracle123";

        public Connection getConnection() {

            Connection conn = null;

            try {
                conn = DriverManager.getConnection(url, username, password);
                System.out.println("connected successfully!!");


            } catch (Exception e) {
                System.out.println("Connection failed!!");
                e.printStackTrace();
            }
            return conn;
        }
    }

