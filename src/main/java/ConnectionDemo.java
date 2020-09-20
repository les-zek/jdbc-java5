import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
        Connection connection =
                DriverManager.getConnection
                        ("jdbc:mysql://localhost:3306/instytut?serverTimezone=Europe/Warsaw",
                "root", "Leszekdupa102");
        connection.close();
    }
}
