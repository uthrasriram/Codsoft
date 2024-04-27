import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApp extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup buttonGroup;
    private JButton submitButton;
    private JLabel timerLabel;

    private String[][] questions = {
            {"What is the capital of France?", "Paris", "Rome", "Berlin", "Madrid", "Paris"},
            {"What is the largest planet in the solar system?", "Jupiter", "Saturn", "Earth", "Venus", "Jupiter"},
            {"Who wrote 'To Kill a Mockingbird'?", "Harper Lee", "J.K. Rowling", "Stephen King", "Ernest Hemingway", "Harper Lee"}
    };

    private int currentQuestionIndex = 0;
    private int score = 0;
    private Timer timer;
    private int timeRemaining = 10;

    public QuizApp() {
        setTitle("Quiz Application");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        questionLabel = new JLabel();
        add(questionLabel);

        options = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            buttonGroup.add(options[i]);
            add(options[i]);
        }

        timerLabel = new JLabel("Time Left: " + timeRemaining + " seconds");
        add(timerLabel);

        submitButton = new JButton("Submit");
        add(submitButton);

        loadQuestion();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time Left: " + timeRemaining + " seconds");
                if (timeRemaining <= 0) {
                    timer.stop();
                    checkAnswer();
                }
            }
        });
        timer.start();
    }

    private void loadQuestion() {
        questionLabel.setText(questions[currentQuestionIndex][0]);
        for (int i = 0; i < 4; i++) {
            options[i].setText(questions[currentQuestionIndex][i + 1]);
            options[i].setSelected(false);
        }
    }

    private void checkAnswer() {
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected() && options[i].getText().equals(questions[currentQuestionIndex][5])) {
                score++;
            }
            options[i].setSelected(false);
        }
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            loadQuestion();
            timeRemaining = 10;
            timerLabel.setText("Time Left: " + timeRemaining + " seconds");
            timer.restart();
        } else {
            showResult();
        }
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, "Quiz ended!\nYour final score is: " + score + "/" + questions.length);
        StringBuilder summary = new StringBuilder("Summary of Correct/Incorrect Answers:\n");
        for (int i = 0; i < questions.length; i++) {
            summary.append("Q").append(i + 1).append(": ").append(questions[i][0]).append("\n");
            summary.append("Correct Answer: ").append(questions[i][5]).append("\n");
            for (int j = 0; j < 4; j++) {
                if (options[j].getText().equals(questions[i][5])) {
                    options[j].setForeground(Color.GREEN);
                } else if (options[j].isSelected() && !options[j].getText().equals(questions[i][5])) {
                    options[j].setForeground(Color.RED);
                }
            }
            summary.append("Your Answer: ");
            boolean answered = false;
            for (int j = 0; j < 4; j++) {
                if (options[j].isSelected()) {
                    summary.append(options[j].getText()).append("\n");
                    answered = true;
                }
            }
            if (!answered) {
                summary.append("No answer submitted\n");
            }
        }
        JOptionPane.showMessageDialog(this, summary.toString());
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizApp().setVisible(true);
            }
        });
    }
}
