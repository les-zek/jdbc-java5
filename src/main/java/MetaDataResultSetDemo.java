import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class MetaDataResultSetDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {

        Connection connection = ConnectionDemo.getConnection();

        Statement selectAll = connection.createStatement();
        ResultSet records = selectAll.executeQuery("select * from demo");
        ResultSetMetaData metaData = records.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            System.out.print(metaData.getColumnName(i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            System.out.print(metaData.getColumnClassName(i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            System.out.print(metaData.getColumnType(i + 1) + " ");
        }
        System.out.println("--------------------------------------------");
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
    }


}
