package com.example.kafak.apis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafak.controller.ChatMessage;
import com.example.kafak.documets.ConversationDocuments;
import com.example.kafak.documets.UserDocument;
import com.example.kafak.documets.UserIdUsername;
import com.example.kafak.requestbody.LoginDetails;
import com.example.kafak.service.ConversationService;
import com.example.kafak.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = { "http://localhost:5174" }, allowedHeaders = "*", allowCredentials = "true")
public class UserController {
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	private final UserService userService;
	private final ConversationService conversationService;

	private UserController(UserService userService, ConversationService conversationService) {
		this.userService = userService;
		this.conversationService = conversationService;
	}

	@PostMapping
	public UserDocument create(@RequestBody UserDocument user) {
		return userService.createUser(user);
	}

	@GetMapping("/{id}")
	public UserDocument getUser(@PathVariable String id) {
		return userService.getUserById(id);
	}

	@GetMapping("/usernames")
	public ResponseEntity<?> getAllUserNames() {
		List<UserDocument> allUserNames = userService.getAllUserNames();
		List<UserIdUsername> collect = allUserNames.stream().map(e -> new UserIdUsername(e.getId(), e.getUsername()))
				.collect(Collectors.toList());
		return new ResponseEntity<Object>(collect, HttpStatus.OK);
	}

	@GetMapping("/addfriend/{user1}/{user2}")
	public ResponseEntity<?> addFriend(@PathVariable("user1") String user1, @PathVariable("user2") String user2) {

		Map<String,Object> map=new HashMap<>();
		
		ConversationDocuments addFriend = conversationService.addFriend(user1, user2);
		UserDocument findByUserName = userService.findByUserName(user2);
		map.put("saveUser", addFriend);
		map.put("newFriend", findByUserName);
		
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<?> getUser(@RequestBody LoginDetails loginDetails) {
		UserDocument checkLogin = userService.checkLogin(loginDetails);
		
		kafkaTemplate.send("online_user",checkLogin);
		return new ResponseEntity<Object>(checkLogin, HttpStatus.OK);
	}

	// this api call on friend search on change
	@GetMapping("/searchByUsername/{search}")
	public ResponseEntity<?> searchByMatchUsername(@PathVariable("search") String search) {
		List<UserDocument> allUserNames = userService.searchByMatchUsername(search);
		List<UserIdUsername> collect = allUserNames.stream().map(e -> new UserIdUsername(e.getId(), e.getUsername()))
				.collect(Collectors.toList());
		return new ResponseEntity<Object>(collect, HttpStatus.OK);
	}
	
	@GetMapping("/friends/{id}")
	public ResponseEntity<?> getFriendsList(@PathVariable("id") String userId) {
		List<String> friendsListId=new ArrayList<>();
		List<ConversationDocuments> findByConversationUserUser1OrConversationUserUser2 = conversationService.findByConversationUserUser1OrConversationUserUser2(userId,userId);
		findByConversationUserUser1OrConversationUserUser2.forEach(e->{
			
			friendsListId.add(e.getConversationUser().getUser1());
			friendsListId.add(e.getConversationUser().getUser2());
			
		});
		List<String> list = friendsListId.stream().filter(e->!e.equalsIgnoreCase(userId)).distinct().toList();
		List<UserDocument> allUserNames = userService.getAllUserNames();
		
		List<UserDocument> list2 = allUserNames.stream().filter(e->list.contains(e.getId())).toList();
		
		return new ResponseEntity<Object>(list2, HttpStatus.OK);
	}
	
	// id is sendser id
	@GetMapping("/getchat/{id}/{currentUserId}")
	public ResponseEntity<?> getChat(@PathVariable("id") String sender,@PathVariable("currentUserId") String currentUserId) {
		Map<String,Object> map=new HashMap<>();
 	ConversationDocuments findByConversationUserUser1OrConversationUserUser2 = conversationService.findConversationBetweenUsers(sender,currentUserId);
 	map.put("conversession", findByConversationUserUser1OrConversationUserUser2);
 	map.put("id", findByConversationUserUser1OrConversationUserUser2.getId().toHexString());
 	return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
}
