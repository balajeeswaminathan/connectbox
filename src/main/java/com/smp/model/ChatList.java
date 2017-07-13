package com.smp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChatList {

	@Id
	private String chatList_Id;
	private String chatNames;
	private String collectionName;
	private String dateAndTime;
	private String lastMgs;

	public String getChatList_Id() {
		return chatList_Id;
	}
	public String getChatNames() {
		return chatNames;
	}
	public String getCollectionName() {
		return collectionName;
	}
	
	public String getDateAndTime() {
		return dateAndTime;
	}
	public String getLastMgs() {
		return lastMgs;
	}
	
	public void setChatList_Id(String chatList_Id) {
		this.chatList_Id = chatList_Id;
	}
	public void setChatNames(String chatNames) {
		this.chatNames = chatNames;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public void setLastMgs(String lastMgs) {
		this.lastMgs = lastMgs;
	}
}
