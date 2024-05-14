import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class FeedbackPeg extends JPanel implements GameElement {
    /**
     * Constructor for the FeedbackPeg class
     * Sets the size of the feedback pegs to 13x13 pixels
     * Sets the pegs to be initially transparent
     * @param none
     * @return void
     * @author Rishit
     */
    public FeedbackPeg() {
        setOpaque(true);
        clear();  // initially transparent
        setPreferredSize(new Dimension(13, 13));  // smaller size for feedback pegs
    }

    /**
     * Sets the color of the feedback peg
     * @param color the color to set the feedback peg to
     * @return void
     * @author Rishit
     */
    @Override
    public void setColor(Color color) {
        setBackground(color);
    }

    /**
     * Clears the color of the feedback peg
     * @param void
     * @return void
     * @author Rishit
     */
    @Override
    public void clear() {
        setBackground(new Color(0, 0, 0, 0));  // reset color to transparent
    }
}
