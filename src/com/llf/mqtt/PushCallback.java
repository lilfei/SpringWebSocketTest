package com.llf.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PushCallback implements MqttCallback {

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("PushCallback connectionLost: " + cause.toString());
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("PushCallback messageArrived: " + topic + ",msg: " + message);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("PushCallback deliveryComplete: " + token);
	}

}
