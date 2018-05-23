package com.smp.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.NullWriter;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.support.MultipartFilter;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.smp.model.Chat;
import com.smp.model.ChatList;
import com.smp.model.ChatNotification;
import com.smp.model.Feed;
import com.smp.model.Comment;
import com.smp.model.Communities;
import com.smp.model.CommunityLikeOrMember;
import com.smp.model.CommunityList;
import com.smp.model.HomeFeed;
import com.smp.model.Like;
import com.smp.model.LikeOrCmtHomeFeed;
import com.smp.model.FriendsList;
import com.smp.model.Photos;
import com.smp.model.TopicFeed;
import com.smp.model.Topics;
import com.smp.model.User;
import com.smp.model.UserTopics;
import com.smp.service.PropertiesData;
import com.smp.utils.Util;

@Component
public class ChatService {

	public static HashMap onlineMembers = new HashMap();
	public MongoClientURI uri;
	public MongoClient mongoClient;

	DB database = Util.mongoClientInit();
	//define collection name
	public static final String COLLECTION_USER = "user", COLLECTION_CHAT = "chat", COLLECTION_COMMUNITIES = "communities";
	DBCollection userCollName = database.getCollection(COLLECTION_USER);

	//Img System path
	public String sysImgPath = "C:/xampp/htdocs/connectingUs/";
	
	//Img Apache server url
	public String serverImgUrl = "http://localhost/connectingUs/";
	
	//Register
	public String updateUser(String userId, String userName, String email, String password, String dob, String gender, String city, String countryState, String country, String profileImgUrl, boolean isEdit){
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String curentTime = dateFormat.format(date);
		String user_Id;
		JSONObject response = new JSONObject();
		DBObject user = new BasicDBObject();
		if(!database.collectionExists(COLLECTION_USER))
		{
			userCollName = database.createCollection(COLLECTION_USER, null);
		}
		
		if(isEdit)
		{
			/*DBCursor frndsDataCursor;
			String updatedDataKeys = "", updatedDataValues = "", frndId;
			DBObject objDoc;
			DBObject homeFeed = new BasicDBObject();
			DateFormat feedDateFormat = new SimpleDateFormat("HH:mm, MMM dd, yyyy");
			String feedDateAndTime = feedDateFormat.format(date);
			homeFeed.put("dateAndTime", feedDateAndTime);
			homeFeed.put("userFeedId", user_Id);
			homeFeed.put("type", "updateProfile");*/
			DBObject query = new BasicDBObject();
			user_Id = userId;
			
			if(!userName.isEmpty())
			{
				user.put("username", userName);
				//updatedDataKeys += "name|||";
				//updatedDataValues += userName + "|||";
			}
			if(!dob.isEmpty())
			{
				user.put("dob", dob);
				//updatedDataKeys += "Date Of Birth|||";
				//updatedDataValues += dob + "|||";
			}
			if(!gender.isEmpty())
			{
				user.put("gender", gender);
			}
			if(!city.isEmpty())
			{
				user.put("city", city);
			}
			if(!countryState.isEmpty())
			{
				user.put("countryState", countryState);
			}
			if(!country.isEmpty())
			{
				user.put("country", country);
			}
			if(!profileImgUrl.isEmpty())
			{
				user.put("imgPath", profileImgUrl);
				//homeFeed.put("updatedDataKeys", "profile picture");
				//homeFeed.put("updatedDataValues", profileImgUrl);
			}
			
			query.put("user_Id", user_Id);
			userCollName.update(query, user);
			
			/*if(!updatedDataKeys.isEmpty())
			{
				homeFeed.put("updatedDataKeys", updatedDataKeys);
			}
			if(!updatedDataValues.isEmpty())
			{
				homeFeed.put("updatedDataValues", updatedDataValues);
			}
			
			DBCollection frndsCollName = database.getCollection(user_Id + "_FriendsList");
			frndsDataCursor = frndsCollName.find(); //friends list
			while(frndsDataCursor.hasNext())
			{
				objDoc = frndsDataCursor.next();
				frndId = (String) objDoc.get("friend_Id");
				
				DBCollection frndIdCollName = database.getCollection(frndId + "_HomeFeeds");
				frndIdCollName.insert(homeFeed);
			}*/
		}
		else
		{
			user_Id = userName+"_"+curentTime;
			user.put("user_Id", user_Id);
			user.put("userName", userName);
			user.put("email", email);
			user.put("password", password);
			user.put("dob", dob);
			user.put("gender", gender);
			user.put("city", city);
			user.put("countryState", countryState);
			user.put("country", country);
			user.put("state", "inActive");
	
			//Query
			userCollName.insert(user);
		}
		
		response.put("status", 0);
		response.put("user_Id", user_Id);
		response.put("sucessMessage", isEdit ? "User updated sucessfully" : "User registered sucessfully");
	
		return response.toString();
	}
		
	//User Exist
	@SuppressWarnings("unchecked")
	public String login(String email, String password){
		BasicDBObject andQuery = new BasicDBObject();
		JSONObject response = new JSONObject();
		
		//Query
		andQuery.put("email", email);
		andQuery.put("password", password);
		DBObject userExistData = userCollName.findOne(andQuery);

		if(userExistData == null)
		{
			response.put("status", 1);
			response.put("errorMessage", "Email or Password id Incorrect");
		}
		else if(!userExistData.get("state").equals("active"))
		{
			response.put("status", 1);
			response.put("errorMessage", "Your account is inactive now. Kindly check your email to activate your account");
		}
		else
		{
			response.put("status", 0);
			response.put("sucessMessage","Username And Password Is Correct");
			response.put("user_Id", userExistData.get("user_Id"));
			response.put("userName", userExistData.get("username"));
		}

		return response.toString();
	}

	public String emailExist(String email){
		DBObject objRef = new BasicDBObject();
		DBObject resultSet;
		JSONObject response = new JSONObject();
		
		objRef.put("email", email);
		resultSet = userCollName.findOne(objRef);
		try
		{
			String userId = (String) resultSet.get("user_Id");
			response.put("status", 0);
			response.put("userId", userId);
		}
		catch(Exception ex)
		{
			response.put("status", 1);
			response.put("errorMessage", "Your email Id is not registered with us");
		}
		return response.toString();
	}	
	
