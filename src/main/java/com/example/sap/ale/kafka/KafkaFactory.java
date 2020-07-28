package com.example.sap.ale.kafka;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaFactory {
	/* Please update KEY PASSWORD & TOPIC NAME Below 
	KEY PASSWORD for JKS - passed as environment variable PASSWORD_ENV
	TOPIC NAME for TOPIC*/
	private static final String PASSWORD_ENV = "KEY_PASSWORD";
	public static final String TOPIC = "TOPIC NAME";

	private static Properties loadProperties() {
		final Properties prop = new Properties();
		try {
			prop.load(KafkaFactory.class.getClassLoader().getResourceAsStream("kafka_producer.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return prop;
	}

	private static void applyPasswords(Properties props) {
		String pw = System.getenv(PASSWORD_ENV);
		if (pw == null) {
			throw new RuntimeException("Missing mandatory environment variable: " + PASSWORD_ENV);
		}

		String[] passwordProps = { "ssl.truststore.password", "ssl.keystore.password", "ssl.key.password" };
		for (String name : passwordProps) {
			props.setProperty(name, pw);
		}
	}

	public static Producer<String, String> createProducer() {
		Properties props = loadProperties();
		applyPasswords(props);
		return new KafkaProducer<>(props);
	}

	public static void main(String[] args) {
		final ProducerRecord<String, String> record = new ProducerRecord<>("test", "Hello From SAP ALE demo");

		try (Producer<String, String> producer = createProducer()) {
			producer.send(record);
			producer.flush();
		}
	}
}
