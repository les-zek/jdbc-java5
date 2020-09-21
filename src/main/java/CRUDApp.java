import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class CRUDApp {
    static Scanner scanner = new Scanner(System.in);

    public static int menu() {
        System.out.println("-----------------");
        System.out.println("1. Utwórz tabelę");
        System.out.println("2. Dodaj rekord");
        System.out.println("3. Wyświetl tabelę");
        System.out.println("0. Wyjście");
        System.out.println("-----------------");

        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public static void insertRecord() throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        System.out.println("Podaj imię:");
        String name = scanner.nextLine();
        System.out.println("Podaj punkty:");
        int points = scanner.nextInt();
        Connection connection = ConnectionDemo.getConnection();
        PreparedStatement insert = connection.prepareStatement(
                "insert into demo(name, points) values(? ,?)"
        );
        insert.setString(1, name);
        insert.setInt(2, points);
        insert.executeUpdate();
        connection.close();
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        while (true) {
            switch (menu()) {
                case 1:
                    //TODO zdefiniować metode tworzenie własnej tabeli
                    CreateTableStatementDemo.createTableDemo();
                    break;
                case 2:
                    insertRecord();
                    break;
                case 3:
                    //wyświetl tabelę
                    Connection connection = ConnectionDemo.getConnection();

                    Statement selectAll = connection.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    ResultSet records = selectAll.executeQuery("select * from demo");

                    SelectDemo.printDemoTable(records);
                    connection.close();
                    break;
                case 0:
                    System.exit(0);
                    //TODO dodać modyfikację, usuwanie i wyszukiwanie rekordów
            }
        }
    }
}