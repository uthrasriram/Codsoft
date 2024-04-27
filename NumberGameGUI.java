import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGameGUI extends JFrame {
    private JLabel titleLabel;
    private JLabel guessLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JButton playAgainButton;

    private int targetNumber;
    private int attemptsLeft;
    private int score;

    public NumberGameGUI() {
        setTitle("Number Guessing Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        titleLabel = new JLabel("Welcome to the Number Guessing Game!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        guessLabel = new JLabel("Enter your guess (1 - 100):");
        guessLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(guessLabel);

        guessField = new JTextField();
        guessField.setHorizontalAlignment(SwingConstants.CENTER);
        add(guessField);

        feedbackLabel = new JLabel();
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(feedbackLabel);

        attemptsLabel = new JLabel();
        attemptsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(attemptsLabel);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
        add(guessButton);

        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgain();
            }
        });
        add(playAgainButton);

        initializeGame();
    }

    private void initializeGame() {
        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;
        attemptsLeft = 5;
        score = 0;
        updateAttemptsLabel();
        feedbackLabel.setText("");
        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
        playAgainButton.setVisible(false);
    }

    private void checkGuess() {
        int guess;
        try {
            guess = Integer.parseInt(guessField.getText());
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid input. Please enter a number.");
            return;
        }

        if (guess < 1 || guess > 100) {
            feedbackLabel.setText("Please enter a number between 1 and 100.");
            return;
        }

        attemptsLeft--;
        updateAttemptsLabel();

        if (guess == targetNumber) {
            feedbackLabel.setText("Congratulations! You guessed the correct number!");
            score++;
            guessField.setEditable(false);
            guessButton.setEnabled(false);
            playAgainButton.setVisible(true);
        } else if (guess < targetNumber) {
            feedbackLabel.setText("Too low! Try again.");
        } else {
            feedbackLabel.setText("Too high! Try again.");
        }

        if (attemptsLeft == 0 && guess != targetNumber) {
            feedbackLabel.setText("Sorry, you've run out of attempts. The correct number was " + targetNumber + ".");
            guessField.setEditable(false);
            guessButton.setEnabled(false);
            playAgainButton.setVisible(true);
        }
    }

    private void updateAttemptsLabel() {
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
    }

    private void playAgain() {
        initializeGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGameGUI().setVisible(true);
            }
        });
    }
}
