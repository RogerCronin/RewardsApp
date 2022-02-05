import com.mysql.cj.jdbc.exceptions.SQLError;

import java.sql.*;

public class DatabaseManager {
    public static void main(String[] args) {
        String username = "JohnSmith";
        String password = "1234";
        returnID(username, password);
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

    public static void returnID(String usr, String psw) {

        try {
            int account_id;
            String myUrl = "jdbc:mysql://127.0.0.1:3306/?user=root";

            Connection conn = DriverManager.getConnection(myUrl, "root","password");
            System.out.println("Connected to the server");

            PreparedStatement pstat = conn.prepareStatement("SELECT account_id FROM users"
                    + " WHERE username = " + "\"?\"" + " AND WHERE password = \"?\"");

            pstat.setString(1, usr);
            pstat.setString(2, psw);

            ResultSet rs = pstat.executeQuery();

            account_id = rs.getInt("account_id");

            System.out.println("Account ID for user " + usr
            + "is " + account_id);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (IndexOutOfBoundsException ee) {
            ee.printStackTrace();
            System.out.println("No information found.");
        }
    }

}