	//get user data
	public DBObject getUserData(String user_Id){
		BasicDBObject objRef = new BasicDBObject();
		BasicDBObject userDataObj = new BasicDBObject();

		//Query
		objRef.append("user_Id", user_Id);
		DBObject userData = userCollName.findOne(objRef);
		userDataObj.put("user_Id", userData.get("user_Id"));
		userDataObj.put("userName", userData.get("userName"));
		userDataObj.put("gender", userData.get("gender"));
		userDataObj.put("imgPath", userData.get("imgPath"));
		
		return userDataObj;
	}
	
	//Home call
	public String getUserBasicInfo(String user_Id) {
		JSONObject response = new JSONObject();
		DBObject userDbObj = getUserData(user_Id);
		
		response.put("userName", userDbObj.get("userName"));
		response.put("imgPath", userDbObj.get("imgPath"));
		response.put("gender", userDbObj.get("gender"));
		
		return response.toString();
	}
	
	

	public void activateUser(String userId)
	{
		DBObject query = new BasicDBObject();
		DBObject update = new BasicDBObject();
		query.put("user_Id", userId);
		update.put("state", "active");
		
		userCollName.update(query, update);
	}
		
	public String searchList(String searchTerm){
		JSONObject searchJson = new JSONObject();

		JSONObject sLUsersData = searchListUser(searchTerm);
		//JSONObject sLTopicsData = searchListTopics(searchTerm);
		searchJson.putAll(sLUsersData);
		//searchJson.putAll(sLTopicsData);
		if(searchJson.isEmpty())
		{
			searchJson.put("errorMessage", "We couldn't find anything for \"" + searchTerm + "\"");
		}
		
		return searchJson.toString();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject searchListUser(String userName) {
		BasicDBObject query = new BasicDBObject();
		List<JSONObject> userSearchList = new ArrayList<JSONObject> ();
		JSONObject UserSearchJson = new JSONObject();
		String imgUrl;

		//Query
		query.put("userName", java.util.regex.Pattern.compile(userName.toString()));
		DBCursor searchListUser = userCollName.find(query);
		
		if(searchListUser.size() > 0)
		{
			while(searchListUser.hasNext())
			{
				DBObject user = searchListUser.next();
				JSONObject UserSearchDataJson = new JSONObject();
				UserSearchDataJson.put("userId", user.get("user_Id"));
				UserSearchDataJson.put("userName", user.get("userName"));
				UserSearchDataJson.put("gender", user.get("gender"));
				imgUrl = (String) user.get("imgPath");
				if(imgUrl != "")
				{
					UserSearchDataJson.put("userImgUrl", imgUrl);
				}
				userSearchList.add(UserSearchDataJson);
			}
			UserSearchJson.put("userSearchList", userSearchList);
		}
		
		return UserSearchJson;
	}

	@SuppressWarnings("unchecked")
	/*public JSONObject searchListTopics(String topicName) {
		Query query = new Query();
		List<JSONObject> topicSearchList = new ArrayList<JSONObject> ();
		JSONObject topicSearchJson = new JSONObject();
		String imgUrl;

		//Query
		//query.limit(10);
		query.addCriteria(Criteria.where("name").regex(topicName));
		List<Topics> searchListTopic = topicName.find(query);
		
		if(searchListTopic.size() > 0)
		{
			for(int i = 0;i < searchListTopic.size(); i++)
			{
				JSONObject UserSearchDataJson = new JSONObject();
				UserSearchDataJson.put("topicId", searchListTopic.get(i).getId());
				UserSearchDataJson.put("topicName", searchListTopic.get(i).getName());
				UserSearchDataJson.put("userId", searchListTopic.get(i).getUserId());
				imgUrl = searchListTopic.get(i).getImgUrl();
				if(imgUrl != "")
				{
					UserSearchDataJson.put("topicImgUrl", imgUrl);
				}
				topicSearchList.add(UserSearchDataJson);
			}
			topicSearchJson.put("topicSearchList", topicSearchList);
		}
		
		return topicSearchJson;
	}*/
	
	//Add Friend
	public String addFriends(String collName, String friendId, int status) {
		JSONObject response = new JSONObject();
		DBObject query = new BasicDBObject();
		DBCollection collection = database.getCollection(collName);
		DBObject frndObj = new BasicDBObject();
		if(status != 0)
		{
			frndObj.put("friend_Id", friendId);
			frndObj.put("status", status);
			collection.insert(frndObj);
			response.put("sucessMessage", "Friend request send successfully");
		}
		else
		{
			frndObj.put("status", status);			
			query.put("friend_Id", friendId);
			collection.update(query, frndObj);
			response.put("sucessMessage","Friend accept send successfully");
		}
		response.put("status", 0);
		
		return response.toString();
	}

	//Get Chat Data Collection Name
	public String getChatListCollName(String senderId, String receiverId){
		DBCollection senderCollName = database.getCollection(senderId+"_ChatList");
		BasicDBObject objRef = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject();
		String chatCollName = null;

		//Query
		objRef.put("chatList_Id", receiverId);
		fields.put("collectionName", 1);
		DBObject resultSet = senderCollName.findOne(objRef, fields);

		try
		{
			chatCollName = (String) resultSet.get("collectionName");
		}
		catch(Exception e)
		{
			
		}
		
		return chatCollName;
	}
	
	//Get Chat Data
	public String getChatListData(String senderId, String receiverId, int pageLevel,int limits)
	{
		String chatCollName = getChatListCollName(senderId, receiverId);
		String getChatData =  getChatData(senderId, receiverId, chatCollName, pageLevel,limits);
		
		return getChatData;
	}
	
	//Save Chat
	@SuppressWarnings("unchecked")
	public String saveChat(String senderId, String senderName, String receiverId, String receiverName, String message, String commType){
		String chatCollName = getChatListCollName(senderId, receiverId);
		Date date = new Date();
		
		DBCollection senderColl = database.getCollection(senderId +"_ChatList");
		DBCollection receiverColl = database.getCollection(receiverId +"_ChatList");

		DateFormat dateFormat = new SimpleDateFormat("HH:mm, MMM dd, yyyy");
		DateFormat chatDataDateFormat = new SimpleDateFormat("MMM dd, yyyy");
		DateFormat chatDataTimeFormat = new SimpleDateFormat("HH:mm");
		
		if(chatCollName == null)
		{
			//Query
			DBObject senderChatList = new BasicDBObject();
			DBObject receiverChatList = new BasicDBObject();
			
			chatCollName = senderId + "_" + receiverId + "_chats";
			
			senderChatList.put("chatList_Id", receiverId);
			senderChatList.put("chatNames", receiverName);
			senderChatList.put("collectionName", chatCollName);
			senderChatList.put("dateAndTime", dateFormat.format(date));
			senderChatList.put("lastMgs", message);
			
			receiverChatList.put("chatList_Id", senderId);
			receiverChatList.put("chatNames", senderName);
			receiverChatList.put("collectionName", chatCollName);
			receiverChatList.put("dateAndTime", dateFormat.format(date));
			receiverChatList.put("lastMgs", message);
			
			senderColl.insert(senderChatList);
			receiverColl.insert(receiverChatList);
		}
		else
		{
			DBObject update = new BasicDBObject();
			DBObject updateSet = new BasicDBObject();
			DBObject query = new BasicDBObject();
			DBObject query1 = new BasicDBObject();
			//Query
			update.put("dateAndTime", dateFormat.format(date));
			update.put("lastMgs", message);
			updateSet.put("$set", update);
			
			query.put("chatList_Id", receiverId);
			senderColl.update(query, updateSet);
			
			query1.put("chatList_Id", senderId);
			receiverColl.update(query1, updateSet);
		}
		DBCollection chatColl = database.getCollection(chatCollName);
		DBObject chat = new BasicDBObject();
		
		chat.put("ChatListId", UUID.randomUUID().toString());
		chat.put("senderId", senderId);
		chat.put("message", message);
		chat.put("date", chatDataDateFormat.format(date));
		chat.put("time", chatDataTimeFormat.format(date));
		chat.put("senderId", senderId);
		chat.put("commType", commType);
		
		chatColl.insert(chat);
		setChatNotification(senderId, receiverId, dateFormat.format(date));
		
		JSONObject response = new JSONObject();
		response.put("isAdded", true);
		return response.toString();
	}
	
	//Set Chat Notification
	public void setChatNotification(String senderId, String receiverId, String dateAndTime)
	{
		DBObject chatNotifData;
		String chatNotifSenderId;
		int chatCount,userExist = 0;
		DBObject chatNotifObj = new BasicDBObject();
		DBObject query = new BasicDBObject();

		//Query
		DBCollection chatNotifCollName = database.getCollection(receiverId + "_ChatNotification");
		DBCursor chatNotifDataCursor = chatNotifCollName.find();
		
		while(chatNotifDataCursor.hasNext())
		{
			chatNotifData = chatNotifDataCursor.next();
			chatNotifSenderId = (String) chatNotifData.get("senderId");
			if(senderId.equals(chatNotifSenderId))
			{
				chatCount = (Integer) chatNotifData.get("chatCount");
				chatCount = chatCount + 1;
				chatNotifObj.put("chatCount", chatCount);
				chatNotifObj.put("dateAndTime", dateAndTime);
				query.put("senderId", senderId);
				chatNotifCollName.update(query, chatNotifObj);
				userExist = 1;
				break;
			}
		}
		if(userExist == 0)
		{
			chatNotifObj.put("senderId", senderId);
			chatNotifObj.put("chatCount", 1);
			chatNotifObj.put("dateAndTime", dateAndTime);
			
			chatNotifCollName.insert(chatNotifObj);
		}
	}
	
	//get Chat Notification Count
	public int getChatNotification(String userId)
	{
		int messageCount = 0;
		
		//Query
		DBCollection chatNotifCollName = database.getCollection(userId + "_ChatNotification");
		DBCursor chatNotifDataCursor = chatNotifCollName.find();
		while(chatNotifDataCursor.hasNext())
		{
			messageCount += (Integer) chatNotifDataCursor.next().get("chatCount");
		}
		
		return messageCount;
	}
	
	public int removeChatNotification(String userId, String senderId)
	{
		BasicDBObject docObjRef = new BasicDBObject();

		//Query
		DBCollection chatNotifCollName = database.getCollection(userId + "_ChatNotification");
		docObjRef.append("senderId", senderId);
		
		chatNotifCollName.remove(docObjRef);
		
		return getChatNotification(userId);
	}
	
	//Get Chat List need to sort
	@SuppressWarnings("unchecked")
	public String getChatListData(String senderId, String clientTZ) throws ParseException
	{
		JSONObject chatListJson = new JSONObject();
		JSONObject chatListResponseJson = new JSONObject();
		List<JSONObject> newL = new ArrayList<JSONObject>();
		DBCollection chatNotifColl = database.getCollection(senderId + "_ChatNotification");
		DBCollection chatDataColl = database.getCollection(senderId + "_ChatList");
		DBObject orderBy = new BasicDBObject();
		
		orderBy.put("dateAndTime", -1);
		
		DBCursor chatNotifDataList = chatNotifColl.find().sort(orderBy);
		DBCursor chatListDataList = chatDataColl.find().sort(orderBy);
		
		int chatListSize = chatListDataList.size();
		if(chatListSize > 0)
		{
			for(int i = 0, j = 0 ;i < chatListSize; i++)
			{
				DBObject chatNotifData = chatNotifDataList.hasNext() ? chatNotifDataList.next() : new BasicDBObject();
				DBObject chatListData = chatListDataList.next();
				if(j < chatNotifDataList.size())
				{
					if(chatNotifData.get("senderId").toString().equals(chatListData.get("chatList_Id").toString()))
					{
						chatListJson.put("newMsg", true);
						chatListJson.put("newMsgCount", chatNotifData.get("chatCount"));
					}
					j++;
				}
				chatListJson.put("chatListId", chatListData.get("chatList_Id"));
				chatListJson.put("chatNames", chatListData.get("chatNames"));
				chatListJson.put("dateAndTime", getDateFormat(chatListData.get("dateAndTime").toString(), clientTZ));
				chatListJson.put("lastMgs", chatListData.get("lastMgs"));
				newL.add(chatListJson);
			}
			chatListResponseJson.put("status", 0);
		}
		else
		{
			chatListResponseJson.put("status", 1);
		}
		
		chatListResponseJson.put("chats", newL);
		
		return chatListResponseJson.toString();
	}
	
	//Get Chat Data
	@SuppressWarnings("unchecked")
	public String getChatData(String senderId, String receiverId, String chatCollName, int pageLevel, int limits)
	{
		DBObject chatData;
		DBObject chatDataObj = new BasicDBObject();
		String senderID, dateTemp = "", commType;
		JSONObject chatDataresponseJson = new JSONObject();
		List<DBObject> chatDataList = new ArrayList<DBObject>();
		List chatDataDataList;
		int skipLevel, chatDataDataListCount;
		String previousDate = "";
		
		Date date = new Date();
		DateFormat chatDataDateFormat = new SimpleDateFormat("MMM dd, yyyy");
		
		DBObject receiverData = getUserData(receiverId);
		chatDataresponseJson.putAll((Map) receiverData);
		try
		{
			Pagination page = new Pagination();
			
			//Query
			skipLevel = page.getSkipLevel(chatCollName, pageLevel, limits);
			if(skipLevel < 0)
			{
				limits = page.getLimits(skipLevel, limits);
				skipLevel = 0;
			}
			chatDataDataList = page.getPaginationData(chatCollName, false, skipLevel, limits);
			chatDataDataListCount = chatDataDataList.size();
			
			for(int i = 0; i < chatDataDataListCount; i++)
			{
				chatData = (DBObject) chatDataDataList.get(i);
				senderID = (String) chatData.get("senderId");
				dateTemp = (String) chatData.get("date");
				commType = (String) chatData.get("commType");
				
				if(!previousDate.equals(dateTemp))
				{
					String todayDate = chatDataDateFormat.format(date);
					chatDataObj.put("senderId", senderID);
					if(dateTemp.equals(todayDate))
					{
						chatDataObj.put("date", "Today");
					}
					else
					{
						chatDataObj.put("date", dateTemp);
					}
				}
				if(senderID.equals(senderId))
				{
					chatDataObj.put("sender", true);
				}
				if(!commType.equals(""))
				{
					chatDataObj.put("commType", commType);
				}
				previousDate = dateTemp;
				chatDataObj.put("message", chatData.get("message"));
				chatDataObj.put("time", chatData.get("time"));
				chatDataList.add(chatDataObj);
				chatDataObj = new BasicDBObject();
			}
			chatDataresponseJson.put("chatList", chatDataList);
			if(skipLevel == 0)
			{
				chatDataresponseJson.put("isLast", true);
			}
			chatDataresponseJson.put("limits", limits);
		}
		catch(Exception e)
		{
			chatDataresponseJson.put("isEmpty", "true");
		}
		
		return chatDataresponseJson.toString();
	}
	
	//Friends List
	@SuppressWarnings("unchecked")
	public String getFriendsList(String collName, int friendsType, boolean isOnline) throws ParseException {
		int status;
		DBObject friendData, userData;
		DBObject friendDataObj = new BasicDBObject();
		List <DBObject> frndsList = new ArrayList <DBObject>();
		JSONObject frndsDataJson = new JSONObject();
		String userId;
		boolean isOnlineMember = false;
		
		DBCollection frndsListCollName = database.getCollection(collName);
		DBCursor frndsDocCursor = frndsListCollName.find();
		
		while(frndsDocCursor.hasNext())
		{
			//Query
			friendData = frndsDocCursor.next();
			userId = (String) friendData.get("friend_Id");
			status = (Integer) friendData.get("status");
			friendDataObj.put("status", status);
			if(isOnline)
			{
				ArrayList<String> userList = new ArrayList<String>();
				userList.add(userId);
				isOnlineMember = isMemberOnline(userId);
			}
			if(isOnlineMember || !isOnline)
			{
				if(status == friendsType)
				{
					userData = getUserData(userId);
					friendDataObj.putAll(userData);
					frndsList.add(friendDataObj);
				}
			}
			isOnlineMember = false;
		}
		frndsDataJson.put("friendsList",frndsList);
		return frndsDataJson.toString();
	}

	public DBObject profileData(String profileId)
	{
		BasicDBObject objRef = new BasicDBObject();

		//Query
		objRef.append("user_Id", profileId);
		DBObject profileData = userCollName.findOne(objRef);
		return profileData;
	}
	
	public static int getAge(Date dateOfBirth) {

	    Calendar today = Calendar.getInstance();
	    Calendar birthDate = Calendar.getInstance();

	    int age = 0;

	    birthDate.setTime(dateOfBirth);
	    if (birthDate.after(today)) {
	        throw new IllegalArgumentException("Can't be born in the future");
	    }

	    age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

	    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year   
	    if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
	            (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
	        age--;

	     // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
	    }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
	              (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
	        age--;
	    }

	    return age;
	}
	
	//Check Friend Status
	@SuppressWarnings("unchecked")
	public String profileCall(String userId, String profileId) throws ParseException {
		BasicDBObject objRef = new BasicDBObject();
		JSONObject responseJson = new JSONObject();
		JSONObject profileDataJson = new JSONObject();
		
		//Query
		DBObject profileData = profileData(profileId);
		String dob = (String) profileData.get("dob");
		
		responseJson.put("profileId", profileId);
		responseJson.put("profileName", profileData.get("userame"));
		responseJson.put("profileImgUrl", profileData.get("imgPath"));
		responseJson.put("gender", profileData.get("gender"));
		responseJson.put("dob", dob);
		responseJson.put("country", profileData.get("country"));
		responseJson.put("countryState", profileData.get("countryState"));
		responseJson.put("city", profileData.get("city"));
		
		if(dob != null)
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int age = getAge(dateFormat.parse(dob));
			responseJson.put("age", age);
		}

		if(!userId.equals(profileId))
		{
			objRef.append("friend_Id", profileId);
			DBCollection frndsCollName = database.getCollection(userId+"_FriendsList");
			DBObject frndsData = frndsCollName.findOne(objRef);
			if(frndsData != null)
			{
				responseJson.put("friendStatus", frndsData.get("status"));
			}
			else
			{
				responseJson.put("friendStatus", null);
			}
		}
		else
		{
			responseJson.put("myProfile", true);
		}

		profileDataJson.put("profileDataList", responseJson);
		return profileDataJson.toString();
	}
	
