import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private GameBoard gameBoard;
    private JPanel pegPalette;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(800, 600);
        gameBoard = new GameBoard();
        add(gameBoard, BorderLayout.CENTER);

        pegPalette = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // TODO: Add feedback pegs to pegPalette
        initializePegPalette();
        add(pegPalette, BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
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
