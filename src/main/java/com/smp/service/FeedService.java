package com.smp.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.smp.model.Comment;
import com.smp.model.Feed;
import com.smp.model.HomeFeed;
import com.smp.model.Like;
import com.smp.model.LikeOrCmtHomeFeed;

import com.smp.service.Pagination;

@Repository
public class FeedService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private chatService chatService;
	
	//Add Feed
	public void addFeed(String userId, String commId, String feedDesc, String feedImgUrl, String videoUrl) throws IOException, ParseException{
		DBObject objDoc;
		String frndId, feedId, type;
		DBCollection frndsCollName;
		HomeFeed homeFeed = new HomeFeed();
	
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		DateFormat feedDateFormat = new SimpleDateFormat("HH:mm, MMM dd, yyyy");
		String feedDateAndTime = feedDateFormat.format(date);
	
		Feed feed = new Feed();
		if(!commId.isEmpty())
		{
			feedId = commId + "_Feed_" + dateFormat.format(date);
			feed.setCommId(commId);
			feed.setUserId(userId);
			type = "community";
		}
		else
		{
			feedId = userId + "_Feed_" + dateFormat.format(date);
			type = "user";
		}
		feed.setId(feedId);
		feed.setDesc(feedDesc);
		feed.setImgUrl(feedImgUrl);
		feed.setDateAndTime(feedDateAndTime);
		feed.setVideoUrl(videoUrl);
		
		
		if(!commId.isEmpty())
		{
			mongoTemplate.insert(feed, commId+"_Feed");
			homeFeed.setCommId(commId);
			frndsCollName = mongoTemplate.getCollection(commId + "_MemberList");
		}
		else
		{
			mongoTemplate.insert(feed, userId+"_Feed");
			frndsCollName = mongoTemplate.getCollection(userId + "_FriendsList");
		}
		DBCursor frndsDataCursor = frndsCollName.find();
		
		homeFeed.setFeedId(feedId);
		homeFeed.setDateAndTime(feedDateAndTime);
		homeFeed.setType(type);
		homeFeed.setUserFeedId(userId);
		while(frndsDataCursor.hasNext())
		{
			objDoc = frndsDataCursor.next();
			frndId = (String) objDoc.get("_id");
			
			//get yesterday date
			Calendar cal = Calendar.getInstance();
			//cal.add(Calendar.DATE, -1);
			cal.add(Calendar.MINUTE, -2);
			String objRefDate = feedDateFormat.format(cal.getTime());
			Query query = new Query(Criteria.where("dateAndTime").lt(objRefDate));
			
			mongoTemplate.remove(query, frndId + "_HomeFeeds");

			mongoTemplate.insert(homeFeed, frndId + "_HomeFeeds");
		}
	}
	
	//Give Feed Like
	public void giveLike(String userId, String commId, String userFeedId, String feedId){
		Like like = new Like();
		DBObject userData= chatService.getUserData(userId);
		DBObject objDoc;
		String frndId, type = "like";
		
		String userName = (String) userData.get("username");
		String userImg = (String) userData.get("imgPath");

		like.setUserId(userId);
		like.setUserName(userName);
		like.setUserImg(userImg);

		Date date = new Date();
		DateFormat feedDateFormat = new SimpleDateFormat("HH:mm, MMM dd, yyyy");
		String feedDateAndTime = feedDateFormat.format(date);
		setLikeOrCmtHomeFeed(feedId, commId, userFeedId, userId, feedDateAndTime, type);
		//Query
		mongoTemplate.insert(like, feedId+"_LikedList");
	}
	
	//Add feed comment
	public void addComment(String userId, String commId, String userFeedId, String feedId, String comments){
		Comment comment = new Comment();
		Date date = new Date();
		DateFormat feedCmtDateFormat = new SimpleDateFormat("HH:mm, MMM dd, yyyy");
		String feedCmtDateAndTime = feedCmtDateFormat.format(date);
		String type = "comments";
		
		DBObject resultSet= chatService.getUserData(userId);
		String userName = (String) resultSet.get("username");
		String userImg = (String) resultSet.get("imgPath");
		
		comment.setId(UUID.randomUUID().toString());
		comment.setUserId(userId);
		comment.setUserName(userName);
		comment.setUserImg(userImg);
		comment.setComments(comments);
		comment.setDateAndTime(feedCmtDateAndTime);

		setLikeOrCmtHomeFeed(feedId, commId, userFeedId, userId, feedCmtDateAndTime, type);
		//Query
		mongoTemplate.insert(comment, feedId+"_CommentList");
	}
	
	//set home like or comment
	public void setLikeOrCmtHomeFeed(String feedId, String commId, String userFeedId, String userId, String feedDateAndTime, String type)
	{
		DBObject objDoc;
		String frndId;
		DBCollection frndsCollName;
		
		LikeOrCmtHomeFeed likeOrCmtHomeFeed = new LikeOrCmtHomeFeed();
		
		if(!commId.isEmpty())
		{
			likeOrCmtHomeFeed.setCommId(commId);
			frndsCollName = mongoTemplate.getCollection(commId + "_MemberList");
		}
		else
		{
			frndsCollName = mongoTemplate.getCollection(userId + "_FriendsList");
		}
		DBCursor frndsDataCursor = frndsCollName.find();
		
		likeOrCmtHomeFeed.setId(UUID.randomUUID().toString());
		likeOrCmtHomeFeed.setFeedId(feedId);
		likeOrCmtHomeFeed.setUserFeedId(userFeedId);
		likeOrCmtHomeFeed.setUserId(userId);
		likeOrCmtHomeFeed.setDateAndTime(feedDateAndTime);
		likeOrCmtHomeFeed.setType(type);
		while(frndsDataCursor.hasNext())
		{
			objDoc = frndsDataCursor.next();
			frndId = (String) objDoc.get("_id");

			mongoTemplate.insert(likeOrCmtHomeFeed, frndId + "_HomeFeeds");
		}
	}
	
	public long findCollectionCount(String collName)
	{
		try
		{
			DBCollection coll = mongoTemplate.getCollection(collName);
			return coll.count();
		}
		catch(Exception ex)
		{
			return 0;
		}
	}

	public String geHomeFeeds(String userId, int pageLevel, int limits, String clientTZ) throws ParseException
	{
		List homeFeedList = new ArrayList();
		List userFeedList = new ArrayList();
		List homeFeedDataList;
		JSONObject userFeedFinalDataJson = new JSONObject();
		DBObject homeFeedObj;
		JSONObject userDataResultSet;
		int skipLevel, homeFeedDataListCount;
		String userFeedId, commId, type, homeFeedCollName = userId + "_HomeFeeds";		
		
		DBCollection userFrndsColl = mongoTemplate.getCollection(userId + "_FriendsList");
		
		//get yesterday date
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		
		Pagination page = new Pagination();
		
		DBCursor userFrndsDC = userFrndsColl.find();
		
		skipLevel = page.getSkipLevel(mongoTemplate, homeFeedCollName, pageLevel, limits);
		if(skipLevel < 0)
		{
			limits = page.getLimits(skipLevel, limits);
			skipLevel = 0;
		}
		homeFeedDataList = page.getPaginationData(mongoTemplate, homeFeedCollName, true, skipLevel, limits);
		homeFeedDataListCount = homeFeedDataList.size();
		
		if(userFrndsDC.count() == 0)
		{
			userFeedFinalDataJson.put("status", 1);
			userFeedFinalDataJson.put("message", "No Members so no recent feeds");
		}
		else if(homeFeedDataListCount == 0)
		{
			userFeedFinalDataJson.put("status", 2);
			userFeedFinalDataJson.put("message", "No recent Feeds from your members");
		}
		else
		{
			for(int i = 0; i < homeFeedDataListCount; i++)
			{
				homeFeedObj = (DBObject) homeFeedDataList.get(i);
				userFeedId = (String) homeFeedObj.get("userFeedId");
				commId = (String) homeFeedObj.get("commId");
				type = (String) homeFeedObj.get("type");

				userDataResultSet = getUserJSONData(userFeedId);
				
				if(type.equals("user") || type.equals("community") || type.equals("photo"))
				{
					DBObject userCommHomeFeedObj = getUserCommPhotosHomeFeeds(userId, homeFeedObj, type, clientTZ);
					userCommHomeFeedObj.putAll(userDataResultSet);
					homeFeedList.add(userCommHomeFeedObj);
				}
				else if(type.equals("createCommunity") || type.equals("updateCommunity") || type.equals("updateProfile"))
				{
					JSONObject createUpdateCommProfileHomeFeedList = getUsercreateOrUpdateCommOreProfileHomeFeeds(commId, homeFeedObj, type, clientTZ);
					createUpdateCommProfileHomeFeedList.putAll(userDataResultSet);
					homeFeedList.add(createUpdateCommProfileHomeFeedList);
				}
				else if(type.equals("like") || type.equals("comments"))
				{
					DBObject likeOrCommentsHomeFeedObj = getUserHomeLikeOrCmtsFeeds(userId, homeFeedObj, type, clientTZ);
					likeOrCommentsHomeFeedObj.putAll(userDataResultSet);
					homeFeedList.add(likeOrCommentsHomeFeedObj);
				}
				
				
			}
			/*if(!userCommHomeFeedList.isEmpty())
			{
				homeFeedList.addAll(userCommHomeFeedList);
			}
			if(!likeOrCmntFeedList.isEmpty())
			{
				homeFeedList.addAll(likeOrCmntFeedList);
			}*/
			if(skipLevel == 0)
			{
				userFeedFinalDataJson.put("isLast", true);
			}
			userFeedFinalDataJson.put("feedList", homeFeedList);
			userFeedFinalDataJson.put("count", homeFeedDataListCount);
		}
		
		return userFeedFinalDataJson.toString();
	}
	
	public JSONObject getUserJSONData(String userId)
	{
		JSONObject userDataJsonObj = new JSONObject();
		String userImgUrl;
		DBObject userDataResultSet;
		
		userDataResultSet = chatService.getUserData(userId);
		userDataJsonObj.put("userFeedId", userDataResultSet.get("_id"));
		userDataJsonObj.put("userFeedUserName", userDataResultSet.get("username"));
		userImgUrl = (String) userDataResultSet.get("imgPath");
		if(userImgUrl != null)
		{
			userDataJsonObj.put("userFeedUserImg", userDataResultSet.get("imgPath"));
		}
		
		return userDataJsonObj;
	}
	
	public DBObject getUserCommPhotosHomeFeeds(String userId, DBObject homeFeedObj, String type, String clientTZ) throws ParseException
	{
		String feedId, userFeedId, commId, feedColl = null;
		DBCollection userFeedsColl;
		BasicDBObject feedIdRef = new BasicDBObject();
		DBObject userFeedData;
		JSONObject commJsonObj = new JSONObject();
		long likeCount, cmntsCount;
		boolean isLiked;
		
		userFeedId = (String) homeFeedObj.get("userFeedId");
		commId = (String) homeFeedObj.get("commId");
		if(type.equals("user"))
		{
			feedColl = userFeedId + "_Feed";
		}
		else if(type.equals("community"))
		{
			feedColl = commId + "_Feed";
			commJsonObj = chatService.getCommunity(commId, "");
		}
		else if(type.equals("photo"))
		{
			feedColl = userFeedId + "_Photos";
		}
		
		feedId = (String) homeFeedObj.get("_id");
		userFeedsColl = mongoTemplate.getCollection(feedColl);
		feedIdRef.append("_id", feedId);
		userFeedData = userFeedsColl.findOne(feedIdRef);
		
		likeCount = findCollectionCount(feedId + "_LikedList");
		cmntsCount = findCollectionCount(feedId + "_CommentList");
		
		isLiked = chatService.dataExist(userId, feedId + "_LikedList");
		
		userFeedData.put("dateAndTime", chatService.getDateFormat((String)userFeedData.get("dateAndTime"), clientTZ));
		userFeedData.putAll(commJsonObj);
		userFeedData.put("likeCount", likeCount);
		userFeedData.put("cmntsCount", cmntsCount);
		if(isLiked)
		{
			userFeedData.put("isLiked", true);
		}

		userFeedData.put("type", type);
		return userFeedData;
	}
	
	public JSONObject getUsercreateOrUpdateCommOreProfileHomeFeeds(String commId, DBObject homeFeedObj, String type, String clientTZ) throws ParseException
	{
		ArrayList updatedKeys = new ArrayList();
		ArrayList updatedValues = new ArrayList();
		JSONObject commJsonObj = new JSONObject();
		
		if(type.equals("createCommunity") || type.equals("updateCommunity"))
		{
			commJsonObj = chatService.getCommunity(commId, "");
		}
		
		if(type.equals("updateCommunity") || type.equals("updateProfile"))
		{
			String key, value;
			String updatedDataKeys = (String) homeFeedObj.get("updatedDataKeys");
			String updatedDataValues = (String) homeFeedObj.get("updatedDataValues");
			
			StringTokenizer st1 = new StringTokenizer(updatedDataKeys, "|||");
			StringTokenizer st2 = new StringTokenizer(updatedDataValues, "|||");
			
			while(st1.hasMoreTokens() && st2.hasMoreTokens())
			{
				key = st1.nextToken();
				value = st2.nextToken();
				
				if(!key.isEmpty() && !value.isEmpty())
				{
					updatedKeys.add(key);
					updatedValues.add(value);
					//commJsonObj.put(key, value);
				}
			}
		}
	
		commJsonObj.put("dateAndTime", chatService.getDateFormat((String) homeFeedObj.get("dateAndTime"), clientTZ));
		commJsonObj.put("updatedKeys", updatedKeys);
		commJsonObj.put("updatedValues", updatedValues);
		commJsonObj.put("type", type);
		return commJsonObj;
	}
	
	//get home like or comment feed
		public DBObject getUserHomeLikeOrCmtsFeeds(String userId, DBObject homeFeedObj, String type, String clientTZ) throws ParseException{
			String userFeedId, feedImgUrl, feedId, commId,  userImgUrl, userDataCmntsId, userDataCmntsDateAndTime;
			String comments, userFeedUsername, userFeedUserImg, userName, userImg;
			DBCollection userFeedsColl, commFeedsColl, feedDataCmntsColl;
			DBObject userFeedData = null, cmntData;
			DBObject userFeedDataResultSet, userDataResultSet;
			List homeFeedList = new ArrayList();
			BasicDBObject feedIdRef = new BasicDBObject();
			BasicDBObject feedCmntsRef = new BasicDBObject();
			JSONObject commJsonObj = new JSONObject();
			long likeCount, cmntsCount;
			boolean isLiked;
			
			feedId = (String) homeFeedObj.get("feedId");
			userFeedId = (String) homeFeedObj.get("userFeedId");
			userId = (String) homeFeedObj.get("userId");
			commId = (String) homeFeedObj.get("commId");
		
			userFeedDataResultSet = chatService.getUserData(userFeedId);
			userDataResultSet = chatService.getUserData(userId);
			
			userFeedsColl = mongoTemplate.getCollection(userFeedId + "_Feed");
			feedIdRef.append("_id", feedId);
			userFeedData = userFeedsColl.findOne(feedIdRef);
			
			if(userFeedData == null && commId != null) // for community
			{
				commFeedsColl = mongoTemplate.getCollection(commId + "_Feed");
				userFeedData = commFeedsColl.findOne(feedIdRef);
				commJsonObj = chatService.getCommunity(commId, "");
				userFeedData.putAll(commJsonObj);
			}
			if(userFeedData == null) //for photos
			{
				commFeedsColl = mongoTemplate.getCollection(userFeedId + "_Photos");
				userFeedData = commFeedsColl.findOne(feedIdRef);
			}
			userFeedId = (String) userFeedDataResultSet.get("_id");
			userFeedUsername = (String) userFeedDataResultSet.get("username");
			userFeedUserImg = (String) userFeedDataResultSet.get("imgPath");
			
			userFeedData.put("userFeedId", userFeedId);
			userFeedData.put("userFeedUserName", userFeedUsername);
			userFeedData.put("userFeedUserImg", userFeedUserImg);
			
			likeCount = chatService.findCollectionCount(feedId + "_LikedList");
			cmntsCount = chatService.findCollectionCount(feedId + "_CommentList");
			
			isLiked = chatService.dataExist(userId, feedId + "_LikedList");
			
			userFeedData.put("likeCount", likeCount);
			userFeedData.put("cmntsCount", cmntsCount);
			
			userId = (String) userDataResultSet.get("_id");
			userName = (String) userDataResultSet.get("username");
			userImg = (String) userDataResultSet.get("imgPath");
			
			userFeedData.put("userId", userId);
			userFeedData.put("userName", userName);
			userFeedData.put("userImg", userImg);
			
			userFeedData.put("dateAndTime", chatService.getDateFormat((String) homeFeedObj.get("dateAndTime"), clientTZ));
			userFeedData.put("type", type);
			if(type.equals("comments"))
			{
				feedDataCmntsColl = mongoTemplate.getCollection(feedId+"_CommentList");
				userDataCmntsId = userId;
				userDataCmntsDateAndTime = (String) homeFeedObj.get("dateAndTime");
				
				feedCmntsRef.append("userId", userDataCmntsId);
				feedCmntsRef.append("dateAndTime", userDataCmntsDateAndTime);
		
				userFeedData.put("likeOrCmntdateAndTime", userDataCmntsDateAndTime);
				cmntData = feedDataCmntsColl.findOne(feedCmntsRef);
				comments = (String) cmntData.get("comments");
				userFeedData.put("comments", comments);
			}

			return userFeedData;
		}
	
	//get home feed
	/*public List getUserCommPhotosHomeFeeds(String userId, DBCollection homeUserFeedsColl) throws ParseException{
		String userFeedId, feedId, commId, feedColl = null, userImgUrl, type;
		DBCollection userFeedsColl;
		DBObject userFeedData;
		DBObject userDataResultSet, homeFeedObj;
		JSONObject commJsonObj = new JSONObject();
		JSONObject userDataJsonObj = new JSONObject();
		List UserCommHomeFeedList = new ArrayList();
		BasicDBObject feedIdRef = new BasicDBObject();
		long likeCount, cmntsCount;
		boolean isLiked;
		
		DBCursor homeFeedDataCursor = homeUserFeedsColl.find();
		while(homeFeedDataCursor.hasNext())
		{
			homeFeedObj = homeFeedDataCursor.next();
			type = (String) homeFeedObj.get("type");
			if(type.equals("user") || type.equals("community") || type.equals("photo") || type.equals("createCommunity") || type.equals("updateCommunity") || type.equals("updateProfile"))
			{
				userFeedId = (String) homeFeedObj.get("userFeedId");
				commId = (String) homeFeedObj.get("commId");

				userDataResultSet = chatService.getUserData(userFeedId);
				userDataJsonObj.put("userFeedId", userDataResultSet.get("_id"));
				userDataJsonObj.put("userFeedUserName", userDataResultSet.get("username"));
				userImgUrl = (String) userDataResultSet.get("imgPath");
				if(userImgUrl != null)
				{
					userDataJsonObj.put("userFeedUserImg", userDataResultSet.get("imgPath"));
				}
				
				if(type.equals("user"))
				{
					feedColl = userFeedId + "_Feed";
				}
				else if(type.equals("community"))
				{
					feedColl = commId + "_Feed";
				}
				else if(type.equals("photo"))
				{
					feedColl = userFeedId + "_Photos";
				}
				else if(type.equals("createCommunity") || type.equals("updateCommunity"))
				{
					commJsonObj = chatService.getCommunity(commId, "");

					if(type.equals("updateCommunity"))
					{
						String key, value;
						String updatedDataKeys = (String) homeFeedObj.get("updatedDataKeys");
						String updatedDataValues = (String) homeFeedObj.get("updatedDataValues");
						
						StringTokenizer st1 = new StringTokenizer(updatedDataKeys, "|||");
						StringTokenizer st2 = new StringTokenizer(updatedDataValues, "|||");
						
						while(st1.hasMoreTokens() && st2.hasMoreTokens())
						{
							key = st1.nextToken();
							value = st2.nextToken();
							
							if(!key.isEmpty() && !value.isEmpty())
							{
								commJsonObj.put(key, value);
							}
						}
					}

					commJsonObj.put("type", type);
					commJsonObj.putAll(userDataJsonObj);
					UserCommHomeFeedList.add(commJsonObj);
				}
				else if(type.equals("updateProfile"))
				{
					String key, value;
					String updatedDataKeys = (String) homeFeedObj.get("updatedDataKeys");
					String updatedDataValues = (String) homeFeedObj.get("updatedDataValues");
					
					StringTokenizer st1 = new StringTokenizer(updatedDataKeys, "|||");
					StringTokenizer st2 = new StringTokenizer(updatedDataValues, "|||");
					
					while(st1.hasMoreTokens() && st2.hasMoreTokens())
					{
						key = st1.nextToken();
						value = st2.nextToken();
						
						if(!key.isEmpty() && !value.isEmpty())
						{
							userDataJsonObj.put(key, value);
						}
					}
					
					userDataJsonObj.put("type", type);
					UserCommHomeFeedList.add(userDataJsonObj);
				}
				
				if(!type.equals("createCommunity") && !type.equals("updateCommunity") && !type.equals("updateProfile"))
				{
					feedId = (String) homeFeedObj.get("_id");
					userFeedsColl = mongoTemplate.getCollection(feedColl);
					feedIdRef.append("_id", feedId);
					userFeedData = userFeedsColl.findOne(feedIdRef);

					likeCount = findCollectionCount(feedId + "_LikedList");
					cmntsCount = findCollectionCount(feedId + "_CommentList");
					
					isLiked = chatService.dataExist(userId, feedId + "_LikedList");
					
					userFeedData.put("likeCount", likeCount);
					userFeedData.put("cmntsCount", cmntsCount);
					if(isLiked)
					{
						userFeedData.put("isLiked", true);
					}

					userFeedData.put("type", type);
					userFeedData.putAll(userDataJsonObj);
					UserCommHomeFeedList.add(userFeedData);
				}
			}
		}
		
		return UserCommHomeFeedList;
	}*/

	public String getFeed(String userId, String profileId, String feedCollName, String clientTZ) throws ParseException{
		List feedList = new ArrayList();
		JSONObject feedData = new JSONObject();
		DBObject feed = null;
		DBObject userDataResultSet;
		String user_Id, userName, userimgUrl, feedId, feedimgUrl, likeUserId, videoUrl;
		long likeCount, cmntsCount;
		boolean isLiked;
		
		//Query
		DBCollection collection = mongoTemplate.getCollection(feedCollName);
		DBCursor resultSet = collection.find();
		userDataResultSet = chatService.getUserData(userId);
		user_Id = (String) userDataResultSet.get("_id");
		userName = (String) userDataResultSet.get("username");
		userimgUrl = (String) userDataResultSet.get("imgPath");
		
		while(resultSet.hasNext())
		{
			feed = resultSet.next();
			feedId = (String) feed.get("_id");
			likeCount = chatService.findCollectionCount(feedId + "_LikedList");
			cmntsCount = chatService.findCollectionCount(feedId + "_CommentList");
			
			isLiked = chatService.dataExist(userId, feedId + "_LikedList");
			if(isLiked)
			{
				feed.put("isLiked", true);
			}
			feed.put("userId", user_Id);
			if(userId.equals(profileId))
			{
				feed.put("userName", "You");
			}
			else
			{
				feed.put("userName", userName);
			}
			if(userimgUrl != null)
			{
				feed.put("userImgUrl", userimgUrl);
			}
			feed.put("dateAndTime", chatService.getDateFormat((String)feed.get("dateAndTime"), clientTZ));
			feed.put("likeCount", likeCount);
			feed.put("cmntsCount", cmntsCount);
			feedList.add(feed);
		}
		feedData.put("feedList", feedList);
		
		return feedData.toString();
	}
	
	//get home like or comment feed
	/*public List getUserHomeLikeOrCmtsFeeds(String userId, DBCollection homeUserFeedsColl) throws ParseException{
		String userFeedId, feedImgUrl, feedId, commId,  userImgUrl, type, userDataCmntsId, userDataCmntsDateAndTime;
		String comments, userFeedUsername, userFeedUserImg, userName, userImg;
		DBCollection userFeedsColl, commFeedsColl, feedDataCmntsColl;
		DBObject userFeedData = null, cmntData;
		DBObject userFeedDataResultSet, userDataResultSet, homeFeedObj;
		List homeFeedList = new ArrayList();
		BasicDBObject feedIdRef = new BasicDBObject();
		BasicDBObject feedCmntsRef = new BasicDBObject();
		long likeCount, cmntsCount;
		boolean isLiked;
		
		DBCursor homeFeedDataCursor = homeUserFeedsColl.find();
		while(homeFeedDataCursor.hasNext())
		{
			homeFeedObj = homeFeedDataCursor.next();
			type = (String) homeFeedObj.get("type");
			
			if(type.equals("like") || type.equals("comments"))
			{
				feedId = (String) homeFeedObj.get("feedId");
				userFeedId = (String) homeFeedObj.get("userFeedId");
				userId = (String) homeFeedObj.get("userId");
				commId = (String) homeFeedObj.get("commId");
	
				userFeedDataResultSet = chatService.getUserData(userFeedId);
				userDataResultSet = chatService.getUserData(userId);
				
				userFeedsColl = mongoTemplate.getCollection(userFeedId + "_Feed");
				feedIdRef.append("_id", feedId);
				userFeedData = userFeedsColl.findOne(feedIdRef);
				
				if(userFeedData == null) // for community
				{
					commFeedsColl = mongoTemplate.getCollection(commId + "_Feed");
					userFeedData = commFeedsColl.findOne(feedIdRef);
				}
				if(userFeedData == null) //for photos
				{
					commFeedsColl = mongoTemplate.getCollection(userFeedId + "_Photos");
					userFeedData = commFeedsColl.findOne(feedIdRef);
				}
				userFeedId = (String) userFeedDataResultSet.get("_id");
				userFeedUsername = (String) userFeedDataResultSet.get("username");
				userFeedUserImg = (String) userFeedDataResultSet.get("imgPath");
				
				userFeedData.put("userFeedId", userFeedId);
				userFeedData.put("userFeedUserName", userFeedUsername);
				userFeedData.put("userFeedUserImg", userFeedUserImg);
				
				likeCount = chatService.findCollectionCount(feedId + "_LikedList");
				cmntsCount = chatService.findCollectionCount(feedId + "_CommentList");
				
				isLiked = chatService.dataExist(userId, feedId + "_LikedList");
				
				userFeedData.put("likeCount", likeCount);
				userFeedData.put("cmntsCount", cmntsCount);
				
				userId = (String) userDataResultSet.get("_id");
				userName = (String) userDataResultSet.get("username");
				userImg = (String) userDataResultSet.get("imgPath");
				
				userFeedData.put("userId", userId);
				userFeedData.put("userName", userName);
				userFeedData.put("userImg", userImg);
				
				userFeedData.put("type", type);
				if(type.equals("comments"))
				{
					feedDataCmntsColl = mongoTemplate.getCollection(feedId+"_CommentList");
					userDataCmntsId = userId;
					userDataCmntsDateAndTime = (String) homeFeedObj.get("dateAndTime");
					
					feedCmntsRef.append("userId", userDataCmntsId);
					feedCmntsRef.append("dateAndTime", userDataCmntsDateAndTime);
	
					userFeedData.put("likeOrCmntdateAndTime", userDataCmntsDateAndTime);
					cmntData = feedDataCmntsColl.findOne(feedCmntsRef);
					comments = (String) cmntData.get("comments");
					userFeedData.put("comments", comments);
				}

				if(userFeedData != null)
				{
					homeFeedList.add(userFeedData);
				}
			}
		}
		return homeFeedList;
	}*/
}