package com.smp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class HomeFeed {
	@Id
	private String feedId;
	private String commId;
	private String userFeedId;
	private String type;
	private String dateAndTime;
	private String updatedDataKeys;
	private String updatedDataValues;
	
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public String getUserFeedId() {
		return userFeedId;
	}
	public void setUserFeedId(String userFeedId) {
		this.userFeedId = userFeedId;
	}
	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public String getCommId() {
		return commId;
	}
	public void setCommId(String commId) {
		this.commId = commId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUpdatedDataKeys() {
		return updatedDataKeys;
	}
	public void setUpdatedDataKeys(String updatedDataKeys) {
		this.updatedDataKeys = updatedDataKeys;
	}
	public String getUpdatedDataValues() {
		return updatedDataValues;
	}
	public void setUpdatedDataValues(String updatedDataValues) {
		this.updatedDataValues = updatedDataValues;
	}
	
}