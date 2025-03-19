import org.eclipse.paho.client.mqttv3.*;

import javax.swing.*;

class Engine implements Runnable, MqttCallback {

    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    private static final String TOPIC = "tetris/486";
    private static final String CLIENT_ID = "tetris-subscriber";

    private boolean stop = false;
    private boolean wait = true;
    private MqttClient client;

    public Engine() {
        try {
            client = new MqttClient(BROKER, CLIENT_ID);
            client.setCallback(this); // Set this class as the callback handler
            client.connect();
            client.subscribe(TOPIC);
            System.out.println("Connected and subscribed to: " + TOPIC);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!stop) {
                Thread.sleep(1000); // Keep thread going
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost: " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String receivedMsg = new String(message.getPayload());
        System.out.println("Received message: " + receivedMsg);
        Blackboard.getInstance().addValue("mqttMessage", receivedMsg);

//        if (receivedMsg.equalsIgnoreCase("start_stroop_test")) {
//            SwingUtilities.invokeLater(StroopTest::new);
        }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        //abstract method not needed
    }

    public void stop(boolean stop) {
        this.stop = stop;
        try {
            if (client.isConnected()) {
                client.disconnect();
                System.out.println("Disconnected from broker.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void pause(boolean b) {
        this.wait = b;
    }
}
