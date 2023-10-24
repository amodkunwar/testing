package com.example.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.service.KafkaMessagePublisher;

@RestController
public class EventController {

	@Autowired
	private KafkaMessagePublisher kafkaMessagePublisher;

	@PostMapping("/publish/{message}")
	public ResponseEntity<String> sendMessage(@PathVariable String message) {
		try {
			for (int i = 0; i < 100000; i++) {
				kafkaMessagePublisher.sendMessage("message " + i + message);
			}
			return ResponseEntity.ok("Message successfully publish to the topic!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
