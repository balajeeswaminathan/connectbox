package com.smp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Like {
	@Id
	private String userId;
	private String userName;
	private String userImg;
	
	//getters
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserImg() {
		return userImg;
	}
	
	//setters
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
}