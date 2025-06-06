package com.example.kafak.controller;

import java.util.List;

import lombok.Data;

@Data
public class GroupChatMessage {
	 private String sender;
	    private List<String> receiver;
	    private String content;
	    private String timestamp;
}
