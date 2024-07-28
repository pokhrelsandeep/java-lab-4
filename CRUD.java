import java.sql.*;
import java.util.Scanner;

public class CRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/RRCollege";
    private static final String USER = "root";
    private static final String PASSWORD = "user";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nCRUD Operations:");
            System.out.println("1. Add Record");
            System.out.println("2. Remove Record");
            System.out.println("3. Update Record");
            System.out.println("4. View Records");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addRecord(scanner);
                    break;
                case 2:
                    removeRecord(scanner);
                    break;
                case 3:
                    updateRecord(scanner);
                    break;
                case 4:
                    viewRecords();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addRecord(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Enter roll: ");
            int roll = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter address: ");
            String address = scanner.nextLine();

            String query = "INSERT INTO students (roll, name, address) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, roll);
                stmt.setString(2, name);
                stmt.setString(3, address);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Record added successfully!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void removeRecord(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Enter roll to remove: ");
            int roll = scanner.nextInt();

            String query = "DELETE FROM students WHERE roll = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, roll);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Record removed successfully!");
                } else {
                    System.out.println("No record found with the given roll.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateRecord(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Enter roll to update: ");
            int roll = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.print("Enter new name: ");
            String name = scanner.nextLine();

            System.out.print("Enter new address: ");
            String address = scanner.nextLine();

            String query = "UPDATE students SET name = ?, address = ? WHERE roll = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setInt(3, roll);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Record updated successfully!");
                } else {
                    System.out.println("No record found with the given roll.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewRecords() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM students";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                System.out.println("| Roll | Name           | Address        |");
                while (rs.next()) {
                    int roll = rs.getInt("roll");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    System.out.printf("| %-4d | %-14s | %-14s |%n", roll, name, address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
