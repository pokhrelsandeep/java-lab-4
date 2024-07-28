import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignUpForm extends JFrame implements ActionListener {
    JTextField t1, t2, t3, t6;
    JRadioButton rb1, rb2;
    JCheckBox cb1, cb2, cb3;
    JComboBox<String> cb;
    JButton b1, b2;

    public SignUpForm() {
        setTitle("Sign Up Form");
        setLayout(null);

        JLabel l1 = new JLabel("ID:");
        JLabel l2 = new JLabel("Name:");
        JLabel l3 = new JLabel("Email:");
        JLabel l4 = new JLabel("Gender:");
        JLabel l5 = new JLabel("Hobbies:");
        JLabel l6 = new JLabel("Country:");
        JLabel l7 = new JLabel("Local Address:");

        t1 = new JTextField();
        t2 = new JTextField();
        t3 = new JTextField();
        t6 = new JTextField();

        rb1 = new JRadioButton("Male");
        rb2 = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);

        cb1 = new JCheckBox("Reading");
        cb2 = new JCheckBox("Traveling");
        cb3 = new JCheckBox("Cooking");

        cb = new JComboBox<>(new String[]{"USA", "UK", "India", "Australia"});

        b1 = new JButton("Submit");
        b2 = new JButton("Lookup");

        l1.setBounds(30, 30, 100, 30);
        t1.setBounds(150, 30, 150, 30);
        l2.setBounds(30, 70, 100, 30);
        t2.setBounds(150, 70, 150, 30);
        l3.setBounds(30, 110, 100, 30);
        t3.setBounds(150, 110, 150, 30);
        l4.setBounds(30, 150, 100, 30);
        rb1.setBounds(150, 150, 70, 30);
        rb2.setBounds(230, 150, 70, 30);
        l5.setBounds(30, 190, 100, 30);
        cb1.setBounds(150, 190, 100, 30);
        cb2.setBounds(250, 190, 100, 30);
        cb3.setBounds(350, 190, 100, 30);
        l6.setBounds(30, 230, 100, 30);
        cb.setBounds(150, 230, 150, 30);
        l7.setBounds(30, 270, 100, 30);
        t6.setBounds(150, 270, 150, 30);
        b1.setBounds(50, 320, 100, 30);
        b2.setBounds(200, 320, 100, 30);

        add(l1); add(t1); add(l2); add(t2); add(l3); add(t3);
        add(l4); add(rb1); add(rb2); add(l5); add(cb1); add(cb2); add(cb3);
        add(l6); add(cb); add(l7); add(t6); add(b1); add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            Boolean ok = insertData();
            if(ok){
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t6.setText("");
            }
        } else if (e.getSource() == b2) {
            lookupData();
        }
    }

    private boolean insertData() {
        String id = t1.getText();
        String name = t2.getText();
        String email = t3.getText();
        String gender = rb1.isSelected() ? "Male" : "Female";
        String hobbies = "";
        if (cb1.isSelected()) hobbies += "Reading ";
        if (cb2.isSelected()) hobbies += "Traveling ";
        if (cb3.isSelected()) hobbies += "Cooking ";
        String country = (String) cb.getSelectedItem();
        String address = t6.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RRCollege", "root", "user");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO person (id, name, email, gender, hobbies, country, address) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, gender);
            ps.setString(5, hobbies);
            ps.setString(6, country);
            ps.setString(7, address);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data Inserted Successfully");
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Exception has Occured.");
            ex.printStackTrace();
            return false;

        }
    }

    private void lookupData() {
        String id = JOptionPane.showInputDialog("Enter ID to lookup:");
        if (id == null || id.isEmpty()) {
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RRCollege", "root", "user");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM person WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String data = "ID: " + rs.getString("id") + "\nName: " + rs.getString("name") + "\nEmail: " + rs.getString("email") +
                              "\nGender: " + rs.getString("gender") + "\nHobbies: " + rs.getString("hobbies") +
                              "\nCountry: " + rs.getString("country") + "\nAddress: " + rs.getString("address");
                JOptionPane.showMessageDialog(this, data);
            } else {
                JOptionPane.showMessageDialog(this, "No record found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SignUpForm();
    }
}
