package com.example.kafak.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.kafak.controller.ChatMessage;
import com.example.kafak.documets.ConversationDocuments;
import com.example.kafak.documets.ConversionUser;
import com.example.kafak.documets.MessageTrack;
import com.example.kafak.documets.Reaction;
import com.example.kafak.documets.Status;
import com.example.kafak.documets.Timestamps;
import com.example.kafak.repo.ConversationRepo;

@Service
public class ConversationService {

	private ConversationRepo conversationRepo;

	public ConversationService(ConversationRepo conversationRepo) {
		super();
		this.conversationRepo = conversationRepo;
	}

 

	public ConversationDocuments addFriend(String user1, String user2) {
		
		ConversationDocuments conversationDocuments=new ConversationDocuments();
		MessageTrack messageTrack=new MessageTrack();
		messageTrack.setContent("Hi");
		messageTrack.setMessageType("Text");
		messageTrack.setSender(user1);
		messageTrack.setReceiver(user2);
		conversationDocuments.setMessageTrack(List.of(messageTrack));
		conversationDocuments.setConversationUser(new ConversionUser(user1,user2));
	 
		ConversationDocuments insert = conversationRepo.insert(conversationDocuments);
		return insert;
	}



	public List<ConversationDocuments> findByConversationUserUser1OrConversationUserUser2(String userId, String userId2) {
		
		
		return conversationRepo.findByConversationUserUser1OrConversationUserUser2(userId,userId2);
	}



	public ConversationDocuments findConversationBetweenUsers(String sender, String sender2) {
		 
		return conversationRepo.findConversationBetweenUsers(sender,sender2);
	}



	public void saveChatMessage(ChatMessage message) {
		 
		Optional<ConversationDocuments> findById = conversationRepo.findById(message.getChatId());
		findById.ifPresentOrElse((e)->{
			MessageTrack messageTrack=new MessageTrack();
 			messageTrack.setSender(message.getSender());
			messageTrack.setReceiver(message.getReceiver());
			messageTrack.setContent(message.getContent());
			messageTrack.setMessageType(message.getType());
			messageTrack.setEdited(false);
			messageTrack.setCreatedAt(new Date());
			messageTrack.setDeleted(false);
			messageTrack.setStatus(Status.builder().delivered(true).read(false).sent(true).build());
			messageTrack.setTimestamps(new Timestamps().builder().deliveredAt(new Date()).readAt(null).sentAt(new Date()).build());
 			messageTrack.setReactions(List.of(new Reaction().builder().emoji(null).userId(null).reactedAt(null).build()));
			messageTrack.setUuid(UUID.randomUUID().toString());
 			e.getMessageTrack().add(messageTrack);
			
			ConversationDocuments save = conversationRepo.save(e);
		}, ()->{
			
		});
	}



	

}
