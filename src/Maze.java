import javax.swing.*;
import java.awt.*;

public class Maze extends JFrame {

    public Maze() {
        setTitle("Maze Generator");

        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        Board board = new Board();
        content.add(board, BorderLayout.CENTER);

        Toolbar toolbar = new Toolbar(board);

        JPanel controls = new JPanel();
        controls.add(toolbar.createToolbar(), toolbar.getBorderLayout());

        content.add(controls, BorderLayout.NORTH);

        setSize(1000, 1100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        board.startGeneration();
    }

    public static void main(String[] args) {
        new Maze().show();
    }
}