package Database;

import java.sql.*;

public class jdbc {
    private static final String URL="Database.jdbc:mysql://localhost:3306/report_builder";
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
    public static void testQuery() {
        String sql = "SELECT * FROM employees";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getInt("salary"));
            }

        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testQuery();
    }

}
