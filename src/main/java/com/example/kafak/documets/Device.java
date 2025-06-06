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
public class Device {
	private String token;
	private String platform;
	private String model;
	private String appVersion;
	private String timezone;
	private Instant lastActive;
}