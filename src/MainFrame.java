import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private GameBoard gameBoard;
    private JPanel pegPalette;
    private Algorithims game;
    public static JButton submitButton;

    public MainFrame() {
        game = new Algorithims();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        gameBoard = new GameBoard(game);
        add(gameBoard, BorderLayout.CENTER);
        pegPalette = new JPanel(new FlowLayout(FlowLayout.CENTER));
        initializePegPalette();
        setupControlPanel();
        // TODO: Add feedback pegs to pegPalette
        setupSubmitButton();
        add(pegPalette, BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(null);
    }

    private void setupSubmitButton() {
        submitButton = new JButton("Submit Guess");
        submitButton.addActionListener(e -> {
            if (game.currentRow <= Algorithims.rows) {
                int[] hints = game.giveHints(game.currentRow);
                int[] feedback = updateFeedback(hints);
                GameBoard.updateFeedbackPegs(game.currentRow, feedback);
                repaint();
                // if the currentrow is 10, disable the submit button
                if (game.currentRow == Algorithims.rows) {
                    submitButton.setEnabled(false);
                    return;
                }
                game.advanceRow();
                gameBoard.updateActiveRow();
            }
        });
        pegPalette.add(submitButton);
    }

    private void setupControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton resetButton = new JButton("Reset Game");
        JButton exitButton = new JButton("Exit Game");
    
        resetButton.addActionListener(e -> {
            game.resetGame();
            gameBoard.rebuildBoard();
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });
    
        controlPanel.add(resetButton);
        controlPanel.add(exitButton);
        add(controlPanel, BorderLayout.WEST);
    }

    private int[] updateFeedback(int[] hints) {
        // placeholder for feedback update logic
        System.out.println("Feedback for row " + game.currentRow + ": " + Arrays.toString(hints));
        // print the actual key
        System.out.println("Key: " + Arrays.toString(game.getKey()));
        int[] feedback = new int[2];
        for (int i = 0; i < hints.length; i++) {
            if (hints[i] == 1) {
                feedback[0]++;
            } else if (hints[i] == 2) {
                feedback[1]++;
            }
        }
        return feedback;
    }

    private void initializePegPalette() {
        Color[] colors = new Color[]{Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.WHITE, Color.BLACK};
        for (Color color : colors) {
            Peg peg = new Peg(color);
            pegPalette.add(peg);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
