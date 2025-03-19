

import tetris.GameForm;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private Engine engine; // MQTT Engine
    private GameForm gameForm; // Tetris Game



        private JMenuBar createMenuBar() {
            Controller controller = new Controller(this);
            JMenuBar menuBar = new JMenuBar();
            JMenu connectMenu = new JMenu("Connection");
            JMenu helpMenu = new JMenu("Help");
            JMenu startTetris = new JMenu("Play");

            JMenuItem aboutMenuItem = new JMenuItem("About");
            JMenuItem startMenuItem = new JMenuItem("Start Connection");
            JMenuItem stopMenuItem = new JMenuItem("Stop");
            JMenuItem startGame = new JMenuItem("Start Game");

            connectMenu.add(startMenuItem);
            connectMenu.add(stopMenuItem);
            helpMenu.add(aboutMenuItem);
            startTetris.add(startGame);


            startMenuItem.addActionListener(controller);
            stopMenuItem.addActionListener(controller);
            aboutMenuItem.addActionListener(controller);
            startGame.addActionListener(controller);

            menuBar.add(connectMenu);
            menuBar.add(helpMenu);
            menuBar.add(startTetris);
            return menuBar;
        }

    public Main() {
        setJMenuBar(createMenuBar());
        setLayout(new BorderLayout());
        setTitle("Tetris");


        gameForm = new GameForm();

        add(gameForm, BorderLayout.CENTER);

        gameForm.setFocusable(true);
        gameForm.requestFocusInWindow();



        ViewPanel centralPanel = new ViewPanel();
        StatusBar viewStatusBar = new StatusBar();

        add(viewStatusBar, BorderLayout.SOUTH);
    }


    public void pauseThread(boolean startSubscriber) {
        if (!startSubscriber) {
            if (engine == null) {
                engine = new Engine();
                Thread engineThread = new Thread(engine);
                engineThread.start();
                System.out.println("Subscriber started.");
            } else {
                System.out.println("Subscriber is already running.");
            }
        } else {
            if (engine != null) {
                engine.stop(true);
                engine = null;
                System.out.println("Subscriber stopped.");
            } else {
                System.out.println("Subscriber is not running.");
            }
        }
    }

    public void about() {
        JOptionPane.showMessageDialog(this, "MQTT Subscriber About");
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setSize(900, 700);  // Make window larger
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }

    public void startGame() {
            gameForm.startGame();
    }
}
