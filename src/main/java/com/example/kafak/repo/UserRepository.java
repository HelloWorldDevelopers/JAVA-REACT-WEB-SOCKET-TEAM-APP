package com.example.kafak.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.kafak.documets.UserDocument;


public interface UserRepository extends MongoRepository<UserDocument, String> {
	UserDocument findByUsername(String username);
    boolean existsByEmail(String email);
	UserDocument findByUsernameAndPasswordHash(String username,String password);
	UserDocument findByEmailAndPasswordHash(String email,String password);
	UserDocument findByPhoneAndPasswordHash(String phone,String password);
	List<UserDocument> findByUsernameContainingIgnoreCase(String search);

    
}
