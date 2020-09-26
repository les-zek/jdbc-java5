package jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollResultSetDemo {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection connection = ConnectionDemo.getConnection();
// ustawiamy możliwość przewijania w obu kierunkach

        Statement selectAll = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet records = selectAll.executeQuery("select * from demo");
    /*
        while (records.next()) {
            System.out.print("Id: " + records.getInt("id"));
            int points = records.getInt("points");
            boolean isPointWasNull = records.wasNull();
            System.out.print(" , points: " + (isPointWasNull ? "null" : points));
            System.out.println(" , name: " + records.getString("name"));
        }
        utworzylismy metode !!!
      */
        SelectDemo.printDemoTable(records);
        records.beforeFirst();  // skrolujemy do początku
        SelectDemo.printDemoTable(records);

        records.afterLast();  // przewija na koniec


        connection.close();
    }
}
