package com.llf.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Publisher {

	public static final String HOST = "tcp://localhost:1883";
	public static final String TOPIC124 = "toclient/124";
	public static final String TOPIC125 = "toclient/125";
	private static final String clientid = "server";

	private MqttClient client;
	private MqttTopic topic124;
	private MqttTopic topic125;
	private String userName = "admin";
	private String passWord = "admin";

	private MqttMessage message;

	public Publisher() throws MqttException {
		// MemoryPersistence设置clientid的保存形式，默认为以内存保存
		client = new MqttClient(HOST, clientid, new MemoryPersistence());
		connect();
	}

	private void connect() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		// 设置超时时间
		options.setConnectionTimeout(10);
		// 设置会话心跳时间
		options.setKeepAliveInterval(20);
		try {
			client.setCallback(new PushCallback());
			client.connect(options);
			topic124 = client.getTopic(TOPIC124);
			topic125 = client.getTopic(TOPIC125);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
		MqttDeliveryToken token = topic.publish(message);
		token.waitForCompletion();
		System.out.println("message is published completely! " + token.isComplete());
	}

	public static void main(String[] args) throws MqttException {
		Publisher server = new Publisher();

		server.message = new MqttMessage();
		server.message.setQos(2);
		server.message.setRetained(true);
		server.message.setPayload("给客户端124推送的信息".getBytes());
		server.publish(server.topic124, server.message);

		server.message = new MqttMessage();
		server.message.setQos(2);
		server.message.setRetained(true);
		server.message.setPayload("给客户端125推送的信息".getBytes());
		server.publish(server.topic125, server.message);

		System.out.println(server.message.isRetained() + "------ratained状态");
	}

}
