import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class SelectDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection connection = ConnectionDemo.getConnection();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj literę: ");
        String letter = scanner.next();  // uwaga na nextline()!!! podatne na SQL INJECTION
    //    Statement find = connection.createStatement();
    //    ResultSet resultSet = find.executeQuery(
   //             "select * from demo where name like '"+ letter + "%'"
   //     );

        // zapytanie zabezpieczone przed SQL injection
        PreparedStatement find = connection.prepareStatement(
                "select * from demo where id = ?"
        );
        // wstawienie wartości parametru w miejscu pierwszego pytajnika
        find.setInt(1, Integer.parseInt(letter));
        ResultSet resultSet = find.executeQuery();
        System.out.println("Wyniki SELECT");
        printDemoTable(resultSet);
        connection.close();
    }

    public static void printDemoTable(ResultSet resultSet) throws SQLException {
        System.out.println("---------------------------T A B L E ---------------------------");
        while (resultSet.next()) {
            System.out.print("Id: " + resultSet.getInt("id"));
            /*
            UWAGA
            trzeba sprawdzac czy nie było null
            dla typów podstawowych
            int, boolean, float, double
             */
            int points = resultSet.getInt("points");
            boolean isPointWasNull = resultSet.wasNull();
            System.out.print(" , points: "+ (isPointWasNull ? "null" : points));
            System.out.println(" , name: " + resultSet.getString("name"));
        }
        System.out.println("----------------------------------------------------------------");

    }
}
