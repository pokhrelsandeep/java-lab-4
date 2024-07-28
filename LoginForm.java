import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame implements ActionListener {
    private JTextField userIDField;
    private JPasswordField passwordField;
    private JButton okButton, cancelButton;

    public LoginForm() {
        setTitle("Login Form");
        setLayout(null);

        JLabel l1 = new JLabel("User ID:");
        JLabel l2 = new JLabel("Password:");

        userIDField = new JTextField(20);
        passwordField = new JPasswordField(20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        l1.setBounds(50, 50, 100, 30);
        l2.setBounds(50, 100, 100, 30);
        userIDField.setBounds(150, 50, 150, 30);
        passwordField.setBounds(150, 100, 150, 30);
        okButton.setBounds(50, 150, 100, 30);
        cancelButton.setBounds(200, 150, 100, 30);

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'l') {
                    okButton.doClick();
                } else if (e.getKeyChar() == 'c') {
                    cancelButton.doClick();
                }
            }
        };

        userIDField.addKeyListener(keyAdapter);
        passwordField.addKeyListener(keyAdapter);

        add(l1);
        add(userIDField);
        add(l2);
        add(passwordField);
        add(okButton);
        add(cancelButton);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            String userID = userIDField.getText();
            String password = new String(passwordField.getPassword());

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/RRCollege", "root", "user");
                String query = "SELECT * FROM account WHERE uid = ? AND password = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, userID);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid user ID or password.");
                }
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelButton) {
            userIDField.setText("");
            passwordField.setText("");
            userIDField.requestFocus();
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
