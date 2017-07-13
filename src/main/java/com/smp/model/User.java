package com.smp.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
	private String user_Id;
	private String username;
	private String email;
	private String password;
	private String gender;
	private String imgPath;
	private String dob;
	private String state;
	
	public String getUser_Id() {
		return user_Id;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getGender() {
		return gender;
	}
	public String getImgPath() {
		return imgPath;
	}
	public String getDob() {
		return dob;
	}
	public String getState() {
		return state;
	}
	
	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public void setState(String state) {
		this.state = state;
	}
}
