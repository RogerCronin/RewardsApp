import java.sql.*;

public class DatabaseManager {
    public static void main(String[] args) {
        verifyInfo("JohnSmith", "1234");
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

    public static void verifyInfo(String usrn, String psw) {

        try {
            String url = "jdbc:postgresql://localhost:5432/JJR";
            String root = "postgres";
            String key = "@$ept1213dE";

            Connection conn = DriverManager.getConnection(url, root, key);
            System.out.println("Connected to the server");

            String query = "SELECT username, password FROM users";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");

                if (usrn.equals(username)) {
                    System.out.println("Username found.");
                } else {
                    System.out.println("No valid username match.");
                }

                if (psw.equals(password)) {
                    System.out.println("Password found.");
                } else {
                    System.out.println("No valid password match.");
                }
            }
            st.close();
            conn.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

}
