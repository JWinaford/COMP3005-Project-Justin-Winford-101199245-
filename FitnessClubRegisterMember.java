import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FitnessClubRegisterMember extends JFrame implements ActionListener {

    // UI components for the registration form
    private JTextField nameTextField, emailTextField, phoneTextField;
    private JButton registerButton, cancelButton;

    // Constructor to initialize UI components
    public FitnessClubRegisterMember() {
        super("Health and Fitness Club - Register Member");

        // Create panels for better organization (optional)
        JPanel registrationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Create labels and text fields for member information
        JLabel nameLabel = new JLabel("Name:");
        nameTextField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneTextField = new JTextField(20);

        // Add labels and text fields to the registration panel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        registrationPanel.add(nameLabel, c);

        c.gridx = 1;
        registrationPanel.add(nameTextField, c);

        c.gridy = 1;
        c.gridx = 0;
        registrationPanel.add(emailLabel, c);

        c.gridx = 1;
        registrationPanel.add(emailTextField, c);

        c.gridy = 2;
        c.gridx = 0;
        registrationPanel.add(phoneLabel, c);

        c.gridx = 1;
        registrationPanel.add(phoneTextField, c);

        // Create buttons for registration and cancellation
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");

        // Add action listeners to buttons
        registerButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Add buttons to the registration panel
        c.gridy = 3;
        c.gridx = 0;
        registrationPanel.add(registerButton, c);

        c.gridx = 1;
        registrationPanel.add(cancelButton, c);

        // Add the registration panel to the main window
        getContentPane().add(registrationPanel);

        // Set window properties
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            // Handle registration logic
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String phone = phoneTextField.getText();

            // Basic validation (replace with more robust validation)
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                return;
            }

            // Implement logic to save member information to the database (using JDBC or your preferred method)
            // This example demonstrates a placeholder for database interaction
            System.out.println("Registering member: " + name + ", " + email + ", " + phone);
            // Replace with actual database connection and insertion logic

            // Show success message after successful registration (assuming successful database interaction)
            JOptionPane.showMessageDialog(this, "Member registration successful!");

            // Close the registration window after confirmation
            this.dispose();  // Call dispose() to close the current window

        } else if (e.getSource() == cancelButton) {
            // Close the registration window with confirmation
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to cancel registration?", "Cancel Registration",
                    JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        }
    }
}
