package com.example.kafak.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.example.kafak.service.ConversationService;

@Component
public class KafkaChatListener {

	private SimpMessagingTemplate messagingTemplate;

	private ConversationService conversationService;
	
	
	

	public KafkaChatListener(SimpMessagingTemplate messagingTemplate, ConversationService conversationService) {
		super();
		this.messagingTemplate = messagingTemplate;
		this.conversationService = conversationService;
	}




	@KafkaListener(topics = "Topic_2", groupId = "chat-group")
	public void listen(ChatMessage message) {

		System.out.println("list1    " + message.toString());
		// Send to specific user using receiverId
		messagingTemplate.convertAndSend("/Private/" + message.getReceiver(), message);
		// messagingTemplate.convertAndSendToUser("vd1473", "/queue/private", message);
		
	}
	
	@KafkaListener(topics = "online_user", groupId = "online_user_notification")
	public void onliceUser(ChatMessage message) {
		
		System.out.println("list1    " + message.toString());
		// Send to specific user using receiverId
		messagingTemplate.convertAndSend("/Private/" + message.getReceiver(), message);
		// messagingTemplate.convertAndSendToUser("vd1473", "/queue/private", message);
		
	}

}
