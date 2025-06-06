package com.example.kafak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafak.service.ConversationService;

@RestController

public class TestController {

	@Autowired
	private KafkaTemplate<String, ChatMessage> kafkaTemplate;
	
 
	private ConversationService conversationService;
	
	
	

	public TestController(ConversationService conversationService) {
		super();
 		this.conversationService = conversationService;
	}


	@PostMapping("/private/chat") // maps to /app/chat
	public ResponseEntity<?> sendPrvateMessage(@RequestBody ChatMessage message) {
		System.out.println(message.toString());
		kafkaTemplate.send("Topic_2", message);
		conversationService.saveChatMessage(message);
		return ResponseEntity.ok(message);
	}
	
	
	 
}
