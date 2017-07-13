package com.smp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChatNotification {

	@Id
	private String senderId;
	private int chatCount;
	private String dateAndTime;
	
	public String getSenderId() {
		return senderId;
	}
	
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	public int getChatCount() {
		return chatCount;
	}
	
	public void setChatCount(int chatCount) {
		this.chatCount = chatCount;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
}
