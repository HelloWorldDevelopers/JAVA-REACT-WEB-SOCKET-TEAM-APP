package com.example.kafak.controller;

import lombok.Data;

@Data
public class ChatMessage {
    private String sender;
    private String receiver;
    private String content;
    private String type;
    private String chatId;
}
