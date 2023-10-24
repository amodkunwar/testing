package com.example.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {

	@Value("${kafka.partitions}")
	private Integer partitions;

	@Value("${kafka.topic}")
	private String topic;

	@Bean
	public NewTopic createTopic() {
		return new NewTopic(topic, partitions, (short) 1);
	}

}
