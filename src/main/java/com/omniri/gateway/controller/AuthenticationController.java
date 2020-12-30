package com.omniri.gateway.controller;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.omniri.gateway.dto.*;
import com.omniri.gateway.bean.User;
import com.omniri.gateway.repository.UserRepository;
@RestController
public class AuthenticationController {
	
	@Autowired
	FeignClient1 client;
	
	@Autowired
	UserRepository userRepo;
	
	@PostMapping("/authenticate")
	@ResponseBody
	@Transactional
	public Token authenticate(@RequestBody LoginRequest login) {
		Token token= client.token(login);
		User user= new User();
		user.setCreatedDate(new Date());
		user.setId(UUID.randomUUID().toString());
		user.setUsername(login.getUsername());
		user.setToken(token.getToken());
		userRepo.save(user);
		return token;
	}
	
	

}
