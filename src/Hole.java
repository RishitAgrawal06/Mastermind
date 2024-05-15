import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

public class Hole extends JPanel implements GameElement, DropTargetListener {
    // instance variables
    private Color currentColor = new Color(0, 0, 0, 0);
    private int row, col;
    private Algorithims game;

    /**
     * Constructor for the Hole class - sets the row, column, and game
     * Sets the preferred size of the hole to 30x30 pixels and the background to transparent with a light background if active
     * It only enables dropping if the row is active (1)
     * @param row
     * @param col
     * @param game
     * @return void
     * @author Rishit
     */
    public Hole(int row, int col, Algorithims game){
        this.row = row;
        this.col = col;
        this.game = game;
        setPreferredSize(new Dimension(30, 30));
        setBackground(row == 1 ? new Color(0, 0, 0, 75) : new Color(0, 0, 0, 0));  // light background if active
        if (row == 1) {
            new DropTarget(this, this);
        }
    }

    /**
     * Set the active state of the hole
     * Sets the background to a light background if active and enables dropping
     * Sets the background to transparent if inactive and disables dropping
     * @param isActive the state to set the hole to
     * @return void
     * @author Rishit
     */
    public void setActive(boolean isActive) {
        if (isActive) {
            setBackground(new Color(0, 0, 0, 75)); 
            new DropTarget(this, this); // enable dropping
            this.getParent().repaint(); // repaints the entire board to remove errors related to dragging
        } else {
            setBackground(new Color(0, 0, 0, 0)); // inactive color
            setDropTarget(null); // disable dropping
            this.getParent().repaint(); // repaints the entire board to remove errors related to dragging
        }
    }

    /**
     * Set the color of the hole
     * Sets the current color to the color and repaints the hole
     * Sets the color of the hole in the game
     * @param color the color to set the hole to
     * @return void
     * @author Roshni
     */
    @Override
    public void setColor(Color color) {
        currentColor = color;
        this.repaint();
        game.setColor(row, col, colorToNum(color));
    } 

    /**
     * Clears the color of the hole
     * Sets the color to transparent and repaints the hole
     * Clears the color of the hole in the game
     * @param void
     * @return void
     * @author Roshni
     */
    @Override
    public void clear() {
        setColor(new Color(0, 0, 0, 0));  // reset color to transparent
    }

    /**
     * Converts a color to a number
     * Returns 1 for green, 2 for red, 3 for blue, 4 for yellow, 5 for white, 6 for black, and 0 for transparent
     * @param color the color to convert to a number
     * @return int the number corresponding to the color
     * @author Siddh
     */
    private int colorToNum(Color color) {
        if (color.equals(Color.GREEN)) {
            return 1;
        } else if (color.equals(Color.RED)) {
            return 2;
        } else if (color.equals(Color.BLUE)) {
            return 3;
        } else if (color.equals(Color.YELLOW)) {
            return 4;
        } else if (color.equals(Color.WHITE)) {
            return 5;
        } else if (color.equals(Color.BLACK)) {
            return 6;
        } else {
            return 0;
        }
    }

    // DropTargetListener methods

    /**
     * Accepts the drag if the data flavor is supported
     * @param dtde the drop target drag event
     * @return void
     * @author Rishit
     */
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        if (dtde.isDataFlavorSupported(Peg.PEG_FLAVOR)) {
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    /**
     * Supports the drag over for drag/drop functionality
     * @param dtde the drop target drag event
     * @return void
     * @author Rishit
     */
    @Override
    public void dragOver(DropTargetDragEvent dtde) {}

    /**
     * Supports the drop action changed for drag/drop functionality
     * @param dtde the drop target drag event
     * @return void
     * @author Rishit
     */
    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {}

    /**
     * Supports the drag exit for drag/drop functionality
     * @param dte the drop target event
     * @return void
     * @author Rishit
     */
    @Override
    public void dragExit(DropTargetEvent dte) {}

    /**
     * Drops the peg into the hole if supported, modifying the color of the hole and repainting the board
     * @param dtde the drop target drop event
     * @return void
     * @throws Exception
     * @author Rishit
     */
    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            Transferable transferable = dtde.getTransferable();
            if (dtde.isDataFlavorSupported(Peg.PEG_FLAVOR)) {
                Peg peg = (Peg) transferable.getTransferData(Peg.PEG_FLAVOR);
                setColor(peg.getPegColor());
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                dtde.dropComplete(true);
                this.getParent().repaint(); // repaints the entire board to remove errors related to dragging
                // print the row of the hole that just got colored for testing
                // System.out.println("Row: " + row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            dtde.rejectDrop();
        }
    }

    /**
     * Paints the hole on the panel, overriding the paintComponent method
     * Sets the rendering hint to make the peg edge smoother using anti-aliasing
     * Fills the hole with the current color
     * @param g the graphics object to paint the hole
     * @return void
     * @author Rishit
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // makes peg edge smoother
        if (!currentColor.equals(new Color(0, 0, 0, 0))) { // if not transparent
            g.setColor(currentColor);
            g.fillOval(0, 0, this.getWidth(), this.getHeight());
        }
    }

    /**
     * Get the row of the hole
     * @param void
     * @return int the row of the hole
     * @author Ethan
     */
    public int getRow() {
        return row;
    }
}
