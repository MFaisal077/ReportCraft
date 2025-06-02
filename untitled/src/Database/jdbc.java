package Database;

import java.sql.*;

public class jdbc {
    private static final String URL="Database.jdbc:mysql://localhost:3306/users";
    private static final String USER="root";
    private static final String PASSWORD="Faisal@123";


    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to MySQL database");
            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }


    public static void main(String[] args) {

    }

}
