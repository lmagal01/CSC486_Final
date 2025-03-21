package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameForm extends JPanel {

    private GameArea ga;
    private GameThread gameThread;
    private JButton speedButton;
    private JButton colorButton;
    private JButton startButton;
    private boolean useColor;
    private JButton restart;

    public GameForm() {
        setLayout(new BorderLayout());

        setFocusable(true);
        requestFocusInWindow();

        //  game area (10x20 grid)
        ga = new GameArea(10, 20);
        ga.setPreferredSize(new Dimension(300, 600));

        //  game area to the CENTER of the frame
        add(ga, BorderLayout.CENTER);

        //  control panel for buttons
        JPanel controlPanel = new JPanel();
        speedButton = new JButton("Increase Speed");
        colorButton = new JButton("Color/NoColor");
        startButton = new JButton("Play");
        restart = new JButton("Restart");
        restart.setVisible(false);

        startButton.addActionListener(e -> {
            initControls();
            startGame();
                });

        // Speed button logic
        speedButton.addActionListener(e -> {
            if (gameThread != null) {
                gameThread.increaseSpeed();
            }
        });

        // Color button logic
        colorButton.addActionListener(e -> {
            useColor = !useColor;
            colorButton.setText(useColor ? "Color: ON" : "Color: OFF");
            ga.setUseColor(useColor);
            ga.repaint();
        });

        controlPanel.add(startButton);
        controlPanel.add(speedButton);
        controlPanel.add(colorButton);
        controlPanel.add(restart);
        add(controlPanel, BorderLayout.SOUTH);


    }

    //controls are inputted here in keyboard
    private void initControls() {
        setFocusable(true);
        requestFocusInWindow();



        InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");

        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Right key pressed!");  // debugging with print statements
                ga.moveBlockRight();
                ga.repaint();
            }
        });

        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Left key pressed!");  // debugging with print statements
                ga.moveBlockLeft();
                ga.repaint();
            }
        });

        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Up key pressed!");  // debugging with print statements
                ga.rotateBlock();
                ga.repaint();
            }
        });

        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Down key pressed!");  // debugging with print statements
                ga.moveDown();
                ga.repaint();
            }
        });
    }
    //function starts game
    public void startGame() {
        gameThread = new GameThread(ga, this);
        gameThread.start();
        SwingUtilities.invokeLater(() ->startButton.setVisible(false));
    }


    public void restartGame(){
        SwingUtilities.invokeLater(() ->startButton.setVisible(true));
        ga.clearGrid();

    }
}
