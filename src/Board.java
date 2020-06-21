import javax.swing.*;
import java.awt.*;

public class Board extends JComponent {
    // maxSize x maxSize (Example: 10x10)
    public int maxSize = 10;
    private boolean active;
    public int delay = 80000000;

    private CellManager manager;

    public Image image;
    private Graphics2D g2;

    public Board() {
        setDoubleBuffered(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        createImage();

        g.drawImage(image, 0, 0, null);
    }

    /**
     * Method that is called when a maze should be generated
     */
    public void startGeneration() {
        active = true;
        manager = new CellManager();
        // Create Cells
        for (int x = 0; x < maxSize; x++) {
            for (int y = 0; y < maxSize; y++) {
                if(x == 0 && y == 0) {
                    manager.addNewCell(new Cell(x, y, Type.START));
                    continue;
                }
                if(x == (maxSize - 1) && y == (maxSize - 1)) {
                    manager.addNewCell(new Cell(x, y, Type.END));
                }
                manager.addNewCell(new Cell(x, y, Type.NORMAL));
            }
        }

        // Checks to see if image is null
        createImage();

        // Method that recursively generates the maze
        generate(manager.getCell(0, 0));

        // Getting things ready for next execution of maze
        active = false;
        image = null;
    }

    /**
     * Recursive backtracking using depth first search(DFS)
     * New cell -> push cell to stack -> mark as visited -> chose a neighbor([at random], [cant be visited], if there are no neighbors, start popping
     * from the stack until we get a cell that has neighbors) [REPEAT]
     * @param cell
     * @return
     */
    private boolean generate(Cell cell) {
        boolean straightPath = true;

        manager.addCellToStack(cell);
        manager.setVisited(cell);

        // Base method that prevents infinite recursion
        if (manager.getVisited().size() == (maxSize * maxSize)) {
            return true;
        }

        // Gets neighboring cell
        Cell neighborCell = manager.getNeighborCell(cell);

        // If neighborCell is null, that means that there was no neighbors, time to start popping off the stack
        if (neighborCell == null) {
            straightPath = false;
            while (neighborCell == null) {
                Cell oldCell = manager.popCell();
                neighborCell = manager.getNeighborCell(oldCell);

                if (neighborCell != null) {
                    drawLine(g2, oldCell, neighborCell);
                }
            }
        }

        // Draws lines
        if (straightPath) {
            drawLine(g2, cell, neighborCell);
        }

        // Delay used to be able to visually see the maze
        if(delay > 0)
            sleep(delay);

        return generate(neighborCell);
    }

    /**
     * Method used to drawlines between two cells
     * @param g         Graphics Object
     * @param cell1     First Cell
     * @param cell2     Second Cell
     */
    private void drawLine(Graphics2D g, Cell cell1, Cell cell2) {
        int x1 = (cell1.getX() * 950 / maxSize) + (500 / maxSize);
        int x2 = (cell1.getY() * 950 / maxSize) + (400 / maxSize);
        int y1 = (cell2.getX() * 950 / maxSize) + (500 / maxSize);
        int y2 = (cell2.getY() * 950 / maxSize) + (400 / maxSize);

        Color color = Color.WHITE;

        if(g != null) {
            if(cell1.getType() == Type.NORMAL || cell2.getType() == Type.NORMAL) {
                color = Color.WHITE;
            }
            if(cell1.getType() == Type.START || cell2.getType() == Type.START) {
                color = Color.GREEN;
            }
            if(cell1.getType() == Type.END || cell2.getType() == Type.END) {
                color = Color.RED;
            }

            //System.out.println("[" + cell1.getX() + "," + cell1.getY() + "]" + "[" + cell2.getX() + "," + cell2.getY() + "Cell1: " + cell1.getType() + " Cell2: " + cell2.getType() + " Color:" + color);

            g.setColor(color);
            g.drawLine(x1, x2, y1, y2);
            repaint();
        }
    }

    /**
     * Method used to create the image
     */
    private void createImage() {
        if(image == null) {
            image = createImage(1000, 1000);
            g2 = (Graphics2D) image.getGraphics();
            g2.setStroke(new BasicStroke(500 / maxSize, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
    }

    /**
     * Method used to setup the background
     */
    private void clear() {
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, 1000, 1000);
        g2.setPaint(Color.WHITE);
        repaint();
    }

    /**
     * Method used to add delay and make an animation effect
     * @param nanoseconds       Delay between drawLine executions
     */
    private void sleep(long nanoseconds) {
        long timeElapsed;
        final long startTime = System.nanoTime();
        do {
            timeElapsed = System.nanoTime() - startTime;
        } while(timeElapsed < nanoseconds);
    }

    public boolean isActive() {
        return active;
    }
}
