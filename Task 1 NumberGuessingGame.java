import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends JPanel {
    private final int maxAttempts = 10;
    private int numberToGuess;
    private int attempts;
    private int score;
    private JTextField guessInputField;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public NumberGuessingGame() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0x62355F));
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0x62355F));
        JLabel headerLabel = new JLabel("Number Guessing Game");
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePanel.setBackground(new Color(0x62355F));
        gamePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(gamePanel, BorderLayout.CENTER);
        JLabel instructionLabel = new JLabel("Guess a number between 1 and 100", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.add(instructionLabel);
        gamePanel.add(Box.createVerticalStrut(20));
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(new Color(0x62355F));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel inputLabel = new JLabel("Enter your guess:");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        inputLabel.setForeground(Color.WHITE);
        guessInputField = new JTextField(10);
        guessInputField.setFont(new Font("Arial", Font.PLAIN, 16));
        guessInputField.setPreferredSize(new Dimension(200, 30));
        guessInputField.setBackground(Color.WHITE);
        guessInputField.setForeground(new Color(0x62355F));
        inputPanel.add(inputLabel);
        inputPanel.add(guessInputField);
        gamePanel.add(inputPanel);
        gamePanel.add(Box.createVerticalStrut(20));
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1, 2, 20, 10));
        infoPanel.setBackground(new Color(0x62355F));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        attemptsLabel = new JLabel("Attempts remaining: " + maxAttempts, SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        attemptsLabel.setForeground(Color.WHITE);
        scoreLabel = new JLabel("Your Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setForeground(Color.WHITE);
        infoPanel.add(attemptsLabel);
        infoPanel.add(scoreLabel);
        gamePanel.add(infoPanel);
        gamePanel.add(Box.createVerticalStrut(20));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x62355F));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton submitButton = new JButton("Submit Guess");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(new Color(0, 153, 76)); // Green
        submitButton.setForeground(Color.WHITE); // White text
        submitButton.setFocusPainted(false);
        submitButton.setPreferredSize(new Dimension(180, 40));

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                submitButton.setBackground(new Color(0, 180, 90));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                submitButton.setBackground(new Color(0, 153, 76));
            }
        });

        submitButton.addActionListener(new SubmitGuessListener());
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
        startNewRound();
    }

    private void startNewRound() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1;
        attempts = 0;
        guessInputField.setText("");
        attemptsLabel.setText("Attempts remaining: " + maxAttempts);
    }

    private void handleGuess(int guess) {
        attempts++;
        if (guess == numberToGuess) {
            score++;
            JOptionPane.showMessageDialog(
                    this,
                    "<html><h3 style='color:green;'>Correct! The number was " + numberToGuess + ".</h3></html>",
                    "Congratulations!",
                    JOptionPane.INFORMATION_MESSAGE);
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to play again?",
                    "Play Again?",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                startNewRound();
            } else {
                System.exit(0);
            }
        } else if (guess < numberToGuess) {
            JOptionPane.showMessageDialog(
                    this,
                    "<html><h3 style='color:orange;'>Too low! Try again.</h3></html>",
                    "Hint",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "<html><h3 style='color:orange;'>Too high! Try again.</h3></html>",
                    "Hint",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (attempts >= maxAttempts) {
            JOptionPane.showMessageDialog(
                    this,
                    "<html><h3 style='color:red;'>Out of attempts! The number was " + numberToGuess + ".</h3></html>",
                    "Game Over",
                    JOptionPane.ERROR_MESSAGE);
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to play again?",
                    "Play Again?",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                startNewRound();
            } else {
                System.exit(0);
            }
        }
        attemptsLabel.setText("Attempts remaining: " + (maxAttempts - attempts));
        scoreLabel.setText("Your Score: " + score);
    }

    private class SubmitGuessListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessInputField.getText());
                if (guess < 1 || guess > 100) {
                    JOptionPane.showMessageDialog(
                            NumberGuessingGame.this,
                            "<html><h3 style='color:red;'>Please enter a number between 1 and 100.</h3></html>",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    handleGuess(guess);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        NumberGuessingGame.this,
                        "<html><h3 style='color:red;'>Invalid input. Please enter a valid number.</h3></html>",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            guessInputField.setText("");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.add(new NumberGuessingGame());
        frame.setVisible(true);
    }
}
