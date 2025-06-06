package com.example.kafak.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.kafak.documets.ConversationDocuments;

public interface ConversationRepo extends MongoRepository<ConversationDocuments, String> {

	List<ConversationDocuments> findByConversationUserUser1OrConversationUserUser2(String userId, String userId2);

	@Query("""
			{
			  "$or": [
			    { 
			      "$and": [
			        { "conversationUser.user1": ?0 },
			        { "conversationUser.user2": ?1 }
			      ]
			    },
			    { 
			      "$and": [
			        { "conversationUser.user1": ?1 },
			        { "conversationUser.user2": ?0 }
			      ]
			    }
			  ]
			}
			""")
	ConversationDocuments findConversationBetweenUsers(String sender, String receiver);


}
