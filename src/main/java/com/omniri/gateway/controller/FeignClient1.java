package com.omniri.gateway.controller;
 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.omniri.gateway.dto.*;



@FeignClient("USER-SERVICE")
public interface FeignClient1 {

	@PostMapping("/authenticate")
	Token token(LoginRequest loginRequest);
}
