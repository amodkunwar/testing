package com.example.kafka.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaMessagePublisher {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${kafka.topic}")
	private String topic;

	public void sendMessage(String message) {
		CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);

		future.whenComplete((result, ex) -> {

			if (ex == null) {
				log.info("Message {} and offset is {}", message, result.getRecordMetadata().offset());
			} else {
				log.info("Unable to send message to topic : {}", ex.getLocalizedMessage());
			}

		});

	}

}
