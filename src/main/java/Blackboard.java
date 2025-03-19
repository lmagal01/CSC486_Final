import java.beans.PropertyChangeSupport;
import java.util.LinkedList;

/**
 * Blackboard class is a Singleton that contains a list of samples.
 * It extends PropertyChangeSupport to notify the observers when a new sample is added.
 *
 * @author javiergs
 * @version 1.0
 */
public class Blackboard extends PropertyChangeSupport {

    private static Blackboard instance;
    private LinkedList<String> samples;

    private Blackboard() {
        super(new Object());
        samples = new LinkedList<>();
    }

    public static Blackboard getInstance() {
        if (instance == null) {
            instance = new Blackboard();
        }
        return instance;
    }

    public void addValue(String key, Object value) {
        samples.add(key + ": " + value);
        firePropertyChange(key, null, value);
    }

}