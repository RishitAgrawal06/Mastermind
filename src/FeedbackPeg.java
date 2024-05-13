import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class FeedbackPeg extends JPanel {
    public FeedbackPeg() {
        setOpaque(true);
        setBackground(new Color(0, 0, 0, 10));  // initially transparent
        setPreferredSize(new Dimension(13, 13));  // smaller size for feedback pegs
    }

    public void setColor(Color color) {
        setBackground(color);
    }
}
