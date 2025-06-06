package com.example.kafak.documets;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDocument {

	@Id
	private String id;

	private String username;
	private String email;
	private String fullName;
	private String phone;
	private String passwordHash;

	private Profile profile;
	private AccountStatus accountStatus;
	private Location location;
	private PrivacySettings privacySettings;
	private SecuritySettings securitySettings;
	private NotificationPreferences notificationPreferences;

	private List<Device> devices;
	private List<String> blockedUsers;

	private boolean isActive;
	private Instant createdAt;
	private Instant updatedAt;

}
