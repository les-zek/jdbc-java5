package jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetaDataResultSetDemo {
    public static void main(String[] args) throws
            IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection connection = ConnectionDemo.getConnection();
        Statement selectAll = connection.createStatement();
        ResultSet records = selectAll.executeQuery("select * from demo");
        ResultSetMetaData metaData = records.getMetaData();
        int validColumns = 0;
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            if (metaData.getColumnName(i + 1).equals("id")) {
                validColumns++;
            }
            if (metaData.getColumnName(i + 1).equals("name")) {
                validColumns++;
            }
            if (metaData.getColumnName(i + 1).equals("points")) {
                validColumns++;
            }
        }
        List<Player> players = new ArrayList<>();
        if (validColumns == 3) {
            while (records.next()) {
                Player player = new Player(
                        records.getInt("id"),
                        records.getString("name"),
                        records.getInt("points")
                );
                players.add(player);
            }
        }
        players.forEach(System.out::println);

    }
}
