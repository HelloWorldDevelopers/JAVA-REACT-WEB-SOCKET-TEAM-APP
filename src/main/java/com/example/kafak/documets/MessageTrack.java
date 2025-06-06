package com.example.kafak.documets;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageTrack {

	
	private String uuid;
	 
	private String sender;
	private String receiver;
	private String messageType;
	private String content;

	private Status status;
	private Timestamps timestamps;
	private List<Reaction> reactions;

	private boolean isDeleted;
	private boolean isEdited;
	private Date createdAt;
	private Date updatedAt;
}
