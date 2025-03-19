import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class is an ActionListener that listens to the JMenuItems.
 * It calls the methods about, pauseThread of the Main class.
 *
 * @author javiergs
 * @version 1.0
 */
public class Controller implements ActionListener {

    private Main viewMain;

    public Controller(Main viewMain) {
        this.viewMain = viewMain;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("About")) {
            viewMain.about();
        } else if (e.getActionCommand().equals("Start")) {
            System.out.println("Start");
            viewMain.pauseThread(false);
        } else if (e.getActionCommand().equals("Stop")) {
            System.out.println("Stop");
            viewMain.pauseThread(true);
        }else if(e.getActionCommand().equals("Play")){
            viewMain.startGame();

        }
    }
}