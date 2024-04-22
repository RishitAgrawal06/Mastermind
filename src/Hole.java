import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

public class Hole extends JPanel implements DropTargetListener {
    private Color currentColor = new Color(0, 0, 0, 0);

    public Hole() {
        setPreferredSize(new Dimension(30, 30));
        setBackground(new Color(0, 0, 0, 0));
        new DropTarget(this, this);
    }

    public void setColor(Color color) {
        currentColor = color;
        this.repaint();
    } 

    // DropTargetListener methods
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        if (dtde.isDataFlavorSupported(Peg.PEG_FLAVOR)) {
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {}

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {}

    @Override
    public void dragExit(DropTargetEvent dte) {}

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
            }
        } catch (Exception e) {
            e.printStackTrace();
            dtde.rejectDrop();
        }
    }

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
}
