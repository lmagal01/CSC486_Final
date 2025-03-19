package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameForm extends JFrame {

    private GameArea ga;
    private GameThread gameThread;
    private JButton speedButton;

    public GameForm() {
        setTitle("Tetris Game");
        setSize(600, 700); // Adjust size to fit game area and controls
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Use BorderLayout instead of GridBagLayout

        // Create the game area (10x20 grid)
        ga = new GameArea(10, 20);
        ga.setPreferredSize(new Dimension(900, 800));

        // Add game area to the CENTER of the frame
        add(ga, BorderLayout.CENTER);

        // Create control panel for the button
        JPanel controlPanel = new JPanel();
        speedButton = new JButton("Increase Speed");

        // Button increases the speed
        speedButton.addActionListener((ActionEvent e) -> {
            if (gameThread != null) {
                gameThread.increaseSpeed();
            }
        });

        controlPanel.add(speedButton);

        // Add button panel to the SOUTH of the frame
        add(controlPanel, BorderLayout.SOUTH);

        // Initialize controls
        initControls();
        startGame();
    }

    private void initControls() {
        InputMap im = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");

        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockRight();
            }
        });
        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockLeft();
            }
        });
        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.rotateBlock();
            }
        });
        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveDown();
            }
        });
    }

    public void startGame() {
        gameThread = new GameThread(ga, this);
        gameThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameForm().setVisible(true));
    }
}
