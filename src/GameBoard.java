import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class GameBoard extends JPanel {
    private Image boardImage;
    int startX = 83; // x-coordinate for the first hole
    int startY = 134; // y-coordinate for the first hole
    int holeSpacingX = 37; // space between holes
    int holeSpacingY = 45; // space between rows
    private Algorithims game;
    private static FeedbackPeg[][] feedbackPegs;

    public GameBoard(Algorithims game) {
        this.game = game;
        setLayout(null); // absolute positioning
        loadBoardImage();
        buildBoard();
        setupFeedbackPegs();
        
        // assuming 10 rows and 4 holes per row, and 4 feedback pegs per row
        // for (int row = 0; row < 10; row++) {
        //     for (int col = 0; col < 4; col++) {
        //         Hole hole = new Hole(9-row, col, game);
        //         hole.setBounds(calculateXPositionForPeg(col), calculateYPositionForRow(9-row), 25, 25);
        //         this.add(hole);
        //     }
        //     // TODO: Add feedback pegs
        // }
    }

    private void loadBoardImage() {
        try {
            boardImage = ImageIO.read(new File("images/mastermindboard.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(boardImage.getWidth(null), boardImage.getHeight(null)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, this);
    }

    // helper methods to calculate positions
    private int calculateXPositionForPeg(int col) {
        return startX + col * holeSpacingX;
    }

    private int calculateYPositionForRow(int row) {
        return startY + row * holeSpacingY;
    }

    public void rebuildBoard() {
        removeAll();
        buildBoard();
        revalidate();
        repaint();
        MainFrame.submitButton.setEnabled(true);
    }

    private void buildBoard() {
        for (int row = 10; row > 0; row--) {
            for (int col = 0; col < 4; col++) {
                Hole hole = new Hole(row, col, game);
                hole.setBounds(calculateXPositionForPeg(col), calculateYPositionForRow(10-row), 25, 25);
                this.add(hole);
            }
        }
    }

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