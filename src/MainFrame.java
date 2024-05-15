import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MainFrame extends JFrame {
    // instance variables
    private GameBoard gameBoard;
    private JPanel pegPalette;
    private Algorithims game;
    public static JButton submitButton;
    private JTextArea sortedGuessesArea;

    /**
     * Constructor for the MainFrame class
     * Initializes the game, sets the layout to BorderLayout, adds the game board and peg palette
     * Sets up the control panel (containing reset, exit, and guess sorting) and submit button
     * Adds the sorted guesses area to the left of the frame
     * @param void
     * @return void
     * @author Rishit
     */
    public MainFrame() {
        game = new Algorithims();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        gameBoard = new GameBoard(game);
        add(gameBoard, BorderLayout.CENTER);
        pegPalette = new JPanel(new FlowLayout(FlowLayout.CENTER));
        initializePegPalette();
        setupControlPanel();
        setupSubmitButton();
        add(pegPalette, BorderLayout.PAGE_END);
        sortedGuessesArea = new JTextArea(20, 25); // JTextArea for displaying sorted guesses
        // set the instructions for the game
        setInstructions();
        add(new JScrollPane(sortedGuessesArea), BorderLayout.WEST);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Set up the submit button for the game
     * Adds an action listener to the submit button to check the guess and update the feedback
     * If all feedback is black, game is over and submit button is disabled
     * If the current row is 10, the submit button is disabled, and a message dialog is displayed
     * Disables the submit button if the game is over
     * @param void
     * @return void
     * @author Rishit
     */
    private void setupSubmitButton() {
        submitButton = new JButton("Submit Guess");
        submitButton.addActionListener(e -> {
            // ensure the current row is less than or equal to the number of rows
            if (game.currentRow <= Algorithims.rows) {
                int[] hints = game.giveHints(game.currentRow);
                int[] feedback = updateFeedback(hints);
                GameBoard.updateFeedbackPegs(game.currentRow, feedback);
                // check for if all feedback is black pegs, if so, game is won
                if (isGameOver(feedback)) {
                    JOptionPane.showMessageDialog(this, "Congrats!:D You win! Game over!");
                    submitButton.setEnabled(false);
                    return;
                }
                repaint();
                // if the currentrow is 10, disable the submit button
                if (game.currentRow == Algorithims.rows) {
                    submitButton.setEnabled(false);
                    JOptionPane.showMessageDialog(this, "You lose! " + "The correct key is: \n" + Arrays.toString(game.getKey()) + "\n" +
                            "1 = Green\n" +
                            "2 = Red\n" +
                            "3 = Blue\n" +
                            "4 = Yellow\n" +
                            "5 = White\n" +
                            "6 = Black" + 
                            "\n\nClick Reset Game to play again!");
                    return;
                }
                game.advanceRow();
                gameBoard.updateActiveRow();
            }
        });
        pegPalette.add(submitButton);
    }

    /**
     * Set up the control panel for the game
     * Adds a reset button to reset the game, an exit button to exit the game, and a sort button to sort the guesses
     * Adds action listeners to the buttons to reset the game, exit the game, and sort the guesses
     * @param void
     * @return void
     * @author Rishit
     */
    private void setupControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton resetButton = new JButton("Reset Game");
        JButton exitButton = new JButton("Exit Game");
        JButton sortButton = new JButton("Sort Guesses by Feedback");
    
        resetButton.addActionListener(e -> {
            game.resetGame();
            gameBoard.rebuildBoard();
            setInstructions();
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        sortButton.addActionListener(e -> {
            List<String> sortedGuesses = game.sortGuessesByFeedback();
            sortedGuessesArea.setText(""); // clear the text area
            for (String guess : sortedGuesses) {
                sortedGuessesArea.append(guess + "\n");
            }
        });
    
        // add the buttons to the control panel
        controlPanel.add(resetButton);
        controlPanel.add(exitButton);
        controlPanel.add(sortButton);
        add(controlPanel, BorderLayout.BEFORE_FIRST_LINE);
    }

    /**
     * Updates the feedback based on the hints given by the game for the current row
     * Prints the feedback for the current row and the actual key in the terminal for debugging
     * @param hints the hints given by the game for the current row
     * @return int[] the feedback array containing the counts of black and white pegs
     * @author Ethan
     */
    private int[] updateFeedback(int[] hints) {
        // placeholder for feedback update logic
        System.out.println("Feedback for row " + game.currentRow + ": " + Arrays.toString(hints));
        // print the actual key
        System.out.println("Key: " + Arrays.toString(game.getKey()));
        int[] feedback = new int[2];
        for (int i = 0; i < hints.length; i++) {
            if (hints[i] == 2) {
                feedback[0]++;
            } else if (hints[i] == 1) {
                feedback[1]++;
            }
        }
        return feedback;
    }

    /**
     * Checks if the game is over based on the feedback for all 4 pegs being black
     * @param feedback the feedback array containing the counts of black and white pegs
     * @return boolean true if the game is over, false otherwise
     * @author Roshni
     */
    private boolean isGameOver(int[] feedback) {
        return feedback[0] == 4;
    }

    /**
     * Initializes the peg palette with 6 colored pegs
     * Adds the pegs to the peg palette panel
     * @param void
     * @return void
     * author Siddh
     */
    private void initializePegPalette() {
        Color[] colors = new Color[]{Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.WHITE, Color.BLACK};
        for (Color color : colors) {
            Peg peg = new Peg(color);
            pegPalette.add(peg);
        }
    }

    /**
     * Sets the instructions for the game in the sorted guesses area
     * @param void
     * @return void
     * @author Shaunak
     */
    public void setInstructions(){
        sortedGuessesArea.setLineWrap(true);
        sortedGuessesArea.setWrapStyleWord(true);
        sortedGuessesArea.setText("Welcome to Mastermind!\n\n" +
                "The objective of the game is to guess the secret code\n" +
                "------------------------------------------\n" +
                "Instructions:\n\n" +
                "1. The code consists of 4 pegs, each peg can be one of 6 colors\n" +
                "2. You have 10 attempts to guess the code\n" +
                "3. After each guess, feedback pegs will be displayed\n" +
                "4. A black peg means a correct color in the correct position\n" +
                "5. A white peg means a correct color in the wrong position\n" +
                "6. Drag/Drop from the peg palette to make your guess\n" +
                "7. Click Submit Guess to check your guess\n" +
                "8. Click Reset Game to start a new game\n" +
                "9. Click Exit Game to quit the game\n" +
                "10. Click Sort Guesses to sort the guesses based on feedback\n\n" + 
                "Good Luck!");
    }

    /**
     * Main method to run the game
     * Creates a new MainFrame and sets it to be visible
     * @param args the command line arguments
     * @return void
     * @author Rishit
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }    
}
