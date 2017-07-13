package com.smp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FriendsList {
	@Id
	private String friend_Id;
	private String friendsName;
	private int status;
	public String getFriend_Id() {
		return friend_Id;
	}
	public String getFriendsName() {
		return friendsName;
	}
	public int getStatus() {
		return status;
	}
	public void setFriend_Id(String friend_Id) {
		this.friend_Id = friend_Id;
	}
	public void setFriendsName(String friendsName) {
		this.friendsName = friendsName;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}