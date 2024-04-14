import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TrainerPage extends JFrame implements ActionListener {

    private final String username;
    private JButton manageScheduleButton, viewReportsButton;

    public TrainerPage(String username) {
        super("Trainer Page");

        this.username = username;

        // Create UI components
        manageScheduleButton = new JButton("Manage Schedule");
        viewReportsButton = new JButton("View Reports");

        // Add action listeners
        manageScheduleButton.addActionListener(this);
        viewReportsButton.addActionListener(this);

        // Layout components using FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(manageScheduleButton);
        buttonPanel.add(viewReportsButton);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == manageScheduleButton) {
            // Open Schedule Management window
            new ScheduleManagement(this.username);
        } else if (e.getSource() == viewReportsButton) {

        }
    }
}

// ScheduleManagement class (inner class for simplicity)
class ScheduleManagement extends JFrame implements ActionListener {

    public String username;
    private JButton viewPersonalSessionsButton, viewClassesButton, addNewSessionButton;

    public ScheduleManagement(String username) {
        super("Schedule Management");

        this.username = username;

        // Create UI components
        viewPersonalSessionsButton = new JButton("View Personal Sessions");
        viewClassesButton = new JButton("View Classes");
        addNewSessionButton = new JButton("Add new Personal Session");

        // Add action listeners
        viewPersonalSessionsButton.addActionListener(this);
        viewClassesButton.addActionListener(this);
        addNewSessionButton.addActionListener(this);


        // Layout components using FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(viewPersonalSessionsButton);
        buttonPanel.add(viewClassesButton);
        buttonPanel.add(addNewSessionButton);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
        setSize(500,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Closes child window only
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewPersonalSessionsButton) {

            String url = "jdbc:postgresql://localhost:5432/Test2";
            String user = "postgres";
            String dbPassword = "admin";

            try {
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(url, user, dbPassword);

                if (connection != null) {
                    // Create a statement object
                    Statement statement = connection.createStatement();

                    String query = "SELECT ps.*, m.Name as MemberName " +
                            "FROM PersonalSession ps " +
                            "INNER JOIN Trainer t ON ps.TrainerID = t.TrainerID " +
                            "INNER JOIN Member m ON ps.MemberID = m.MemberID " +
                            "WHERE t.Username ='" + username + "'";


                PreparedStatement preparedStatement = connection.prepareStatement(query);


                // Execute the query and process results
                ResultSet resultSet = preparedStatement.executeQuery();
                String message = "** Personal Sessions **\n";

                while (resultSet.next()) {
                    // Process each personal session record here
                    Date date = resultSet.getDate("Date");
                    String time = resultSet.getString("Time");
                    int duration = resultSet.getInt("Duration");
                    String Name = resultSet.getString("MemberName");


                    // Format the message
                    message += "Date: " + date + "\n";
                    message += "Time: " + time + "\n";
                    message += "Duration: " + duration + " minutes\n";
                    message += "Member Name: " + Name + "\n\n";
                }

                // Close resources
                resultSet.close();
                preparedStatement.close();
                statement.close();
                connection.close();

                // Display the message
                if (!message.equals("** Personal Sessions **\n")) { // Check for empty results
                    JOptionPane.showMessageDialog(this, message);
                } else {
                    JOptionPane.showMessageDialog(this, "No personal sessions found.");
                }
            }} catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "An error occurred. Please try again.");
                }
            } else if (e.getSource() == viewClassesButton) {

                String url = "jdbc:postgresql://localhost:5432/Test2";
                String user = "postgres";
                String dbPassword = "admin";

                try {
                    Class.forName("org.postgresql.Driver");
                    Connection connection = DriverManager.getConnection(url, user, dbPassword);

                    if (connection != null) {
                        // Get TrainerID based on username
                        String trainerIdQuery = "SELECT TrainerID " +
                                "FROM Trainer " +
                                "WHERE Username ='" + this.username + "'";
                        PreparedStatement trainerIdStatement = connection.prepareStatement(trainerIdQuery);
                        //trainerIdStatement.setString(1, this.username); // Use username from the object
                        ResultSet trainerIdResult = trainerIdStatement.executeQuery();


                        if (trainerIdResult.next()) {
                            // Query to retrieve classes for the trainer
                            String classQuery = "SELECT c.*, r.Capacity " +
                                    "FROM Class c, Room r " +
                                    "WHERE c.InstructorID ='" + trainerIdResult.getString("TrainerID") + "'";
                            PreparedStatement classStatement = connection.prepareStatement(classQuery);
                            ResultSet classResult = classStatement.executeQuery();

                            String message = "** Your Classes **\n";

                            while (classResult.next()) {
                                // Process each class record here
                                String name = classResult.getString("Name");
                                String dayName = classResult.getString("Day");
                                String startTime = classResult.getString("Time");
                                int capacity = classResult.getInt("Capacity");

                                // Format the message
                                message += "Name: " + name + "\n";
                                message += "Day: " + dayName + "\n";
                                message += "Time: " + startTime + "\n";
                                message += "Capacity: " + capacity + "\n\n";
                            }

                            // Close resources for trainer ID query
                            trainerIdResult.close();
                            trainerIdStatement.close();

                            // Close resources for class query
                            classResult.close();
                            classStatement.close();

                            // Display the message
                            if (!message.equals("** Your Classes **\n")) { // Check for empty results
                                JOptionPane.showMessageDialog(this, message);
                            } else {
                                JOptionPane.showMessageDialog(this, "No classes found.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Error: Trainer not found.");
                        }

                        connection.close(); // Close connection after all queries
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: Database connection failed.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "An error occurred. Please try again.");
                }
        }else if (e.getSource() == addNewSessionButton) {
            // Open Add New Session window
            JDialog addNewSessionDialog = new JDialog(this, "Add New Session", true); // Modal dialog

            // Create input fields and labels
            JLabel dateLabel = new JLabel("Date (YYYY-MM-DD): ");
            JTextField dateField = new JTextField(10);
            JLabel timeLabel = new JLabel("Time (HH:MM): ");
            JTextField timeField = new JTextField(5);
            JLabel durationLabel = new JLabel("Duration (minutes): ");
            JTextField durationField = new JTextField(5);
            JLabel costLabel = new JLabel("Cost ($): ");
            JTextField costField = new JTextField(5);

            // Create a button to submit the new session information
            JButton submitButton = new JButton("Submit");

            // Layout components using GridLayout
            JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
            inputPanel.add(dateLabel);
            inputPanel.add(dateField);
            inputPanel.add(timeLabel);
            inputPanel.add(timeField);
            inputPanel.add(durationLabel);
            inputPanel.add(durationField);
            inputPanel.add(costLabel);
            inputPanel.add(costField);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(submitButton);

            addNewSessionDialog.getContentPane().add(inputPanel, BorderLayout.CENTER);
            addNewSessionDialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            addNewSessionDialog.pack();
            addNewSessionDialog.setVisible(true);

            submitButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String dateStr = dateField.getText();
                    String timeStr = timeField.getText();
                    String durationStr = durationField.getText();
                    String costStr = costField.getText();

                    if (dateStr.isEmpty() || timeStr.isEmpty() || durationStr.isEmpty() || costStr.isEmpty()) {
                        JOptionPane.showMessageDialog(addNewSessionDialog, "Please fill in all fields.");
                        return;
                    }
                    try {
                        // Convert input to appropriate data types
                        Date date = Date.valueOf(dateStr);
                        java.sql.Time time = java.sql.Time.valueOf(timeStr);
                        int duration = Integer.parseInt(durationStr);
                        double cost = Double.parseDouble(costStr);

                        // Connect to the database
                        String url = "jdbc:postgresql://localhost:5432/Test2";
                        String user = "postgres";
                        String dbPassword = "admin";

                        Class.forName("org.postgresql.Driver");
                        Connection connection = DriverManager.getConnection(url, user, dbPassword);

                        if (connection != null) {

                            // Get TrainerID based on username
                            String trainerIdQuery = "SELECT TrainerID " +
                                    "FROM Trainer " +
                                    "WHERE Username = this.username";


                            PreparedStatement trainerIdStatement = connection.prepareStatement(trainerIdQuery);
                           // trainerIdStatement.setString(1, username);
                            ResultSet trainerIdResult = trainerIdStatement.executeQuery();

                            if (trainerIdResult.next()) {
                                int trainerId = trainerIdResult.getInt("TrainerID");
                                JOptionPane.showMessageDialog(addNewSessionDialog, "New session added successfully!");
                            } else {
                                JOptionPane.showMessageDialog(addNewSessionDialog, "Error: Trainer not found.");
                            }

                            //TODO Add sequence to count each SessionID(S1,S2,S3...)
                            //TODO F
                            String insertQuery = "INSERT INTO PersonalSession (SessionID, TrainerID, MemberID, Date, " +
                                    "Time, Duration,Cost) VALUES ('S1','T1', NULL, date, time, duration, cost)";
                            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                            insertStatement.executeQuery();

                            trainerIdResult.close();
                            trainerIdStatement.close();
                            insertStatement.close();
                        } else {
                            JOptionPane.showMessageDialog(addNewSessionDialog, "Error: Database connection failed.");
                        }

                        connection.close(); // Close connection even if memberId is -1 or trainer not found

                    }catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(addNewSessionDialog, "An error occurred: " + ex.getMessage());
                    }
                }
            });
        }
    }

}
