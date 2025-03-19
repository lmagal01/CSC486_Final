import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * StatusBar implements PropertyChangeListener to listen to the time property of the Blackboard.
 * It updates the JLabel with the new time value.
 *
 * @author javiergs
 * @version 1.0
 */
public class StatusBar extends JPanel implements PropertyChangeListener {

    private JLabel statusLabel;

    public StatusBar() {
        statusLabel = new JLabel("Status: waiting");
        add(statusLabel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("time")) {
            statusLabel.setText("Status: " + evt.getNewValue());
        }
    }

}