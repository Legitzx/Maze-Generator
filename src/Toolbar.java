import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class Toolbar {
    // Components
    private JToolBar toolBar;
    private JButton generate;
    private JButton solve;
    private JSlider speed;
    private JTextField dimension;
    private JLabel dimensionLabel;

    // Dependencies
    private Board board;

    /**
     * Sets up config for toolbar
     * @param board     Board that we will be adding the toolbar to
     */
    public Toolbar(Board board) {
        this.board = board;

        generate = new JButton("Generate");
        solve = new JButton("Solve");

        speed = new JSlider(JSlider.HORIZONTAL, 1000000, 80000000, 80000000);
        speed.setMajorTickSpacing(1000000);
        speed.setMinorTickSpacing(500000);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(80000000, new JLabel("Slow"));
        labelTable.put(1000000, new JLabel("Fast"));
        speed.setLabelTable(labelTable);
        speed.setPaintLabels(true);

        dimension = new JTextField(4);
        dimension.setText("10");
        dimensionLabel = new JLabel("Dimension");

        toolBar = new JToolBar();

        eventListener();
    }

    /**
     * Adds components to toolbar
     */
    public JToolBar createToolbar() {
        toolBar.add(generate);
        toolBar.add(solve);
        toolBar.add(speed);
        toolBar.addSeparator();
        toolBar.add(dimensionLabel);
        toolBar.addSeparator();
        toolBar.add(dimension);
        toolBar.setFloatable(false);
        toolBar.setOpaque(true);

        return toolBar;
    }

    /**
     * Returns the BorderLayout for the toolbar
     */
    public String getBorderLayout() {
        return BorderLayout.SOUTH;
    }

    /**
     * Event Listener for the toolbar
     */
    private void eventListener() {
        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                Board.delay = source.getValue();
            }
        });

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Thread worker = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(!board.isActive()) {
                                    String dimensionText = dimension.getText();
                                    try {
                                        int dim = Integer.parseInt(dimensionText);
                                        board.maxSize = dim;
                                    } catch (Exception e) {
                                        board.maxSize = 10;
                                    }

                                    System.out.println(board.maxSize);
                                    
                                    board.startGeneration();
                                }
                            }
                        });

                        worker.start();
                    }
                });
            }
        });
    }
}
