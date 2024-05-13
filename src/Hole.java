import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

public class Hole extends JPanel implements DropTargetListener {
    private Color currentColor = new Color(0, 0, 0, 0);
    private int row, col;
    private Algorithims game;

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

    public void setColor(Color color, Algorithims game) {
        currentColor = color;
        this.repaint();
        game.setColor(row, col, colorToNum(color));
    } 

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
                setColor(peg.getPegColor(), game);
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                dtde.dropComplete(true);
                this.getParent().repaint(); // repaints the entire board to remove errors related to dragging
                // print the row of the hole that just got colored
                System.out.println("Row: " + row);
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

    public int getRow() {
        return row;
    }
}
