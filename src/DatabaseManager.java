

import java.sql.*;

public class DatabaseManager {
    static final String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
    static final String user = "root";
    static final String pass = "password";
    static String query = "SELECT account_id FROM users WHERE username = 1 AND password = 2";
    public static void main(String[] args) {
        returnId("JohnSmith", "1234");
//        try {
//            String url = "jdbc:postgresql://localhost:5432/JJR";
//            String username = "postgres";
//            String password = "@$ept1213dE";
//
//            Connection connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Connected to the server");

//            String sql = "INSERT INTO users (username, password)"
//                    + " VALUES (?, ?)";
//
//            PreparedStatement statement = connection.prepareStatement(sql);

//            Statement statement = connection.createStatement();

//            statement.setString(1, "name");
//            statement.setString(2, "password");

//            int rows = statement.executeUpdate();
//            int rows = statement.executeUpdate(sql);
//            if (rows > 0) {
//                System.out.println("A new contact has been inserted.");
//            }

            // execute SELECT statement
//            String sql = "SELECT * FROM users";
//
//            Statement statement = connection.createStatement();
//            ResultSet result = statement.executeQuery(sql);
//
//            while (result.next()) {
//                int id = result.getInt("account_id");
//                String name = result.getString("username");
//                String word = result.getString("password");
//
//                System.out.printf("%d - %s - %s\n", id, name, word);
//            }
//
//            connection.close();
//
//        } catch (SQLException e) {
//            System.out.println("Error connecting to the server");
//            e.printStackTrace();
//        }
    }

    public static void returnId(String username, String password) {
        query.replace("1", username);
        query.replace("2", password);
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()
             ) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("account_id"));
                System.out.println("UN: " + rs.getString("username"));
                System.out.println("PW: " + rs.getString("password"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

}
