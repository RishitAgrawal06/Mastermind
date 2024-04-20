import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class GameBoard extends JPanel {
    private Image boardImage;
    int startX = 83; // X-coordinate for the first hole
    int startY = 134; // Y-coordinate for the first hole
    int holeSpacingX = 37; // Space between holes
    int holeSpacingY = 45; // Space between rows

    public GameBoard() {
        setLayout(null); // Absolute positioning
        loadBoardImage();
        
        // assuming 10 rows and 4 holes per row, and 4 feedback pegs per row
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 4; col++) {
                Hole hole = new Hole();
                hole.setBounds(calculateXPositionForPeg(col), calculateYPositionForRow(row), 25, 25);
                this.add(hole);
            }
            // TODO: Add feedback pegs
        }
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
}
