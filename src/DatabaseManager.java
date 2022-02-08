

import java.sql.*;

public class DatabaseManager {
    static final String url = "jdbc:mysql://localhost:3306/?user=root";
    static final String user = "root";
    static final String pass = "password";
    public static void main(String[] args) {
        infoExists("rat", "1");
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

    public static boolean infoExists(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()
        ) {
            String query = "SELECT account_id FROM rewardsapp.users WHERE EXISTS (SELECT username FROM users" +
                    "WHERE username = \"" + username + "\" AND password = \"" + password + "\");";

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                System.out.println("true");
                return true;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("false");
        return false;
    }

    public static void returnIdentification(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()
        ) {
            String query = "SELECT * FROM rewardsapp.users WHERE username = "
                    + "\"" + username + "\"" + " AND password = \"" + password + "\";";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("account_id"));
                System.out.println("USERNAME: " + rs.getString("username"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void returnId(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()
             ) {
            String query = "SELECT account_id FROM rewardsapp.users WHERE username = "
                    + "\"" + username + "\"" + " AND password = \"" + password + "\";";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("account_id"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

}
