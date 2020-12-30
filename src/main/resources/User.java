package com.omniri.gateway.bean

@Entity
public class User {
    @Id
	private String id;
	private String username;
	private String token;
	
	
	public String getId() {
		return id;
	}

}
