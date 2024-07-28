import java.sql.*;

public class ScrollableUpdatableResultSetExample {
     static  String URL = "jdbc:mysql://localhost:3306/RRCollege";
     static  String USER = "root";
     static  String PASSWORD = "user";

    public static void main(String[] args) throws Exception{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT roll, name, address FROM students");
            
            if (rs.absolute(2)) {
                System.out.println("Before Update: Roll = " + rs.getInt("roll") + ", Name = " + rs.getString("name") + ", Address = " + rs.getString("address"));
                rs.updateString("name", "Updated Name");
                rs.updateRow();
                System.out.println("After Update: Roll = " + rs.getInt("roll") + ", Name = " + rs.getString("name") + ", Address = " + rs.getString("address"));
            }
            conn.close();
    }
}
