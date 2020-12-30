package com.omniri.gateway.repository;

import org.springframework.data.repository.CrudRepository;


import java.util.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.omniri.gateway.bean.User;

public interface UserRepository extends PagingAndSortingRepository<User, String> {
	
	List<User> findByToken(String token);
	@Query("from User ")
	List<User> findAll();
}
