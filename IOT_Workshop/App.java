import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class App {
    private static MqttClient sampleClient;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\nHello IOT");

        String broker = "tcp://192.168.0.115:1883";
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            sampleClient = new MqttClient(broker, MqttClient.generateClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            sampleClient.setCallback(new SimpleMqttCallBack());

            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            sampleClient.subscribe("stat/grp7455/POWER");
            sampleClient.subscribe("tele/grp7455/SENSOR");

            Thread.sleep(200000);

            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

        }
    }

    public static void publishMessage(String topicsend, String content)
            throws MqttPersistenceException, MqttException {
        MqttMessage message = new MqttMessage();
        message.setPayload(content.getBytes());
        System.out.println(content.getBytes());
        sampleClient.publish(topicsend, message);
        System.out.println("Message published");
    }

    public static void turnOn() throws MqttPersistenceException, MqttException {
        System.out.println("TURNING ON");
        publishMessage("cmnd/grp7455/Power1", "1");
    }

    public static void turnOff() throws MqttPersistenceException, MqttException {
        System.out.println("TURNING OFF");
        publishMessage("cmnd/grp7455/Power1", "0");
    }
}
