import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;

public class Peg extends JComponent implements DragGestureListener, DragSourceListener, Transferable {
    static final DataFlavor PEG_FLAVOR = new DataFlavor(Peg.class, "Peg Object");
    private static final DataFlavor[] SUPPORTED_FLAVORS = {PEG_FLAVOR};
    private final Color color;
    private final DragSource dragSource;
    
    public Peg(Color color) {
        this.color = color;
        this.setPreferredSize(new Dimension(30, 30));
        this.setBackground(color);
        this.setOpaque(true);
        dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
    }

    public Color getPegColor() {
        return color;
    }

    // transferable methods for drag and drop between Peg and Hole for UI accessibility
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(PEG_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(PEG_FLAVOR)) {
            return this;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

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
    @Override
    public void dragEnter(DragSourceDragEvent dsde) {}

    @Override
    public void dragOver(DragSourceDragEvent dsde) {}

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {}

    @Override
    public void dragExit(DragSourceEvent dse) {}

    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // makes peg edge smoother
        g.setColor(color);
        g.fillOval(5, 5, this.getWidth()-5, this.getHeight()-5);
    }
}
