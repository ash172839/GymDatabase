import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GymManagementGUI extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gymdatabase"; // Replace with your actual DB URL
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "ashishr987*5"; // Replace with your MySQL password

    private JTabbedPane tabbedPane;
    
    // Components for Members Tab
    private JTable membersTable;
    private DefaultTableModel membersTableModel;
    private JTextField nameField;
    private JTextField passwordField;
    private JTextField membershipPlanField;
    private JTextField ageField;

    // Components for Classes Tab
    private JTable classesTable;
    private DefaultTableModel classesTableModel;
    private JTextField classNameField;
    private JTextField classTimeField;
    private JTextField trainerIdField;

    // Components for Diet Plans Tab
    private JTable dietPlansTable;
    private DefaultTableModel dietPlansTableModel;
    private JTextField foodItemField;
    private JTextArea descriptionArea;
    private JTextField calorieCountField;

    // Components for Payments Tab
    private JTable paymentsTable;
    private DefaultTableModel paymentsTableModel;
    private JTextField paymentMemberIdField;
    private JTextField amountField;
    private JTextField paymentDateField;

    // Components for Trainers Tab
    private JTable trainersTable;
    private DefaultTableModel trainersTableModel;
    private JTextField trainerNameField;
    private JTextField expertiseField;

    // Components for Workout Plans Tab
    private JTable workoutPlansTable;
    private DefaultTableModel workoutPlansTableModel;
    private JTextField workoutMemberIdField;
    private JTextArea planDescriptionArea;

    public GymManagementGUI() {
        setTitle("Gym Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        
        // Initialize all tabs
        initializeMembersTab();
        initializeClassesTab();
        initializeDietPlansTab();
        initializePaymentsTab();
        initializeTrainersTab();
        initializeWorkoutPlansTab();
    }

    // Initialize Members Tab
    private void initializeMembersTab() {
        JPanel panel = new JPanel(new BorderLayout());
        membersTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Membership Plan"}, 0);
        membersTable = new JTable(membersTableModel);
        JScrollPane scrollPane = new JScrollPane(membersTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        inputPanel.add(passwordField);

        inputPanel.add(new JLabel("Membership Plan:"));
        membershipPlanField = new JTextField();
        inputPanel.add(membershipPlanField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        JButton addButton = new JButton("Add Member");
        addButton.addActionListener(new AddMemberAction());
        inputPanel.add(addButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        loadMembers();
        tabbedPane.addTab("Members", panel);
    }

    // Initialize Classes Tab
    private void initializeClassesTab() {
        JPanel panel = new JPanel(new BorderLayout());
        classesTableModel = new DefaultTableModel(new String[]{"ID", "Class Name", "Class Time", "Trainer ID"}, 0);
        classesTable = new JTable(classesTableModel);
        JScrollPane scrollPane = new JScrollPane(classesTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Class Name:"));
        classNameField = new JTextField();
        inputPanel.add(classNameField);

        inputPanel.add(new JLabel("Class Time (HH:MM:SS):"));
        classTimeField = new JTextField();
        inputPanel.add(classTimeField);

        inputPanel.add(new JLabel("Trainer ID:"));
        trainerIdField = new JTextField();
        inputPanel.add(trainerIdField);

        JButton addButton = new JButton("Add Class");
        addButton.addActionListener(new AddClassAction());
        inputPanel.add(addButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        loadClasses();
        tabbedPane.addTab("Classes", panel);
    }

    // Initialize Diet Plans Tab
    private void initializeDietPlansTab() {
        JPanel panel = new JPanel(new BorderLayout());
        dietPlansTableModel = new DefaultTableModel(new String[]{"ID", "Food Item", "Description", "Calorie Count"}, 0);
        dietPlansTable = new JTable(dietPlansTableModel);
        JScrollPane scrollPane = new JScrollPane(dietPlansTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Food Item:"));
        foodItemField = new JTextField();
        inputPanel.add(foodItemField);

        inputPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea();
        inputPanel.add(new JScrollPane(descriptionArea));

        inputPanel.add(new JLabel("Calorie Count:"));
        calorieCountField = new JTextField();
        inputPanel.add(calorieCountField);

        JButton addButton = new JButton("Add Diet Plan");
        addButton.addActionListener(new AddDietPlanAction());
        inputPanel.add(addButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        loadDietPlans();
        tabbedPane.addTab("Diet Plans", panel);
    }

    // Initialize Payments Tab
    private void initializePaymentsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        paymentsTableModel = new DefaultTableModel(new String[]{"Payment ID", "Member ID", "Amount", "Date"}, 0);
        paymentsTable = new JTable(paymentsTableModel);
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Member ID:"));
        paymentMemberIdField = new JTextField();
        inputPanel.add(paymentMemberIdField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        paymentDateField = new JTextField();
        inputPanel.add(paymentDateField);

        JButton addButton = new JButton("Add Payment");
        addButton.addActionListener(new AddPaymentAction());
        inputPanel.add(addButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        loadPayments();
        tabbedPane.addTab("Payments", panel);
    }

    // Initialize Trainers Tab
    private void initializeTrainersTab() {
        JPanel panel = new JPanel(new BorderLayout());
        trainersTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Expertise"}, 0);
        trainersTable = new JTable(trainersTableModel);
        JScrollPane scrollPane = new JScrollPane(trainersTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name:"));
        trainerNameField = new JTextField();
        inputPanel.add(trainerNameField);

        inputPanel.add(new JLabel("Expertise:"));
        expertiseField = new JTextField();
        inputPanel.add(expertiseField);

        JButton addButton = new JButton("Add Trainer");
        addButton.addActionListener(new AddTrainerAction());
        inputPanel.add(addButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        loadTrainers();
        tabbedPane.addTab("Trainers", panel);
    }

    // Initialize Workout Plans Tab
    private void initializeWorkoutPlansTab() {
        JPanel panel = new JPanel(new BorderLayout());
        workoutPlansTableModel = new DefaultTableModel(new String[]{"Plan ID", "Member ID", "Plan Description"}, 0);
        workoutPlansTable = new JTable(workoutPlansTableModel);
        JScrollPane scrollPane = new JScrollPane(workoutPlansTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Member ID:"));
        workoutMemberIdField = new JTextField();
        inputPanel.add(workoutMemberIdField);

        inputPanel.add(new JLabel("Plan Description:"));
        planDescriptionArea = new JTextArea();
        inputPanel.add(new JScrollPane(planDescriptionArea));

        JButton addButton = new JButton("Add Workout Plan");
        addButton.addActionListener(new AddWorkoutPlanAction());
        inputPanel.add(addButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        loadWorkoutPlans();
        tabbedPane.addTab("Workout Plans", panel);
    }

    // Load Members from the database
    private void loadMembers() {
        String query = "SELECT * FROM members";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            membersTableModel.setRowCount(0);
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("age"),
                    resultSet.getString("membership_plan")
                };
                membersTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load Classes from the database
    private void loadClasses() {
        String query = "SELECT * FROM classes";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            classesTableModel.setRowCount(0);
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("id"),
                    resultSet.getString("class_name"),
                    resultSet.getString("class_time"),
                    resultSet.getInt("trainer_id")
                };
                classesTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load Diet Plans from the database
    private void loadDietPlans() {
        String query = "SELECT * FROM diet_plans";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            dietPlansTableModel.setRowCount(0);
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("id"),
                    resultSet.getString("food_item"),
                    resultSet.getString("description"),
                    resultSet.getInt("calorie_count")
                };
                dietPlansTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load Payments from the database
    private void loadPayments() {
        String query = "SELECT * FROM payments";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            paymentsTableModel.setRowCount(0);
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("payment_id"),
                    resultSet.getInt("member_id"),
                    resultSet.getDouble("amount"),
                    resultSet.getDate("payment_date")
                };
                paymentsTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load Trainers from the database
    private void loadTrainers() {
        String query = "SELECT * FROM trainers";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            trainersTableModel.setRowCount(0);
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("expertise")
                };
                trainersTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load Workout Plans from the database
    private void loadWorkoutPlans() {
        String query = "SELECT * FROM workout_plans";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            workoutPlansTableModel.setRowCount(0);
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("plan_id"),
                    resultSet.getInt("member_id"),
                    resultSet.getString("plan_description")
                };
                workoutPlansTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Member Action
    private class AddMemberAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String password = passwordField.getText();
            String membershipPlan = membershipPlanField.getText();
            int age;
            try {
                age = Integer.parseInt(ageField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GymManagementGUI.this, "Please enter a valid age.");
                return;
            }

            String query = "INSERT INTO members (name, password, membership_plan, age) VALUES (?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, membershipPlan);
                preparedStatement.setInt(4, age);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(GymManagementGUI.this, "A new member was added successfully!");
                    loadMembers(); // Refresh member list
                    clearMemberFields(); // Clear input fields
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void clearMemberFields() {
            nameField.setText("");
            passwordField.setText("");
            membershipPlanField.setText("");
            ageField.setText("");
        }
    }

    // Add Class Action
    private class AddClassAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String className = classNameField.getText();
            String classTime = classTimeField.getText();
            int trainerId;
            try {
                trainerId = Integer.parseInt(trainerIdField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GymManagementGUI.this, "Please enter a valid Trainer ID.");
                return;
            }

            String query = "INSERT INTO classes (class_name, class_time, trainer_id) VALUES (?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, className);
                preparedStatement.setString(2, classTime);
                preparedStatement.setInt(3, trainerId);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(GymManagementGUI.this, "A new class was added successfully!");
                    loadClasses(); // Refresh class list
                    clearClassFields(); // Clear input fields
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void clearClassFields() {
            classNameField.setText("");
            classTimeField.setText("");
            trainerIdField.setText("");
        }
    }

    // Add Diet Plan Action
    private class AddDietPlanAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String foodItem = foodItemField.getText();
            String description = descriptionArea.getText();
            int calorieCount;
            try {
                calorieCount = Integer.parseInt(calorieCountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GymManagementGUI.this, "Please enter a valid Calorie Count.");
                return;
            }

            String query = "INSERT INTO diet_plans (food_item, description, calorie_count) VALUES (?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, foodItem);
                preparedStatement.setString(2, description);
                preparedStatement.setInt(3, calorieCount);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(GymManagementGUI.this, "A new diet plan was added successfully!");
                    loadDietPlans(); // Refresh diet plan list
                    clearDietPlanFields(); // Clear input fields
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void clearDietPlanFields() {
            foodItemField.setText("");
            descriptionArea.setText("");
            calorieCountField.setText("");
        }
    }

    // Add Payment Action
    private class AddPaymentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int memberId;
            double amount;
            String paymentDate = paymentDateField.getText();

            try {
                memberId = Integer.parseInt(paymentMemberIdField.getText());
                amount = Double.parseDouble(amountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GymManagementGUI.this, "Please enter valid Member ID and Amount.");
                return;
            }

            String query = "INSERT INTO payments (member_id, amount, payment_date) VALUES (?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setInt(1, memberId);
                preparedStatement.setDouble(2, amount);
                preparedStatement.setDate(3, Date.valueOf(paymentDate));
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(GymManagementGUI.this, "A new payment was added successfully!");
                    loadPayments(); // Refresh payment list
                    clearPaymentFields(); // Clear input fields
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void clearPaymentFields() {
            paymentMemberIdField.setText("");
            amountField.setText("");
            paymentDateField.setText("");
        }
    }

    // Add Trainer Action
    private class AddTrainerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = trainerNameField.getText();
            String expertise = expertiseField.getText();

            String query = "INSERT INTO trainers (name, expertise) VALUES (?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, expertise);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(GymManagementGUI.this, "A new trainer was added successfully!");
                    loadTrainers(); // Refresh trainer list
                    clearTrainerFields(); // Clear input fields
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void clearTrainerFields() {
            trainerNameField.setText("");
            expertiseField.setText("");
        }
    }

    // Add Workout Plan Action
    private class AddWorkoutPlanAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int memberId;
            String planDescription = planDescriptionArea.getText();

            try {
                memberId = Integer.parseInt(workoutMemberIdField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GymManagementGUI.this, "Please enter a valid Member ID.");
                return;
            }

            String query = "INSERT INTO workout_plans (member_id, plan_description) VALUES (?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setInt(1, memberId);
                preparedStatement.setString(2, planDescription);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(GymManagementGUI.this, "A new workout plan was added successfully!");
                    loadWorkoutPlans(); // Refresh workout plan list
                    clearWorkoutPlanFields(); // Clear input fields
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void clearWorkoutPlanFields() {
            workoutMemberIdField.setText("");
            planDescriptionArea.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymManagementGUI gui = new GymManagementGUI();
            gui.setVisible(true);
        });
    }
}
