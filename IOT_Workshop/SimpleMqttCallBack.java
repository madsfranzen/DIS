import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class SimpleMqttCallBack implements MqttCallback {
    int status = 0;
    boolean power;

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String res = new String(mqttMessage.getPayload());

        if (res.equals("OFF") || res.equals("ON")) {
            System.out.println(res);
            if (res.equals("OFF")) {
                power = false;
            } else {
                power = true;
            }
            System.out.println("Power is: " + power);

        } else {
            JSONObject jo = new JSONObject(res);
            JSONObject am2301 = jo.getJSONObject("AM2301");
            double humidity = am2301.getDouble("Humidity");
            double temperature = am2301.getDouble("Temperature");

            System.out.println();
            System.out.println("Humidity: [" + humidity + "] Temperature: [" + temperature + "]");

            if (humidity >= 45) {
                if (!power) {
                    App.turnOn();
                }
            } else if (humidity < 45) {
                if (power) {
                    App.turnOff();
                }
            }
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }

}
