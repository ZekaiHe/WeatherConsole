import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CreateConnection {
    public static String username = "WeatherApp";
    public static String password = "weatherpass"; //remember to delte when uploading to github
    public static String dbURL = "jdbc:mysql://localhost:3306/WeatherApp";

    public static Connection connection;

    public static void main(String[] args) {
        CreateConnection.getConnection();
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("SQL Connection to database established!");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Beep Beep Boop Boop X.X", e);
        }
    }
}

