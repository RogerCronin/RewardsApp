import java.sql.*;

public class DatabaseConnectivity {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/JJR";
        String username = "postgres";
        String password = "@$ept1213dE";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the server");

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
            String sql = "SELECT * FROM users";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("account_id");
                String name = result.getString("username");
                String word = result.getString("password");

                System.out.printf("%d - %s - %s\n", id, name, word);
            }

            connection.close();

        } catch (SQLException e) {
            System.out.println("Error connecting to the server");
            e.printStackTrace();
        }
    }

    public static class DatabaseManager {
        String driver = "jdbc:postgresql://localhost:5432/JJR";
        String user = "postgres";
        String pass = "@$ept1213dE";

        public void addNewUser(String username, String password) {
            try {
                Connection connection = DriverManager.getConnection(driver, user, pass);
                System.out.println("Connected to the server");

                String sql = "INSERT INTO users (username, password)"
                        + " VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);

                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("A new user has been inserted");
                }

            } catch (SQLException sqlE) {
                System.out.println("Error connecting to server");
                sqlE.printStackTrace();
            }
        }


    //    public DatabaseConnectivity.DatabaseManager (String driver, String username, String password) {
    //        this.driver = driver;
    //        this.username = username;
    //        this.password = password;
    //    }


    }
}
