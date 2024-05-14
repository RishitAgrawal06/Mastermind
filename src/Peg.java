import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;

public class Peg extends JComponent implements DragGestureListener, DragSourceListener, Transferable {
    // instance variables
    // drag and drop variables
    static final DataFlavor PEG_FLAVOR = new DataFlavor(Peg.class, "Peg Object");
    private static final DataFlavor[] SUPPORTED_FLAVORS = {PEG_FLAVOR};
    private final Color color;
    private final DragSource dragSource;
    
    /**
     * Constructor for the Peg class
     * Sets the color of the peg and the preferred size of the peg to 30x30 pixels
     * Sets the background of the peg to the color
     * Creates a drag source for the peg
     * @param color the color of the peg
     * @return void
     * @author Rishit
     */
    public Peg(Color color) {
        this.color = color;
        this.setPreferredSize(new Dimension(30, 30));
        this.setBackground(color);
        this.setOpaque(true);
        dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
    }

    /**
     * Gets the color of the peg
     * @param void
     * @return the color of the peg
     * @author Rishit
     */
    public Color getPegColor() {
        return color;
    }

    // transferable methods for drag and drop between Peg and Hole for UI accessibility
    /**
     * Gets the supported data flavors for the peg
     * @param void
     * @return the supported data flavors for the peg
     * @author Rishit
     */
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    /**
     * Checks if the data flavor is supported
     * @param flavor the data flavor to check
     * @return true if the data flavor is supported, false otherwise
     * @author Rishit
     */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(PEG_FLAVOR);
    }

    /**
     * Gets the transfer data for the peg
     * @param flavor the data flavor to get the transfer data for
     * @return the transfer data for the peg
     * @throws UnsupportedFlavorException
     * @author Rishit
     */
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(PEG_FLAVOR)) {
            return this;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    /**
     * Handles the drag gesture recognized event for the peg
     * Sets the mannerism for dragging the pegs using the cursor and its relative position
     * @param dge the drag gesture event
     * @return void
     * @author Rishit
     */
    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        // dragSource.startDrag(dge, DragSource.DefaultCopyDrop, this, this);
        BufferedImage dragImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dragImage.createGraphics();
        paintComponent(g2);
        g2.dispose();
        // dragging animation from Peg origin to appropriate hole - (18, 18) is for centering the peg on the cursor
        dragSource.startDrag(dge, Cursor.getDefaultCursor(), dragImage, new Point(18, 18), this, this);
    }

    // DragSourceListener methods
    /**
     * Supports the drag enter for drag/drop functionality
     * @param dsde the drag source drag event
     * @return void
     * @author Rishit
     */
    @Override
    public void dragEnter(DragSourceDragEvent dsde) {}

    /**
     * Supports the drag over for drag/drop functionality
     * @param dsde the drag source drag event
     * @return void
     * @author Rishit
     */
    @Override
    public void dragOver(DragSourceDragEvent dsde) {}

    /**
     * Supports the drop action changed for drag/drop functionality
     * @param dsde the drag source drag event
     * @return void
     * @author Rishit
     */
    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {}

    /**
     * Supports the drag exit for drag/drop functionality
     * @param dse the drag source event
     * @return void
     * @author Rishit
     */
    @Override
    public void dragExit(DragSourceEvent dse) {}

    /**
     * Supports the drag drop end for drag/drop functionality
     * @param dsde the drag source drop event
     * @return void
     * @author Rishit
     */
    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {}

    /**
     * Paints the peg on the panel, overriding the paintComponent method
     * @param g the graphics object to paint the peg
     * @return void
     * @author Rishit
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // makes peg edge smoother
        g.setColor(color);
        g.fillOval(5, 5, this.getWidth()-5, this.getHeight()-5);
    }
}
