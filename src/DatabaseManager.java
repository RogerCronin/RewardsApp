import java.sql.*;

public class DatabaseManager {
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


//    public DatabaseManager (String driver, String username, String password) {
//        this.driver = driver;
//        this.username = username;
//        this.password = password;
//    }


}
