import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MemberPage extends JFrame implements ActionListener {
    private String memberID;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Double weightGoal;
    private Integer weeklyVisitGoal;
    private Double weight;
    private Double height;
    private String exerciseRoutine;
    private final String username;
    private JButton updateInfoButton, updateGoalsButton, updateHealthStatsButton, bookPersonalSessionButton, bookClassButton;
    private JTextArea routineTextArea, achievementsTextArea, statisticsTextArea;

    public MemberPage(String username) {
        super("Member Page - Welcome " + username);

        this.username = username;

        // Create UI components
        JPanel dashboardPanel = createDashboardPanel(); // Call method to create dashboard panel

        updateInfoButton = new JButton("Update Personal Info");
        updateGoalsButton = new JButton("Update Fitness Goals");
        updateHealthStatsButton = new JButton("Update Health Metrics");
        bookPersonalSessionButton = new JButton("Book Personal Training");
        bookClassButton = new JButton("Book Class");

        // Add action listeners
        updateInfoButton.addActionListener(this);
        updateGoalsButton.addActionListener(this);
        updateHealthStatsButton.addActionListener(this);
        bookPersonalSessionButton.addActionListener(this);
        bookClassButton.addActionListener(this);

        // Layout components using GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        buttonPanel.add(updateInfoButton);
        buttonPanel.add(updateGoalsButton);
        buttonPanel.add(updateHealthStatsButton);
        buttonPanel.add(bookPersonalSessionButton);
        buttonPanel.add(bookClassButton);
        // Add an empty panel to balance the layout (optional)
        buttonPanel.add(new JPanel());

        // Main content layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(dashboardPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createDashboardPanel() {
        String url = "jdbc:postgresql://localhost:5432/Test2";
        String user = "postgres";
        String password = "admin";

        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5)); // 3 rows for routine, achievements, and statistics

        try {
            Class.forName("org.postgresql.Driver"); // Load the PostgreSQL JDBC driver

            Connection connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("Connected to database!");

                String query = "SELECT * " +
                        "FROM Member " +
                        "WHERE Username ='" + this.username + "'";

                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    memberID = resultSet.getString("MemberID");
                    name = resultSet.getString("Name");
                    phone = resultSet.getString("Phone");
                    email = resultSet.getString("Email");
                    address = resultSet.getString("Address");
                    weightGoal = resultSet.getDouble("WeightGoal");
                    weeklyVisitGoal = resultSet.getInt("WeeklyVisitGoal");
                    weight = resultSet.getDouble("Weight");
                    height = resultSet.getDouble("Height");
                    exerciseRoutine = resultSet.getString("ExerciseRoutine");
                }

                resultSet.close();
                statement.close();
                connection.close();
            } else {
                System.out.println("Connection failed!");
            }
        } catch (Exception ex) {
        }

        // Routine
        JLabel routineLabel = new JLabel("**Current Exercise Routine:**");
        panel.add(routineLabel);
        routineTextArea = new JTextArea();
        routineTextArea.setEditable(false); // Prevent editing on dashboard
        panel.add(routineTextArea);

        // Achievements
        JLabel achievementsLabel = new JLabel("**Recent Achievements:**");
        panel.add(achievementsLabel);
        achievementsTextArea = new JTextArea();
        achievementsTextArea.setEditable(false); // Prevent editing on dashboard
        panel.add(achievementsTextArea);

        // Statistics
        JLabel statisticsLabel = new JLabel("**Health Statistics:**");
        panel.add(statisticsLabel);
        statisticsTextArea = new JTextArea();
        statisticsTextArea.setEditable(false); // Prevent editing on dashboard
        panel.add(statisticsTextArea);

        // Replace with actual data retrieval and display logic
        routineTextArea.setText(exerciseRoutine);
        achievementsTextArea.setText("Your weekly visit goal is:" + weeklyVisitGoal + "with a weight goal of:" + weightGoal);
        statisticsTextArea.setText("Current Weight:" + weight + "kg, Current Height:" + height + "cm");

        return panel;
    }

        @Override
        public void actionPerformed (ActionEvent e){
            if (e.getSource() == updateInfoButton) {
                // Open Update Personal Info page (implementation needed)
                JOptionPane.showMessageDialog(this, "Update Personal Info functionality not yet implemented.");
            } else if (e.getSource() == updateGoalsButton) {
                // Create a new dialog to get user input for new goals
                JPanel goalPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                JLabel weightGoalLabel = new JLabel("New Weight Goal (kg):");
                goalPanel.add(weightGoalLabel);
                JTextField weightGoalField = new JTextField();
                goalPanel.add(weightGoalField);
                JLabel weeklyVisitGoalLabel = new JLabel("New Weekly Visit Goal:");
                goalPanel.add(weeklyVisitGoalLabel);
                JTextField weeklyVisitGoalField = new JTextField();
                goalPanel.add(weeklyVisitGoalField);

                int result = JOptionPane.showConfirmDialog(this, goalPanel, "Update Fitness Goals", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String newWeightGoalStr = weightGoalField.getText();
                    String newWeeklyVisitGoalStr = weeklyVisitGoalField.getText();

                    // Validate user input (optional)
                    // You can add checks here to ensure weight goal is a valid number and weekly visit goal is a positive integer

                    // Update database with new goals if input is valid
                    try {
                        String url = "jdbc:postgresql://localhost:5432/Test2";
                        String user = "postgres";
                        String password = "admin";
                        Class.forName("org.postgresql.Driver");

                        Connection connection = DriverManager.getConnection(url, user, password);

                        if (connection != null) {
                            String query = "UPDATE Member " +
                                    "SET WeightGoal = newWeightGoalStr, WeeklyVisitGoal = newWeeklyVisitGoalStr" +
                                    "WHERE Username ='" + this.username + "'";

                            PreparedStatement statement = connection.prepareStatement(query);
                            statement.setDouble(1, Double.parseDouble(newWeightGoalStr)); // Parse weight goal to double
                            statement.setInt(2, Integer.parseInt(newWeeklyVisitGoalStr)); // Parse visit goal to int
                            statement.setString(3, this.username);

                            int rowsUpdated = statement.executeUpdate();

                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(this, "Fitness Goals Updated Successfully!");
                                // Update dashboard panel to reflect changes (optional)
                                // You can call createDashboardPanel again to refresh the panel with new data
                            } else {
                                JOptionPane.showMessageDialog(this, "Error updating goals. Please try again.");
                            }

                            statement.close();
                            connection.close();
                        } else {
                            System.out.println("Connection failed!");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.");

                    }
                }
            } else if (e.getSource() == updateHealthStatsButton) {
                String query = "UPDATE Member " +
                        "SET Weight = newWeight, Height  = newHeight" +
                        "WHERE Username ='" + this.username + "'";
                JOptionPane.showMessageDialog(this, "Update Health Metrics functionality not yet implemented.");
            } else if (e.getSource() == bookPersonalSessionButton) {
                // Open Book Personal Training page (implementation needed)
                JOptionPane.showMessageDialog(this, "Book Personal Training functionality not yet implemented.");
            } else if (e.getSource() == bookClassButton) {
                // Open Book Class page (implementation needed)
                JOptionPane.showMessageDialog(this, "Book Class functionality not yet implemented.");
            }
        }
    }

