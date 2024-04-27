import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeCalculator extends JFrame {
    private JLabel[] subjectLabels;
    private JTextField[] marksFields;
    private JButton calculateButton;
    private JLabel totalMarksLabel;
    private JLabel averagePercentageLabel;
    private JLabel gradeLabel;

    public GradeCalculator() {
        setTitle("Grade Calculator");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        subjectLabels = new JLabel[5];
        marksFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            subjectLabels[i] = new JLabel("Subject " + (i + 1) + ":");
            subjectLabels[i].setBounds(70, 30 + i * 60, 90, 30);
            marksFields[i] = new JTextField();
            marksFields[i].setBounds(150, 30 + i * 60, 70, 30);
            add(subjectLabels[i]);
            add(marksFields[i]);
        }

        totalMarksLabel = new JLabel();
        totalMarksLabel.setBounds(100, 380, 450, 60);
        add(totalMarksLabel);

        averagePercentageLabel = new JLabel();
        averagePercentageLabel.setBounds(70, 400, 280, 80);
        add(averagePercentageLabel);

        gradeLabel = new JLabel();
        gradeLabel.setBounds(90, 430, 290, 90);
        add(gradeLabel);

        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(300, 280, 150, 60);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });
        add(calculateButton);
    }

    private void calculateResults() {
        int totalMarks = 0;
        int subjectsCount = 0;

        for (int i = 0; i < 5; i++) {
            try {
                int marks = Integer.parseInt(marksFields[i].getText());
                if (marks >= 0 && marks <= 100) {
                    totalMarks += marks;
                    subjectsCount++;
                } else {
                    JOptionPane.showMessageDialog(this, "Marks should be between 0 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid marks.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        double averagePercentage = (double) totalMarks / (subjectsCount * 100) * 100;
        String grade = calculateGrade(averagePercentage);

        totalMarksLabel.setText("Total Marks: " + totalMarks);
        averagePercentageLabel.setText("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
        gradeLabel.setText("Grade: " + grade);
    }

    private String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A";
        } else if (averagePercentage >= 80) {
            return "B";
        } else if (averagePercentage >= 70) {
            return "C";
        } else if (averagePercentage >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeCalculator().setVisible(true);
            }
        });
    }
}
