import org.postgresql.Driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class FitnessClubLogin extends JFrame implements ActionListener {

    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public FitnessClubLogin() {
        super("Health and Fitness Club Management");

        // Create UI components
        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Add action listeners
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        // Add color
        loginButton.setBackground(Color.decode("#3399FF")); // Blue background
        loginButton.setForeground(Color.WHITE);           // White text
        registerButton.setBackground(Color.decode("#E0E0E0")); // Gray background
        registerButton.setForeground(Color.BLACK);          // Black text

        // Layout components using GridBagLayout
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(usernameLabel, c);

        c.gridx = 1;
        loginPanel.add(usernameTextField, c);

        c.gridy = 1;
        c.gridx = 0;
        loginPanel.add(passwordLabel, c);

        c.gridx = 1;
        loginPanel.add(passwordField, c);

        c.gridy = 2;
        c.gridx = 1;
        loginPanel.add(loginButton, c);

        c.gridy = 3;
        c.gridx = 1;
        loginPanel.add(registerButton, c);

        // Add login panel to main window
        getContentPane().add(loginPanel);

        // Set window properties
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());

            String url = "jdbc:postgresql://localhost:5432/Test2";
            String user = "postgres";
            String dbPassword = "admin";

            try {
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(url, user, dbPassword);

                if (connection != null) {
                    // Create a statement object
                    Statement statement = connection.createStatement();

                    //SQL query for login validation
                    String query = "SELECT * FROM LoginInfo WHERE username = '" + username + "'" +
                            " AND password = '" + password + "'";

                    // Execute the query and get the result set
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        // Login successful
                        String role = resultSet.getString("Role");

                        // Close resources
                        resultSet.close();
                        statement.close();
                        connection.close();

                        // Redirect to appropriate page based on role
                        if (role.equals("Member")|| role.equals("member")) {
                            new MemberPage(username);  // Replace with actual MemberPage creation
                        } else if (role.equals("Admin")|| role.equals("admin")) {
                            new AdminPage(username);  // Replace with actual AdminPage creation
                        } else if (role.equals("Trainer") || role.equals("trainer")) {
                            new TrainerPage(username);  // Replace with actual TrainerPage creation
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid user role.");
                        }

                        // Clear login information
                        usernameTextField.setText("");
                        passwordField.setText("");
                    } else {
                        // Login failed
                        JOptionPane.showMessageDialog(this, "Invalid username or password.");
                    }
                } else {
                    System.out.println("Failed to connect to database.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred. Please try again.");
            }

        } else if (e.getSource() == registerButton) {
            // Open the registration window
            new FitnessClubRegisterMember();
        }
    }
    public static void main(String[] args) {
        // Create an instance of the FitnessClubLogin class and make it visible
        new FitnessClubLogin();
    }
}
