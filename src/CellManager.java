import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

public class CellManager {
    private ArrayList<Cell> cells = new ArrayList<>();
    private ArrayList<Cell> visited = new ArrayList<>();
    private Stack<Cell> cellStack = new Stack<>();

    public void addNewCell(Cell cell) {
        cells.add(cell);
    }

    public void setVisited(Cell cell) {
        visited.add(cell);
    }

    public void addCellToStack(Cell cell) {
        cellStack.add(cell);
    }

    public Cell popCell() {
        return cellStack.pop();
    }

    /**
     * Gets a cell from (x,y) coords
     * @param x     x coord
     * @param y     y coord
     * @return      a cell from (x,y) coords, if such cell at coords (x,y) does not exist -> return null
     */
    public Cell getCell(int x, int y) {
        for(Cell cell : cells) {
            if((cell.getX() == x) && (cell.getY() == y)) {
                return cell;
            }
        }
        return null;
    }

    public ArrayList<Cell> getVisited() {
        return visited;
    }

    /**
     * Checks the cell neighbors, will return null if there are no neighbors
     * @param cell      current cell we are checking neighbors for
     * @return          a cell that has not be visited
     */
    public Cell getNeighborCell(Cell cell) {
        ArrayList<Cell> availableNeighbors = new ArrayList<>();

        int xBase = cell.getX();
        int yBase = cell.getY();

        // left
        Cell left = getCell(xBase - 1, yBase);
        // right
        Cell right = getCell(xBase + 1, yBase);
        // top
        Cell top = getCell(xBase, yBase - 1);
        // bottom
        Cell bottom = getCell(xBase, yBase + 1);

        if(isAvailable(left)) {
            availableNeighbors.add(left);
        }
        if(isAvailable(right)) {
            availableNeighbors.add(right);
        }
        if(isAvailable(top)) {
            availableNeighbors.add(top);
        }
        if(isAvailable(bottom)) {
            availableNeighbors.add(bottom);
        }

        if(availableNeighbors.isEmpty()) {
            return null;
        }

        Iterator<Cell> iter = availableNeighbors.iterator();
        while(iter.hasNext()) {
            Cell c = iter.next();

            if(visited.contains(c)) {
                iter.remove();
            }
        }

        if(availableNeighbors.isEmpty()) {
            return null;
        }

        return pickRandomCell(availableNeighbors);
    }

    private boolean isAvailable(Cell cell) {
        return cell != null;
    }

    /**
     * Picks Random Side from an array
     * @param cellz     Array
     * @return          Random Side
     */
    private Cell pickRandomCell(ArrayList<Cell> cellz) {
        int randNum = new Random().nextInt(cellz.size());
        return cellz.get(randNum);
    }
}
