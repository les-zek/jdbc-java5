import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CRUDApp {
    static Scanner scanner = new Scanner(System.in);

    public static int menu() {
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("1. Utwórz tabelę");
        System.out.println("2. Dodaj rekord");
        System.out.println("3. Wyświetl tabelę");
        System.out.println("4. Znajdź rekord z podanym ID");
        System.out.println("5. Usuń rekord z podanym ID");


        System.out.println("0. Wyjście");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-");


        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public static void createTableUsers() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, SQLException {
        Connection connection = ConnectionDemo.getConnection();
        Statement createTable = connection.createStatement();
        createTable.execute("drop table if exists users");
        boolean execute = createTable.execute(
                "create table users (id integer primary key auto_increment, email varchar(20), password varchar(20), age integer)"
        );
        System.out.println("Wartość zwrócona przez polecenie create table: " + execute);
        connection.close();
    }

    public static void insertRecord() throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        System.out.println("Give the user email :");
        String email = scanner.nextLine();
        System.out.println("Give the user password  :");
        String password = scanner.nextLine();

        System.out.println("Input age of the user :");
        int age = scanner.nextInt();
        Connection connection = ConnectionDemo.getConnection();
        PreparedStatement insert = connection.prepareStatement(
                "insert into users (email, password, age) values(? ,?, ?)"
        );
        insert.setString(1, email);
        insert.setString(2, password);

        insert.setInt(3, age);
        insert.executeUpdate();
        connection.close();
    }

    public static void printUserTable() throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection connection = ConnectionDemo.getConnection();
        Statement selectAll = connection.createStatement();
        ResultSet records = selectAll.executeQuery("select * from users");
        ResultSetMetaData metaData = records.getMetaData();
        int validColumns = 0;
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            if (metaData.getColumnName(i + 1).equals("id")) {
                validColumns++;
            }
            if (metaData.getColumnName(i + 1).equals("email")) {
                validColumns++;
            }
            if (metaData.getColumnName(i + 1).equals("password")) {
                validColumns++;
            }
            if (metaData.getColumnName(i + 1).equals("age")) {
                validColumns++;
            }
        }
        List<User> users = new ArrayList<>();
        if (validColumns == 4) {
            while (records.next()) {
                User user = new User(
                        records.getInt("id"),
                        records.getString("email"),
                        records.getString("password"),
                        records.getInt("age")
                );
                users.add(user);
            }
        }
        System.out.println("------------U S E R S  TABLE--------------");
        users.forEach(System.out::println);
        System.out.println("-----END OF U S E R S  TABLE--------------");

        connection.close();
    }

    public static User findUserById(int id, boolean delete) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection connection = ConnectionDemo.getConnection();
        Statement selectAll = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
        );
        ResultSet records = selectAll.executeQuery("select * from users");
        boolean found = false;
        while (records.next() && !found) {
            int actualId = records.getInt("id");
            if (actualId == id) {
                User user = new User(
                        actualId,
                        records.getString("email"),
                        records.getString("password"),
                        records.getInt("age")
                );
                found = true;
                if (delete) {
                    records.deleteRow();
                }
                return user;

            }
        }
        return null;
    }


    public static void main(String[] args) throws
            IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        while (true) {
            switch (menu()) {
                case 1:
                    //TODO zdefiniować metode tworzenie własnej tabeli
//                    CreateTableStatementDemo.createTableDemo();
                    createTableUsers();
                    break;
                case 2:
                    insertRecord();
                    break;
                case 3:
                    //wyświetl tabelę
                    printUserTable();
                    break;
                case 4:
                    // znajdź rekord wg id
                    System.out.println("Input ID of the user :");
                    int idUser = scanner.nextInt();
                    User foundUser = findUserById(idUser, false);
                    System.out.println("------------USER DATA  START---------------");
                    if (foundUser != null) {
                        System.out.println(foundUser.toString());
                    } else {
                        System.out.println(" No User with given ID ");
                    }
                    ;
                    System.out.println("------------USER DATA    END---------------");
                    break;
                case 5:
                    // znajdź rekord wg id
                    System.out.println("Input ID of the user :");
                    int delUser = scanner.nextInt();
                    User deletedUser = findUserById(delUser, true);
                    System.out.println("------------USER DATA  START---------------");
                    if (deletedUser != null) {
                        System.out.println("!!!  DELETED USER !!!");
                        System.out.println(deletedUser.toString());
                    } else {
                        System.out.println(" No User with given ID ");
                        System.out.println("!!!  NO USER DELETED !!!");

                    }
                    ;
                    System.out.println("------------USER DATA    END---------------");
                    break;
                case 0:
                    System.exit(0);
                    //TODO dodać modyfikację, usuwanie i wyszukiwanie rekordów
            }
        }
    }
}