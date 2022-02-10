import APIObjects.*;
import APIObjects.GetCardsResponse.Card;
import APIObjects.GetRedeemedRewardsResponse.RedeemedReward;
import APIObjects.GetTransactionsResponse.Transaction;
import APIObjects.GetRewardsResponse.Reward;
import java.sql.*;

public class DatabaseManager {
    static final String url = "jdbc:mysql://localhost:3306/?user=root";
    static final String user = "root";
    static final String pass = "password";

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
