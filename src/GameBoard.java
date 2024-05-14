import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class GameBoard extends JPanel {
    // instance variables
    private Image boardImage;
    int startX = 83; // x-coordinate for the first hole
    int startY = 134; // y-coordinate for the first hole
    int holeSpacingX = 37; // space between holes
    int holeSpacingY = 45; // space between rows
    private Algorithims game;
    private static GameElement[][] feedbackPegs;

    /**
     * Constructor for the GameBoard class
     * Initializes the game and sets the layout to null, builds the board, and sets up the feedback pegs
     * @param game the game to be played
     * @return void
     * @author Rishit
     */
    public GameBoard(Algorithims game) {
        this.game = game;
        setLayout(null); // absolute positioning
        loadBoardImage();
        buildBoard();
        setupFeedbackPegs();
    }

    /**
     * Load the board image from the images folder and sets the preferred size of the board
     * @param void
     * @return void
     * @author Rishit
     * @throws IOException
     */
    private void loadBoardImage() {
        try {
            boardImage = ImageIO.read(new File("images/mastermindboard.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(boardImage.getWidth(null), boardImage.getHeight(null)));
    }

    /**
     * Paints the board image on the panel, overriding the paintComponent method
     * @param g the graphics object to paint the board image
     * @return void
     * @author Rishit
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, this);
    }

    // helper methods to calculate positions
    /**
     * Calculate the x-coordinate for the peg in the given column
     * @param col
     * @return int the x-coordinate for the peg in the given column
     * @author Rishit
     */
    private int calculateXPositionForPeg(int col) {
        return startX + col * holeSpacingX;
    }

    /**
     * Calculate the y-coordinate for the row
     * @param row
     * @return int the y-coordinate for the row
     * @author Rishit
     */
    private int calculateYPositionForRow(int row) {
        return startY + row * holeSpacingY;
    }

    /**
     * Rebuild the board by removing all components, rebuilding the board, setting up the feedback pegs, and repainting the board
     * To be used when resetting the game
     * @param void
     * @return void
     * @author Rishit
     */
    public void rebuildBoard() {
        removeAll();
        buildBoard();
        setupFeedbackPegs();
        revalidate();
        repaint();
        MainFrame.submitButton.setEnabled(true);
    }

    /**
     * Build the board by creating holes for each row and column
     * Holes are added to the panel and positioned accordingly
     * @param void
     * @return void
     * @author Rishit
     */
    private void buildBoard() {
        for (int row = 10; row > 0; row--) {
            for (int col = 0; col < 4; col++) {
                Hole hole = new Hole(row, col, game);
                hole.setBounds(calculateXPositionForPeg(col), calculateYPositionForRow(10-row), 25, 25);
                this.add(hole);
            }
        }
    }

    /**
     * Update the active row by enabling the current row and disabling others
     * Loops through all components and sets the active row based on the current row in the game
     * Used to lock the user's input to the current row
     * @param void
     * @return void
     * @author Rishit
     */
    public void updateActiveRow() {
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof Hole) {
                Hole hole = (Hole) component;
                // enable the current row and disable others
                hole.setActive(hole.getRow() == game.currentRow);
            }
        }
    }
    
    /**
     * Set up the feedback pegs by creating a 2D array of feedback pegs
     * Feedback pegs are added to the panel by calculating the x and y positions for sets of 4 per row
     * @param void
     * @return void
     * @author Rishit
     */
    private void setupFeedbackPegs() {
        feedbackPegs = new FeedbackPeg[Algorithims.rows][4]; // 4 feedback pegs per row
        for (int row = 0; row < Algorithims.rows; row++) {
            for (int i = 0; i < 4; i++) {
                FeedbackPeg peg = new FeedbackPeg();
                int baseX = calculateXPositionForPeg(4) - 191;  // adjust place feedback pegs next to the guess holes
                int baseY = calculateYPositionForRow(10 - row) - 49;
                int x = baseX + (i % 2) * 19;  // arrange in 2x2 grid
                int y = baseY + (i / 2) * 19;
                peg.setBounds(x, y, 13, 13);
                feedbackPegs[row][i] = peg;
                this.add(peg);
            }
        }
    }

    /**
     * Update the feedback pegs for the given row with the feedback array
     * Feedback array contains counts of black and white pegs
     * Loops through the feedback pegs for the given row and sets the color based on the feedback array
     * @param row the row to update the feedback pegs for
     * @param feedback the feedback array containing counts of black and white pegs
     * @return void
     * @author Rishit
     */
    public static void updateFeedbackPegs(int row, int[] feedback) {
        // feedback array contains counts of black and white pegs
        int blacks = feedback[0];
        int whites = feedback[1];
        for (int i = 0; i < 4; i++) {
            if (i < blacks) {
                feedbackPegs[row-1][i].setColor(Color.BLACK);
            } else if (i < blacks + whites) {
                feedbackPegs[row-1][i].setColor(Color.WHITE);
            } else {
                feedbackPegs[row-1][i].setColor(new Color(0, 0, 0, 0));  // reset to transparent if needed
            }
        }
    }

}