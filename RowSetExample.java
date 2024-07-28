import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

public class RowSetExample {
    static  String URL = "jdbc:mysql://localhost:3306/RRCollege";
    static  String USER = "root";
    static  String PASSWORD = "user";

    public static void main(String[] args) {
        try {
            // Create a JdbcRowSet instance
            JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);

            rowSet.setCommand("SELECT roll, name, address FROM students");
            rowSet.execute();

            // Iterate through the RowSet
            System.out.println("RowSet Contents:");
            while (rowSet.next()) {
                System.out.println("Roll: " + rowSet.getInt("roll") + ", Name: " + rowSet.getString("name") + ", Address: " + rowSet.getString("address"));
            }

            // Update the second row
            rowSet.absolute(2);
            rowSet.updateString("name", "RowSet Updated Name");
            rowSet.updateRow();
            System.out.println("Updated second row.");

            // Insert a new row
            rowSet.moveToInsertRow();
            rowSet.updateInt("roll", 5);
            rowSet.updateString("name", "RowSet New Student");
            rowSet.updateString("address", "RowSet New Address");
            rowSet.insertRow();
            System.out.println("Inserted new row.");

            // Delete the last row
            rowSet.last();
            rowSet.deleteRow();
            System.out.println("Deleted last row.");

            rowSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