	//Upload Image
	public String uploadImage(byte[] imgBytes) throws IOException {
		
		/*PropertiesData propData = new PropertiesData();
		propData.initProp();
		sysImgPath = propData.getProp("sysImgPath");
		
		File outerFile = new File(sysImgPath + userId);
		if (!outerFile.exists() && !outerFile.isDirectory())
		{
			new File(sysImgPath + userId).mkdirs();
		}
		
		File innerFile = new File(sysImgPath + userId + "/" + imgType);
		if (!innerFile.exists() && !innerFile.isDirectory())
		{
			new File(sysImgPath + userId + "/" + imgType).mkdirs();
		}
		
		String  imgPath = sysImgPath + userId + "/" + imgType + "/" + imgId + ".jpg";
		String  imgUrl = serverImgUrl + userId + "/" + imgType + "/" + imgId + ".jpg";
		
		//compressImage(imgBytes, imgPath);
		// convert byte array back to BufferedImage
		/*InputStream inputStream = new ByteArrayInputStream(imgBytes);
     	BufferedImage imgBufferedData = ImageIO.read(inputStream);
     	
     	ImageIO.write(imgBufferedData, "jpg", new File(imgPath));*/
     	
		imgBytes = compressImage(imgBytes);
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				  "cloud_name", "connectbox",
				  "api_key", "778358248731998",
				  "api_secret", "jYfN6PxoUSwvATvv5i42udD-q9c"));
		Map uploadResult = cloudinary.uploader().upload(imgBytes, ObjectUtils.emptyMap());
		
     	return (String) uploadResult.get("url");
	}

	public byte[] compressImage(byte[] imgBytes) throws IOException
	{
		/*ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);
		BufferedImage image = ImageIO.read(bais);
		
		File compressedImageFile = new File(imgPath);
	    OutputStream os =new FileOutputStream(compressedImageFile);

	    Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
	    ImageWriter writer = (ImageWriter) writers.next();

	    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
	    writer.setOutput(ios);

	    ImageWriteParam param = writer.getDefaultWriteParam();
	    
	    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	    param.setCompressionQuality(0.5f);
	    writer.write(null, new IIOImage(image, null, null), param);*/
	    
		ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);
		BufferedImage image = ImageIO.read(bais);
	    ByteArrayOutputStream compressed = new ByteArrayOutputStream();
	    ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);

	    ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();

	    // Configure JPEG compression: 70% quality
	    ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
	    jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	    jpgWriteParam.setCompressionQuality(0.7f);

	    // Set your in-memory stream as the output
	    jpgWriter.setOutput(outputStream);
	    jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);

	    // Dispose the writer to free resources
	    jpgWriter.dispose();

	    // Get data for further processing...
	    byte[] jpegData = compressed.toByteArray();
	    
	    return jpegData;
	}
	
	public long findCollectionCount(String collName)
	{
		try
		{
			DBCollection coll = database.getCollection(collName);
			return coll.count();
		}
		catch(Exception ex)
		{
			return 0;
		}
	}
	
	public boolean dataExist(String userId, String collName)
	{
		
		DBObject objRef = new BasicDBObject();
		objRef.put("user_Id", userId);
		DBCollection collection = database.getCollection(collName);

		DBObject likedDataObj = collection.findOne(objRef);
		try
		{
			String likeUserId = (String) likedDataObj.get("user_Id");
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	public String getLikeOrCmnts(String uerId, String id, String type, String clientTZ) throws ParseException{
		List dataList = new ArrayList();
		JSONObject dataJson = new JSONObject();
		DBObject data;
		String collName = null;
		if(type.equals("like"))
		{
			collName = id + "_LikedList";
		}
		else if(type.equals("comments"))
		{
			collName = id + "_CommentList";
		}
		
		//Query
		DBCollection collection = database.getCollection(collName);
		DBCursor resultSet = collection.find();
		
		while(resultSet.hasNext())
		{
			data = resultSet.next();
			if(type.equals("comments"))
			{
				data.put("dateAndTime", getDateFormat((String) data.get("dateAndTime"), clientTZ));
			}
			dataList.add(data);
		}
		dataJson.put("likeOrCmntsList", dataList);
		
		return dataJson.toString();
	}
	
	public void addPhotos(String userId, String desc, String imgUrl)
	{
		DBObject objDoc;
		String frndId;
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String photoId = userId + "_photo_" + dateFormat.format(date);
		DateFormat photodDateFormat = new SimpleDateFormat("HH:mm, MMM dd, yyyy");
		String photoDateAndTime = photodDateFormat.format(date);

		DBObject photoObj = new BasicDBObject();
		photoObj.put("photo_Id", photoId);
		photoObj.put("desc", desc);
		photoObj.put("imgUrl", imgUrl);
		photoObj.put("dateAndTime", photoDateAndTime);
		
		//Query
		DBCollection collection = database.getCollection(userId+"_Photos");
		collection.insert(photoObj);
		
		DBCollection frndsCollName = database.getCollection(userId + "_FriendsList");
		
		DBObject homeFeedObj = new BasicDBObject();
		homeFeedObj.put("feedId", photoId);
		homeFeedObj.put("userFeedId", userId);
		homeFeedObj.put("dateAndTime", photoDateAndTime);
		homeFeedObj.put("type", "photo");
		
		DBCursor frndsDataCursor = frndsCollName.find();
		while(frndsDataCursor.hasNext())
		{
			objDoc = frndsDataCursor.next();
			frndId = (String) objDoc.get("friend_Id");

			DBCollection frndCollName = database.getCollection(frndId + "_HomeFeeds");
			frndCollName.insert(homeFeedObj);
		}
	}
	
	public String getPhotos(String userId, String profileId, String photosCollName, String clientTZ) throws ParseException
	{
		List photosList = new ArrayList();
		JSONObject photoData = new JSONObject();
		DBObject photo;
		DBObject photoObj = new BasicDBObject();
		String photoId;
		long likeCount, cmntsCount;
		boolean isLiked;
		
		//Query
		DBCollection collection = database.getCollection(photosCollName);
		DBCursor resultSet = collection.find();
		
		while(resultSet.hasNext())
		{
			photo = resultSet.next();

			photoId = (String) photo.get("photo_Id");
			likeCount = findCollectionCount(photoId + "_LikedList");
			cmntsCount = findCollectionCount(photoId + "_CommentList");
			isLiked = dataExist(userId, photoId + "_LikedList");
			
			photoObj.put("photo_Id", photoId);
			if(isLiked)
			{
				photoObj.put("isLiked", true);
			}
			photoObj.put("dateAndTime", getDateFormat((String)photo.get("dateAndTime"), clientTZ));
			photoObj.put("likeCount", likeCount);
			photoObj.put("cmntsCount", cmntsCount);
			photoObj.put("desc", photo.get("desc"));
			photoObj.put("imgUrl", photo.get("imgUrl"));
			
			photosList.add(photoObj);
		}
		photoData.put("photosList", photosList);

		return photoData.toString();
	}
	
	/*public void addTopic(String topicName, String imgUrl, String userId)
	{
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String topicId = topicName + "_topic_" + dateFormat.format(date);
		
		Topics topic = new Topics();
		topic.setId(topicId);
		topic.setName(topicName);
		topic.setUserId(userId);
		topic .setImgUrl(imgUrl);

		addUserTopic(userId, topicId, "owner");
		
		//Query
		mongoTemplate.insert(topic, "topics");
	}
	
	public void addUserTopic(String userId, String topicId, String type)
	{
		UserTopics userTopics = new UserTopics();
		userTopics.setId(topicId);
		userTopics.setType(type);
		
		//Query
		mongoTemplate.insert(userTopics, userId+"_topics");
	}
	
	public String getTopics(String userId, String topicType){
		String topicId, userTopicsId;
		BasicDBObject obj = new BasicDBObject();
		DBCursor topicsResult;
		DBObject topic;
		List topicList = new ArrayList();
		JSONObject topicsData = new JSONObject();

		Query query = new Query(Criteria.where("type").is(topicType));
		List<UserTopics> userTopicsResult = mongoTemplate.find(query, UserTopics.class, userId+"_topics");
		DBCollection topicsColl = mongoTemplate.getCollection("topics");
		topicsResult = topicsColl.find();
		
		if(userTopicsResult.size() > 0)
		{
			while(topicsResult.hasNext())
			{
				topic = topicsResult.next();
				topicId = (String) topic.get("_id");
				for(int i = 0 ; i < userTopicsResult.size() ; i++)
				{
					userTopicsId = userTopicsResult.get(i).getId();
					if(userTopicsId.equals(topicId))
					{
						topicList.add(topic);
						break;
					}
				}
			}
		}
		topicsData.put("topicsList", topicList);
		
		return topicsData.toString();
	}
	
	public DBObject getTopic(String topicId){
		DBCollection topicColl = mongoTemplate.getCollection("topics");
		BasicDBObject topicIdRef = new BasicDBObject();
		DBObject resultSet;
		
		topicIdRef.append("_id", topicId);
		resultSet = topicColl.findOne(topicIdRef);
		
		return resultSet;
	}
	
	public void addTopicFeed(String topicId, String userId, String feedDesc, String feedImgUrl){
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String feedId = topicId + "_TopicFeed_" + dateFormat.format(date);
		DateFormat feedDateFormat = new SimpleDateFormat("HH:mm, MMM dd, yyyy");
		String feedDateAndTime = feedDateFormat.format(date);
		
		TopicFeed topicFeed = new TopicFeed();
		topicFeed.setId(feedId);
		topicFeed.setUserId(userId);
		topicFeed.setDesc(feedDesc);
		topicFeed.setImg(feedImgUrl);
		topicFeed.setDateAndTime(feedDateAndTime);
		
		//Query
		mongoTemplate.insert(topicFeed, topicId+"_Feed");
	}
	
	public String getTopicFeed(String feedCollName){
		List feedList = new ArrayList();
		JSONObject feedData = new JSONObject();
		DBObject feed;
		
		//Query
		DBCollection collection = mongoTemplate.getCollection(feedCollName);
		DBCursor resultSet = collection.find();
		
		while(resultSet.hasNext())
		{
			feed = resultSet.next();
			String img = (String) feed.get("imgUrl");
			if(img.isEmpty())
			{
				feed.removeField("imgUrl");
			}
			feedList.add(feed);
		}
		feedData.put("feedList", feedList);
		
		return feedData.toString();
	}*/

	public boolean checkPsw(String userId, String password)
	{
		List<BasicDBObject> objRefList = new ArrayList<BasicDBObject>();
		BasicDBObject andQuery = new BasicDBObject();
		String email = null;
		
		objRefList.add(new BasicDBObject("user_Id", userId));
		objRefList.add(new BasicDBObject("password", password));
		andQuery.put("$and", objRefList);
		DBObject pswExist = userCollName.findOne(andQuery);
		if(pswExist != null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public int generateOTP(int limit)
	{
		Random random = new Random();
		int OTP = random.nextInt(limit);
		return OTP;
	}
	
	public void setSession(HttpSession session, String attr, int value, int timeOut)
	{
		session.setAttribute(attr, value);
		session.setMaxInactiveInterval(timeOut);
	}
	
	public String setPassword(HttpSession session, String userId, String password, int OTP)
	{
		DBCollection collection = database.getCollection(COLLECTION_USER);
		JSONObject response = new JSONObject();
		DBObject query = new BasicDBObject();
		DBObject pswObj = new BasicDBObject();
		try
		{
			int OTPResult = (Integer) session.getAttribute(userId);
			if(OTPResult == OTP)
			{
				query.put("user_Id", userId);
				pswObj.put("password", password);
				collection.update(query, pswObj);
				response.put("status", 0);
			}
			else
			{
				response.put("status", 1);
			}
		}
		catch(Exception ex)
		{
			response.put("status", 2);
		}
		return response.toString();
	}
	
	public String resetPassword(HttpSession session, String userId, String password) throws MessagingException
	{
		int OTP = generateOTP(100000);
		setSession(session, userId, OTP, 2*60);
		JSONObject response = new JSONObject();
		boolean status = checkPsw(userId, password);
		if(status)
		{
			String mailDom, email;
			response.put("status", 0);
			mailDom = "<div class='connUs-mail-cont'>"+
					"<div class='connUs-header-cont'style='height: 50px;background-color: #616161;padding: 7px'>"+
					"<div class='connUs-header-label' style='font-weight: 600;font-size: 23px;width: 200px;margin: 0 auto;color: #fff;'>Connecting Us</div></div>"+
					"<h2>Reset Password OTP !</h2>"+
					"<div class='connUs-mailContent-cont'>Please give below OTP in the OTP feild,It will get expired in 2 mins</div>"+
					"<div class='connUs-mailContent-cont'>" + OTP + "</div>"+
					"</div>";
			String mailSubject = "OTP for Reset Password";

			List<BasicDBObject> objRefList = new ArrayList<BasicDBObject>();
			BasicDBObject andQuery = new BasicDBObject();
			
			
			objRefList.add(new BasicDBObject("user_Id", userId));
			andQuery.put("$and", objRefList);
			DBObject emailData = userCollName.findOne(andQuery);
			
			email = (String) emailData.get("email");
			MailSend mailSend = new MailSend();
			mailSend.sendMail(email, mailSubject, mailDom);
		}
		else
		{
			response.put("status", 1);
		}
		
		return response.toString();
	}
	
	/*public void updateCommunities(String commId, String name, String coverImg, String img, String address, String phNo, String email, String ownerId, boolean isEdit){
		DBCollection frndsCollName, membersCollName;
		DBObject objDoc;
		String frndId, updatedDataKeys = "", updatedDataValues = "";
		Date date = new Date();
		DBCursor frndsDataCursor, membersDataCursor;
		HomeFeed homeFeed = new HomeFeed();

		frndsCollName = mongoTemplate.getCollection(ownerId + "_FriendsList");
		DateFormat feedDateFormat = new SimpleDateFormat("HH:mm, MMM dd, yyyy");
		String feedDateAndTime = feedDateFormat.format(date);

		homeFeed.setDateAndTime(feedDateAndTime);
		homeFeed.setUserFeedId(ownerId);

		if(!isEdit)
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			String id = name + dateFormat.format(date);
			
			Communities comm = new Communities();
			comm.setId(id);
			comm.setName(name);
			comm.setCoverImg(coverImg);
			comm.setCoverImg(coverImg);
			comm.setImg(img);
			comm.setAddress(address);
			comm.setPhNo(phNo);
			comm.setEmail(email);
			comm.setOwnerId(ownerId);
			
			//Query
			mongoTemplate.insert(comm, COLLECTION_COMMUNITIES);
			setCommunityList(id, ownerId, "owned");
			homeFeed.setCommId(id);
			homeFeed.setType("createCommunity");
		}
		else
		{
			homeFeed.setCommId(commId);
			homeFeed.setType("updateCommunity");
			membersCollName = mongoTemplate.getCollection(commId + "_MemberList");

			Update update = new Update();
			Query query = new Query();
			Query query1 = new Query();
			//Query
			if(!name.isEmpty())
			{
				update.set("name", name);
				updatedDataKeys += "name|||";
				updatedDataValues += name + "|||";
			}
			if(!coverImg.isEmpty())
			{
				update.set("coverImg", coverImg);
				homeFeed.setUpdatedDataKeys("cover picture");
				homeFeed.setUpdatedDataValues(coverImg);
			}
			if(!img.isEmpty())
			{
				update.set("img", img);
				homeFeed.setUpdatedDataKeys("picture");
				homeFeed.setUpdatedDataValues(img);
			}
			if(!address.isEmpty())
			{
				update.set("address", address);
				updatedDataKeys += "address|||";
				updatedDataValues += address + "|||";
			}
			if(!phNo.isEmpty())
			{
				update.set("phNo", phNo);
				updatedDataKeys += "phone number|||";
				updatedDataValues += phNo + "|||";
			}
			if(!email.isEmpty())
			{
				update.set("email", email);
				updatedDataKeys += "email|||";
				updatedDataValues += email + "|||";
			}
			
			query.addCriteria(Criteria.where("_id").is(commId));
			mongoTemplate.updateFirst(query, update, COLLECTION_COMMUNITIES);

			if(!updatedDataKeys.isEmpty())
			{
				homeFeed.setUpdatedDataKeys(updatedDataKeys);
			}
			if(!updatedDataValues.isEmpty())
			{
				homeFeed.setUpdatedDataValues(updatedDataValues);
			}

			membersDataCursor = membersCollName.find(); //members list
			while(membersDataCursor.hasNext())
			{
				objDoc = membersDataCursor.next();
				frndId = (String) objDoc.get("_id");

				mongoTemplate.insert(homeFeed, frndId + "_HomeFeeds");
			}
		}

		frndsDataCursor = frndsCollName.find(); //friends list
		while(frndsDataCursor.hasNext())
		{
			objDoc = frndsDataCursor.next();
			frndId = (String) objDoc.get("_id");

			mongoTemplate.insert(homeFeed, frndId + "_HomeFeeds");
		}
	}
	
	public void setCommunityList(String commId, String userId, String type)
	{
		if(type.equals("removeMember"))
		{
			BasicDBObject objRef = new BasicDBObject();
			DBCollection userCommListCollName = mongoTemplate.getCollection(userId + "_CommunityList");
			objRef.append("commId", commId);
			objRef.append("type", "member");
			
			userCommListCollName.remove(objRef);
		}
		else
		{
			CommunityList cl = new CommunityList();
			
			cl.setId(UUID.randomUUID().toString());
			cl.setCommId(commId);
			cl.setType(type);
			
			mongoTemplate.insert(cl, userId + "_CommunityList");
		}
	}
	
	public void giveLikeOrMemberCommunity(String commId, String userId, String type)
	{
		CommunityLikeOrMember commLikeOrMember = new CommunityLikeOrMember();
		BasicDBObject objRef = new BasicDBObject();
		
		setCommunityList(commId, userId, type);
		commLikeOrMember.setUserId(userId);
		if(type.equals("liked"))
		{
			mongoTemplate.insert(commLikeOrMember, commId + "_LikedList");
		}
		else if(type.equals("member"))
		{
			mongoTemplate.insert(commLikeOrMember, commId + "_MemberList");
		}
		else if(type.equals("removeMember"))
		{
			DBCollection commMemberListCollName = mongoTemplate.getCollection(commId + "_MemberList");
			objRef.append("_id", userId);
			
			commMemberListCollName.remove(objRef);
		}
	}
	
	public boolean checkCommunityList(String commId, String userId, String type)
	{
		DBCollection userCommListCollName = mongoTemplate.getCollection(userId + "_CommunityList");
		BasicDBObject objRef = new BasicDBObject();
		
		objRef.append("commId", commId);
		objRef.append("type", type);
		DBObject commListData = userCommListCollName.findOne(objRef);
		
		if(commListData != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getCommunityList(String userId, String type)
	{
		String commId;
		DBCollection userCommListCollName = mongoTemplate.getCollection(userId + "_CommunityList");
		BasicDBObject objRef = new BasicDBObject();
		DBObject commObj;
		List commList = new ArrayList();
		JSONObject commData = new JSONObject();
		JSONObject commDataJson = new JSONObject();
		
		objRef.append("type", type);
		DBCursor commDataCur = userCommListCollName.find(objRef);
		
		while(commDataCur.hasNext())
		{
			commObj = commDataCur.next();
			commId = (String) commObj.get("commId");
			commData = getCommunity(commId, userId);
			commList.add(commData);
		}
		
		commDataJson.put("communityList", commList);
		
		return commDataJson.toString();
	}

	public JSONObject getCommunity(String commId, String userId)
	{
		DBCollection commCollName = mongoTemplate.getCollection(COLLECTION_COMMUNITIES);
		BasicDBObject objRef = new BasicDBObject();
		String communityId, name, coverImg, img, address, phNo, email, ownerId;
		JSONObject jsonObj = new JSONObject();
		long likeCount, memberCount;
		boolean isLiked, isMember, isOwned;
		
		//Query
		objRef.append("_id", commId);
		DBObject commData = commCollName.findOne(objRef);
		
		communityId = (String) commData.get("_id");
		name = (String) commData.get("name");
		coverImg = (String) commData.get("coverImg");
		img = (String) commData.get("img");
		address = (String) commData.get("address");
		phNo = (String) commData.get("phNo");
		email = (String) commData.get("email");
		ownerId = (String) commData.get("ownerId");
		
		likeCount = findCollectionCount(commId + "_LikedList");
		memberCount = findCollectionCount(commId + "_MemberList");
		
		isLiked = checkCommunityList(commId, userId, "liked");
		isMember = checkCommunityList(commId, userId, "member");
		isOwned = checkCommunityList(commId, userId, "owned");
		
		if(!communityId.isEmpty())
		{
			jsonObj.put("communityId", communityId);
		}
		if(!name.isEmpty())
		{
			jsonObj.put("name", name);
		}
		if(!coverImg.isEmpty())
		{
			jsonObj.put("coverImg", coverImg);
		}
		if(!img.isEmpty())
		{
			jsonObj.put("img", img);
		}
		if(!address.isEmpty())
		{
			jsonObj.put("address", address);
		}
		if(!phNo.isEmpty())
		{
			jsonObj.put("phNo", phNo);
		}
		if(!email.isEmpty())
		{
			jsonObj.put("email", email);
		}
		if(!ownerId.isEmpty())
		{
			jsonObj.put("ownerId", ownerId);
		}
		jsonObj.put("likeCount", likeCount);
		jsonObj.put("memberCount", memberCount);
		if(isLiked)
		{
			jsonObj.put("isLiked", isLiked);
		}
		if(isMember)
		{
			jsonObj.put("isMember", isMember);
		}
		if(isOwned)
		{
			jsonObj.put("isOwned", isOwned);
		}
		if(!isMember && !isOwned)
		{
			jsonObj.put("isNonMember", true);
		}
		
		return jsonObj;
	}
	
	public void communitiesInvite(String commId, String senderId, String senderName, List[] friendsListId, List[] friendsListName)
	{
		for(int i = 0;i < friendsListId.length; i++)
		{
			saveChat(senderId, senderName, friendsListId[i].toString(), friendsListName.toString(), commId, "invite");
		}
	}*/
	
	public void setOnlineMember(String userId)
	{
		onlineMembers.put(userId, new Date());
	}
	
	public String getOnlineMembers(HttpSession session, ArrayList<String> userIds) throws ParseException
	{
		JSONObject OnlineMembersJsonObj = new JSONObject();
		ArrayList OnlineMembersArr = new ArrayList();
		for(String userId : userIds)
		{
			try
			{
				boolean isOnline = isMemberOnline(userId);
				if(isOnline)
				{
					OnlineMembersArr.add(userId);
				}
				else
				{
					onlineMembers.remove(userId);
				}
			}
			catch(NullPointerException ex)
			{
				
			}
		}
		OnlineMembersJsonObj.put("onlineMembers", OnlineMembersArr);
		return OnlineMembersJsonObj.toString();
	}
	
	public boolean isMemberOnline(String userId)
	{
		Date currDate = new Date();
		Date startDate = (Date) onlineMembers.get(userId);
		long duration  = currDate.getTime() - startDate.getTime();
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		if(diffInMinutes > 2)//2 mins
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public String getDateFormat(String date, String clientTZ) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, MMM dd, yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm, MMM dd, yyyy", Locale.ENGLISH);
		
		sdf.setTimeZone(TimeZone.getTimeZone("GMT" + clientTZ));
		Date dateObj = sdf.parse(date);
		return sdf1.format(dateObj);
	}
	
	/*public String clearHomeFeeds()
	{
		DBCollection userColl = mongoTemplate.getCollection(COLLECTION_USER);
		while(userColl.)
		mongoTemplate.remove(new Query(), "bala_2016_09_30_19_52_13_pam_2016_09_30_19_53_38_chats");
		return "status";
	}*/
}