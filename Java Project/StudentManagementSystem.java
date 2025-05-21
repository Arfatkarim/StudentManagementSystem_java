import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class StudentManagementSystem extends JFrame {
    // Data storage
    private ArrayList<Student> students = new ArrayList<>();
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTable studentTable;
    
    // Input fields
    private JTextField nameField, rollField, idField, emailField, deptField, cgpaField,
                      courseField, examField, resultField, facultyField, feeField;
    
    // Status display
    private JLabel statusLabel;
    private Timer statusTimer;
    
    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(0, 105, 92); // Deep Teal
    private static final Color SECONDARY_COLOR = new Color(245, 246, 245); // Off-White
    private static final Color ACCENT_COLOR = new Color(255, 111, 97); // Coral
    private static final Color HOVER_COLOR = new Color(38, 166, 154); // Light Teal
    private static final Color TEXT_COLOR = new Color(55, 71, 79); // Charcoal

    public StudentManagementSystem() {
        setTitle("Student Management System - Daffodil Institute of IT");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(SECONDARY_COLOR);

        // Header panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Input and control panel
        mainPanel.add(createInputPanel(), BorderLayout.WEST);

        // Student table
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);

        // Status bar
        statusLabel = new JLabel("Ready to manage student records");
        statusLabel.setFont(new Font("Roboto", Font.ITALIC, 14));
        statusLabel.setForeground(PRIMARY_COLOR);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        // Initialize status timer
        statusTimer = new Timer();

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Developer info
        JLabel devLabel = new JLabel(
            "<html><center>Developed by: Mohammad Arfat Karim, CST-5, Daffodil Institute of IT<br>" +
             "Email: arafatkarim37@gmail.com | Faculty: Santosh Shushil (Department Head)</center></html>"
        );
        devLabel.setForeground(SECONDARY_COLOR);
        devLabel.setFont(new Font("Roboto", Font.BOLD, 15));
        devLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(devLabel, BorderLayout.CENTER);

        // Links panel
        JPanel linksPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        linksPanel.setBackground(PRIMARY_COLOR);
        linksPanel.add(createLinkLabel("GitHub", "https://github.com/Arfatkarim"));
        linksPanel.add(createLinkLabel("LinkedIn", "https://www.linkedin.com/in/mohammad-arafat-karim-004a5129b"));
        linksPanel.add(createLinkLabel("Portfolio", "https://arfatkarim.netlify.app/"));
        headerPanel.add(linksPanel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(SECONDARY_COLOR);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                "Student Details",
                0, 0, new Font("Roboto", Font.BOLD, 16), PRIMARY_COLOR
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(11, 2, 10, 10));
        formPanel.setBackground(SECONDARY_COLOR);
        Font labelFont = new Font("Roboto", Font.PLAIN, 14);
        Font fieldFont = new Font("Roboto", Font.PLAIN, 14);

        formPanel.add(createStyledLabel("Name:", labelFont));
        nameField = createStyledTextField(fieldFont);
        formPanel.add(nameField);

        formPanel.add(createStyledLabel("Roll:", labelFont));
        rollField = createStyledTextField(fieldFont);
        formPanel.add(rollField);

        formPanel.add(createStyledLabel("ID No:", labelFont));
        idField = createStyledTextField(fieldFont);
        formPanel.add(idField);

        formPanel.add(createStyledLabel("Email:", labelFont));
        emailField = createStyledTextField(fieldFont);
        formPanel.add(emailField);

        formPanel.add(createStyledLabel("Department:", labelFont));
        deptField = createStyledTextField(fieldFont);
        formPanel.add(deptField);

        formPanel.add(createStyledLabel("CGPA:", labelFont));
        cgpaField = createStyledTextField(fieldFont);
        formPanel.add(cgpaField);

        formPanel.add(createStyledLabel("Course:", labelFont));
        courseField = createStyledTextField(fieldFont);
        formPanel.add(courseField);

        formPanel.add(createStyledLabel("Exam:", labelFont));
        examField = createStyledTextField(fieldFont);
        formPanel.add(examField);

        formPanel.add(createStyledLabel("Result:", labelFont));
        resultField = createStyledTextField(fieldFont);
        formPanel.add(resultField);

        formPanel.add(createStyledLabel("Faculty:", labelFont));
        facultyField = createStyledTextField(fieldFont);
        facultyField.setText("Santosh Shushil");
        formPanel.add(facultyField);

        formPanel.add(createStyledLabel("Tuition Fee (BDT):", labelFont));
        feeField = createStyledTextField(fieldFont);
        formPanel.add(feeField);

        inputPanel.add(formPanel, BorderLayout.CENTER);

        // Control panel for buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(SECONDARY_COLOR);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton addButton = createStyledButton("Add Student");
        JButton updateButton = createStyledButton("Update Student");
        JButton removeButton = createStyledButton("Remove Student");
        JButton clearButton = createStyledButton("Clear Fields");
        JButton sortButton = createStyledButton("Sort by Name");

        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        removeButton.addActionListener(e -> removeStudent());
        clearButton.addActionListener(e -> clearFields());
        sortButton.addActionListener(e -> sortTableByName());

        controlPanel.add(addButton);
        controlPanel.add(updateButton);
        controlPanel.add(removeButton);
        controlPanel.add(clearButton);
        controlPanel.add(sortButton);

        inputPanel.add(controlPanel, BorderLayout.SOUTH);

        return inputPanel;
    }

    private JPanel createTablePanel() {
        String[] columns = {"Name", "Roll", "ID No", "Email", "Department", "CGPA",
                           "Course", "Exam", "Result", "Faculty", "Tuition Fee"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : SECONDARY_COLOR);
                return c;
            }
        };
        studentTable.setFont(new Font("Roboto", Font.PLAIN, 13));
        studentTable.setRowHeight(30);
        studentTable.setGridColor(new Color(220, 220, 220));
        studentTable.getTableHeader().setBackground(PRIMARY_COLOR);
        studentTable.getTableHeader().setForeground(SECONDARY_COLOR);
        studentTable.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));

        // Set column widths
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        studentTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(8).setPreferredWidth(80);
        studentTable.getColumnModel().getColumn(9).setPreferredWidth(150);
        studentTable.getColumnModel().getColumn(10).setPreferredWidth(100);

        sorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(sorter);

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = studentTable.convertRowIndexToModel(selectedRow);
                nameField.setText((String) tableModel.getValueAt(modelRow, 0));
                rollField.setText((String) tableModel.getValueAt(modelRow, 1));
                idField.setText((String) tableModel.getValueAt(modelRow, 2));
                emailField.setText((String) tableModel.getValueAt(modelRow, 3));
                deptField.setText((String) tableModel.getValueAt(modelRow, 4));
                cgpaField.setText((String) tableModel.getValueAt(modelRow, 5));
                courseField.setText((String) tableModel.getValueAt(modelRow, 6));
                examField.setText((String) tableModel.getValueAt(modelRow, 7));
                resultField.setText((String) tableModel.getValueAt(modelRow, 8));
                facultyField.setText((String) tableModel.getValueAt(modelRow, 9));
                feeField.setText((String) tableModel.getValueAt(modelRow, 10));
                setStatusMessage("Student selected. Edit details and click 'Update' or 'Remove'.");
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(studentTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            "Student List",
            0, 0, new Font("Roboto", Font.BOLD, 16), PRIMARY_COLOR
        ));

        return new JPanel(new BorderLayout()) {{
            setBackground(SECONDARY_COLOR);
            add(tableScrollPane, BorderLayout.CENTER);
        }};
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JTextField createStyledTextField(Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setBackground(SECONDARY_COLOR);
        field.setForeground(TEXT_COLOR);
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(SECONDARY_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        return button;
    }

    private JLabel createLinkLabel(String text, String url) {
        JLabel linkLabel = new JLabel(text);
        linkLabel.setFont(new Font("Roboto", Font.PLAIN, 13));
        linkLabel.setForeground(ACCENT_COLOR);
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                linkLabel.setForeground(HOVER_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                linkLabel.setForeground(ACCENT_COLOR);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                    setStatusMessage("Opened " + text + " link in browser.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(StudentManagementSystem.this,
                        "Could not open " + text + " link.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return linkLabel;
    }

    private void setStatusMessage(String message) {
        statusLabel.setText(message);
        statusTimer.cancel();
        statusTimer = new Timer();
        statusTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> statusLabel.setText("Ready to manage student records"));
            }
        }, 4000);
    }

    private void sortTableByName() {
        sorter.setSortKeys(java.util.Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        setStatusMessage("Table sorted by student name.");
    }

    private class Student {
        String name, roll, id, email, dept, cgpa, course, exam, result, faculty, tuitionFee;

        Student(String name, String roll, String id, String email, String dept, String cgpa,
               String course, String exam, String result, String faculty, String tuitionFee) {
            this.name = name;
            this.roll = roll;
            this.id = id;
            this.email = email;
            this.dept = dept;
            this.cgpa = cgpa;
            this.course = course;
            this.exam = exam;
            this.result = result;
            this.faculty = faculty;
            this.tuitionFee = tuitionFee;
        }
    }

    private void addStudent() {
        try {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String id = idField.getText().trim();
            String email = emailField.getText().trim();
            String dept = deptField.getText().trim();
            String cgpa = cgpaField.getText().trim();
            String course = courseField.getText().trim();
            String exam = examField.getText().trim();
            String result = resultField.getText().trim();
            String faculty = facultyField.getText().trim();
            String tuitionFee = feeField.getText().trim();

            // Input validation
            if (name.isEmpty() || roll.isEmpty() || id.isEmpty() || email.isEmpty() || 
                dept.isEmpty() || cgpa.isEmpty() || course.isEmpty() || exam.isEmpty() ||
                result.isEmpty() || faculty.isEmpty() || tuitionFee.isEmpty()) {
                setStatusMessage("Error: All fields must be filled!");
                return;
            }

            double cgpaValue = Double.parseDouble(cgpa);
            if (cgpaValue < 0 || cgpaValue > 4.0) {
                setStatusMessage("Error: CGPA must be between 0.0 and 4.0!");
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                setStatusMessage("Error: Invalid email format!");
                return;
            }

            double feeValue = Double.parseDouble(tuitionFee);
            if (feeValue < 0) {
                setStatusMessage("Error: Tuition fee must be a positive number!");
                return;
            }

            // Check for duplicate roll or ID
            for (Student s : students) {
                if (s.roll.equals(roll) || s.id.equals(id)) {
                    setStatusMessage("Error: Roll or ID already exists!");
                    return;
                }
            }

            // Create and add student
            Student student = new Student(name, roll, id, email, dept, cgpa, course, exam, 
                                        result, faculty, tuitionFee);
            students.add(student);
            tableModel.addRow(new String[]{name, roll, id, email, dept, cgpa, course, 
                                         exam, result, faculty, tuitionFee});
            
            clearFields();
            setStatusMessage("Student added successfully! Total students: " + students.size());

        } catch (NumberFormatException e) {
            setStatusMessage("Error: Invalid format for CGPA or Tuition Fee!");
        } catch (Exception e) {
            setStatusMessage("Error: An unexpected issue occurred!");
        }
    }

    private void updateStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            setStatusMessage("Error: Please select a student to update!");
            return;
        }

        try {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String id = idField.getText().trim();
            String email = emailField.getText().trim();
            String dept = deptField.getText().trim();
            String cgpa = cgpaField.getText().trim();
            String course = courseField.getText().trim();
            String exam = examField.getText().trim();
            String result = resultField.getText().trim();
            String faculty = facultyField.getText().trim();
            String tuitionFee = feeField.getText().trim();

            if (name.isEmpty() || roll.isEmpty() || id.isEmpty() || email.isEmpty() || 
                dept.isEmpty() || cgpa.isEmpty() || course.isEmpty() || exam.isEmpty() ||
                result.isEmpty() || faculty.isEmpty() || tuitionFee.isEmpty()) {
                setStatusMessage("Error: All fields must be filled!");
                return;
            }

            double cgpaValue = Double.parseDouble(cgpa);
            if (cgpaValue < 0 || cgpaValue > 4.0) {
                setStatusMessage("Error: CGPA must be between 0.0 and 4.0!");
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                setStatusMessage("Error: Invalid email format!");
                return;
            }

            double feeValue = Double.parseDouble(tuitionFee);
            if (feeValue < 0) {
                setStatusMessage("Error: Tuition fee must be a positive number!");
                return;
            }

            int modelRow = studentTable.convertRowIndexToModel(selectedRow);
            
            // Check for duplicate roll or ID
            for (int i = 0; i < students.size(); i++) {
                if (i != modelRow && (students.get(i).roll.equals(roll) || students.get(i).id.equals(id))) {
                    setStatusMessage("Error: Roll or ID already exists!");
                    return;
                }
            }

            Student student = students.get(modelRow);
            student.name = name;
            student.roll = roll;
            student.id = id;
            student.email = email;
            student.dept = dept;
            student.cgpa = cgpa;
            student.course = course;
            student.exam = exam;
            student.result = result;
            student.faculty = faculty;
            student.tuitionFee = tuitionFee;

            tableModel.setValueAt(name, modelRow, 0);
            tableModel.setValueAt(roll, modelRow, 1);
            tableModel.setValueAt(id, modelRow, 2);
            tableModel.setValueAt(email, modelRow, 3);
            tableModel.setValueAt(dept, modelRow, 4);
            tableModel.setValueAt(cgpa, modelRow, 5);
            tableModel.setValueAt(course, modelRow, 6);
            tableModel.setValueAt(exam, modelRow, 7);
            tableModel.setValueAt(result, modelRow, 8);
            tableModel.setValueAt(faculty, modelRow, 9);
            tableModel.setValueAt(tuitionFee, modelRow, 10);

            clearFields();
            setStatusMessage("Student updated successfully!");

        } catch (NumberFormatException e) {
            setStatusMessage("Error: Invalid format for CGPA or Tuition Fee!");
        } catch (Exception e) {
            setStatusMessage("Error: An unexpected issue occurred!");
        }
    }

    private void removeStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            setStatusMessage("Error: Please select a student to remove!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to remove this student?",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int modelRow = studentTable.convertRowIndexToModel(selectedRow);
            students.remove(modelRow);
            tableModel.removeRow(modelRow);
            clearFields();
            setStatusMessage("Student removed successfully! Total students: " + students.size());
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        idField.setText("");
        emailField.setText("");
        deptField.setText("");
        cgpaField.setText("");
        courseField.setText("");
        examField.setText("");
        resultField.setText("");
        facultyField.setText("Santosh Shushil");
        feeField.setText("");
        studentTable.clearSelection();
        setStatusMessage("All fields cleared. Ready to add or select a student.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem());
    }
}