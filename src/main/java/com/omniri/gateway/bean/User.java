package com.omniri.gateway.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class User {
    
	@Id
	private String id;
	
	private Date createdDate;
	private String username;
	private String token;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
