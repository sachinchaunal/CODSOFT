import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class StudentManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String USER = "root"; // Change to your MySQL username
    private static final String PASSWORD = "nihcas"; // Change to your MySQL password

    private Connection connection;

    public StudentManagementSystem() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Student student) {
        String query = "INSERT INTO students (name, roll_number, grade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getRollNumber());
            stmt.setString(3, student.getGrade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeStudent(int rollNumber) {
        String query = "DELETE FROM students WHERE roll_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rollNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student searchStudent(int rollNumber) {
        String query = "SELECT * FROM students WHERE roll_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rollNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Student(rs.getString("name"), rs.getInt("roll_number"), rs.getString("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                students.add(new Student(rs.getString("name"), rs.getInt("roll_number"), rs.getString("grade")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", rollNumber=" + rollNumber +
                ", grade='" + grade + '\'' +
                '}';
    }
}

class StudentManagementApp {
    private static StudentManagementSystem system = new StudentManagementSystem();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Management System");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 10, 80, 25);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(100, 10, 160, 25);
        frame.add(nameField);

        JLabel rollNumberLabel = new JLabel("Roll Number:");
        rollNumberLabel.setBounds(10, 40, 80, 25);
        frame.add(rollNumberLabel);

        JTextField rollNumberField = new JTextField();
        rollNumberField.setBounds(100, 40, 160, 25);
        frame.add(rollNumberField);

        JLabel gradeLabel = new JLabel("Grade:");
        gradeLabel.setBounds(10, 70, 80, 25);
        frame.add(gradeLabel);

        JTextField gradeField = new JTextField();
        gradeField.setBounds(100, 70, 160, 25);
        frame.add(gradeField);

        JButton addButton = new JButton("Add Student");
        addButton.setBounds(10, 100, 150, 25);
        frame.add(addButton);

        JButton removeButton = new JButton("Remove Student");
        removeButton.setBounds(170, 100, 150, 25);
        frame.add(removeButton);

        JButton searchButton = new JButton("Search Student");
        searchButton.setBounds(10, 130, 150, 25);
        frame.add(searchButton);

        JButton displayButton = new JButton("Display All Students");
        displayButton.setBounds(170, 130, 150, 25);
        frame.add(displayButton);

        JTextArea outputArea = new JTextArea();
        outputArea.setBounds(10, 160, 360, 200);
        frame.add(outputArea);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int rollNumber = Integer.parseInt(rollNumberField.getText());
                String grade = gradeField.getText();
                Student student = new Student(name, rollNumber, grade);
                system.addStudent(student);
                outputArea.setText("Student added: " + student);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rollNumber = Integer.parseInt(rollNumberField.getText());
                system.removeStudent(rollNumber);
                outputArea.setText("Student with roll number " + rollNumber + " removed.");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rollNumber = Integer.parseInt(rollNumberField.getText());
                Student student = system.searchStudent(rollNumber);
                if (student != null) {
                    outputArea.setText("Student found: " + student);
                } else {
                    outputArea.setText("Student with roll number " + rollNumber + " not found.");
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("All students:\n");
                for (Student student : system.getAllStudents()) {
                    outputArea.append(student + "\n");
                }
            }
        });

        frame.setVisible(true);
    }
}
