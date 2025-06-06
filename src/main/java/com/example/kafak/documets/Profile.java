package com.example.kafak.documets;

import java.time.Instant;

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
public  class Profile {
	private String bio;
	private String profilePictureUrl;
	private String coverPhotoUrl;
	private String gender;
	private Instant birthDate;
}