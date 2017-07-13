package com.smp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {
	@Id
	private String id;
	private String userId;
	private String userName;
	private String userImg;
	private String comments;
	private String dateAndTime;
	
	//getters
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserImg() {
		return userImg;
	}
	public String getComments() {
		return comments;
	}
	public String getDateAndTime() {
		return dateAndTime;
	}
	
	//setters
	public void setId(String id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
}