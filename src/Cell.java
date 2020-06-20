public class Cell {
    // [x,y] coordinates of cell
    private int x;
    private int y;

    // determines if left/right/top/bottom is a border
    private boolean left;
    private boolean right;
    private boolean top;
    private boolean bottom;

    private Type type;

    public Cell(int x, int y, Type type) {
        this.x = x;
        this.y = y;

        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }
}
