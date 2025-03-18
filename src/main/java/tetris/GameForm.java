package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameForm extends JFrame {

    private JPanel gameAreaPlaceholder;
    private GameArea ga;

    public GameForm() {
        setTitle("Game Form");
        setSize(600, 500); // Adjusted for better centering
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new GridBagLayout()); // Use GridBagLayout for proper centering

        // Initialize placeholder panel (acts as a centered container)
        gameAreaPlaceholder = new JPanel();
        gameAreaPlaceholder.setPreferredSize(new Dimension(400, 300));
        gameAreaPlaceholder.setBackground(Color.LIGHT_GRAY);
        gameAreaPlaceholder.setLayout(new GridBagLayout());

        // Create and add the tetris.GameArea
        ga = new GameArea(10, 15); // 10 columns, 6 rows
        gameAreaPlaceholder.add(ga);

        // Add the placeholder to the JFrame
        add(ga);
        initControls();
        startGame();
    }

    private void initControls()
    {
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"),"right");
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

    public void startGame()
    {
        new GameThread(ga, this).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameForm().setVisible(true));
    }

//    public void updateScore(int score)
//    {
//        scoreDisplay.setText("Score" + score);
//    }
//    public void updateLevel(int level)
//    {
//        levelDisplay.setText("Level " + level);
//    }
}
