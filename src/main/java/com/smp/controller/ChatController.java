package com.smp.controller;

//Import core Java Package
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.*;
import java.util.*;

//Import Java Image Package
import javax.imageio.ImageIO;





import javax.mail.MessagingException;




import javax.servlet.http.HttpSession;



//Import Spring Framework Package
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


















//Import Mongo DB Package
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

//Import User Defined Package
import com.smp.model.Chat;
import com.smp.model.User;
import com.smp.service.FeedService;
import com.smp.service.MailSend;
import com.smp.service.chatService;

@Controller
public class ChatController {
	
	@Autowired
	private chatService chatService;
	@Autowired
	private FeedService feedService;
	
	//Login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam String email, @RequestParam String password) throws UnknownHostException {
		String response = chatService.userExist(email,password);
		return createJsonObject(JSON.parse(response));
    }
	
	//Register
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(@RequestParam String userId, @RequestParam String userName, @RequestParam String email, @RequestParam String password, @RequestParam String dob, @RequestParam String countryState, @RequestParam String country, @RequestParam String gender, @RequestParam String profileImgUrl, @RequestParam boolean isEdit) throws ParseException {
			JSONObject response = new JSONObject();

		    String user_Id = chatService.updateUser(userId, userName, email, password, dob, gender, countryState, country, profileImgUrl, isEdit);

    		response.put("status", "0");
        	response.put("sucessMessage", "Registered Sucessfully!");
        	response.put("user_Id", user_Id);
        	response.put("userName", userName);
        	
        	return createJsonObject(response);
    }
	
	@RequestMapping(value = "/forgetPsw", method = RequestMethod.POST)
	@ResponseBody
	public String forgetPsw(@RequestParam String email){
		String response = chatService.emailExist(email);

		return createJsonObject(JSON.parse(response));
	}
	
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	@ResponseBody
	public void sendMail(@RequestParam String email, @RequestParam String mailSubject, @RequestParam String mailDom) throws MessagingException{
		MailSend mailSend = new MailSend();
		mailSend.sendMail(email, mailSubject, mailDom);
	}
	
	//Request Friend
	@RequestMapping(value = "/requestFriends", method = RequestMethod.GET)
	@ResponseBody
	public String requestFriends(@RequestParam String requesterId, @RequestParam String requesterName, @RequestParam String accepterId, @RequestParam String accepterName) {
		JSONObject response = new JSONObject();
		
		chatService.addFriends(requesterId+"_FriendsList", accepterId, accepterName,-1);
		chatService.addFriends(accepterId+"_FriendsList", requesterId, requesterName,1);
		
		response.put("status","-1");
		response.put("sucessMessage","Friend request send successfully");
		
		return createJsonObject(response);
    }
	
	//Accept Friend
	@RequestMapping(value = "/acceptFriends", method = RequestMethod.GET)
	@ResponseBody
	public String acceptFriends(@RequestParam String accepterId, @RequestParam String accepterName, @RequestParam String requesterId, @RequestParam String requesterName) {
		JSONObject response = new JSONObject();
		
		chatService.addFriends(accepterId+"_FriendsList", requesterId, requesterName, 0);
		chatService.addFriends(requesterId+"_FriendsList", accepterId, accepterName, 0);
		
		response.put("status","0");
		response.put("sucessMessage","Friend accept send successfully");
    	
		return createJsonObject(response);
    }
	
	//Friends List
	@RequestMapping(value = "/friendList", method = RequestMethod.GET)
	@ResponseBody
	public String friendList(@RequestParam String userId, @RequestParam int friendsType, @RequestParam boolean isOnline) throws ParseException {
		String flData = chatService.getFriendsList(userId+"_FriendsList", friendsType, isOnline);
		
		return createJsonObject(JSON.parse(flData));
    }

	//Chat List
	@RequestMapping(value = "/chatList", method = RequestMethod.GET)
	@ResponseBody
	public String chatList(@RequestParam String senderId, @RequestParam String clientTZ) throws ParseException {
		String chatByList = chatService.getChatListData(senderId, clientTZ);
		
		return createJsonObject(JSON.parse(chatByList));
    }
	
	//Chat List Data - pending for date format
	@RequestMapping(value = "/chatListData", method = RequestMethod.GET)
	@ResponseBody
	public String chatListData(@RequestParam String senderId, @RequestParam String receiverId,@RequestParam int pageLevel, @RequestParam int limits) {
		String getChatData = chatService.getChatListData(senderId, receiverId, pageLevel, limits);
		
		return createJsonObject(JSON.parse(getChatData));
    }
	
	//Send Chat
	@RequestMapping(value = "/sendChat", method = RequestMethod.GET)
	@ResponseBody
	public String sendChat(@RequestParam String senderId, @RequestParam String senderName, @RequestParam String receiverId, @RequestParam String receiverName, @RequestParam String message, @RequestParam String commType) {
		String getChatData = chatService.saveChat(senderId, senderName, receiverId, receiverName, message, commType);

		return createJsonObject(JSON.parse(getChatData));
	}
	
	//Home
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	@ResponseBody
	public String homeCall(@RequestParam String userId)
	{
		JSONObject response = new JSONObject();
		List <DBObject> userDataList = new ArrayList <DBObject>();
		DBObject userData = chatService.homeCall(userId);
		userDataList.add(userData);
		
		return createJsonObject(userDataList);
	}
	
	//Profile
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	@ResponseBody
	public String profileCall(@RequestParam String userId, String profileId) throws ParseException {
		String flData = chatService.profileCall(userId, profileId);

		return createJsonObject(JSON.parse(flData));
    }
	
	//Upload Image
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public String uploadImage(MultipartHttpServletRequest request) throws IOException {
		MultipartFile multipartFile = request.getFile("file");
		String userId = request.getParameter("userId");
		String imgType = request.getParameter("imgType");
		String imgId = null, imgUrl = null;
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		
		byte[] imgBytes= multipartFile.getBytes();
		JSONObject response = new JSONObject();
		
		if(imgType.equals("profileImg"))
		{
			imgId = userId + "_profile";
		}
		else if(imgType.equals("feedImg"))
		{
			imgId = userId + "_Feed_" + dateFormat.format(date);
		}
		else if(imgType.equals("photos"))
		{
			imgId = userId + "_Photo_" + dateFormat.format(date);
		}
		else if(imgType.equals("communityImg"))
		{
			imgId = userId + "_Photo_" + dateFormat.format(date);
		}
		else if(imgType.equals("communityCoverImg"))
		{
			imgId = userId + "_Photo_" + dateFormat.format(date);
		}
		imgUrl = chatService.uploadImage(userId, imgType, imgId, imgBytes);
		
        response.put("imgPath", imgUrl);
        return createJsonObject(response);
	}
	
	//Search Users List
	@RequestMapping(value = "/searchList", method = RequestMethod.GET)
	@ResponseBody
	public String searchList(@RequestParam String searchTerm) {
		String slData = chatService.searchList(searchTerm);
		
		return createJsonObject(JSON.parse(slData));
	}
	
	//Add Feed
	@RequestMapping(value = "/addFeed", method = RequestMethod.POST)
	@ResponseBody
	public void addFeed(@RequestParam String userId, @RequestParam String commId, @RequestParam String feedDesc, @RequestParam String feedImgUrl, @RequestParam String videoUrl) throws IOException, ParseException {		
		feedService.addFeed(userId, commId, feedDesc, feedImgUrl, videoUrl);
	}
	
	//Like Feed
	@RequestMapping(value = "/giveLike", method = RequestMethod.POST)
	@ResponseBody
	public void feedLike(@RequestParam String userId, @RequestParam String commId, @RequestParam String userFeedId, @RequestParam String feedId){
		feedService.giveLike(userId, commId, userFeedId, feedId);
	}
	
	//Comment Feed
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	@ResponseBody
	public void feedComment(@RequestParam String userId, @RequestParam String commId, @RequestParam String userFeedId, @RequestParam String feedId, @RequestParam String comments){
		feedService.addComment(userId, commId, userFeedId, feedId, comments);
	}
	
	//User Or Comm Feed Call
	@RequestMapping(value = "/getFeed", method = RequestMethod.POST)
	@ResponseBody
	public String getUserFeed(@RequestParam String userId, @RequestParam String profileId, @RequestParam String commId, @RequestParam String clientTZ) throws ParseException{
		String feedResponse;
		if(commId.isEmpty())
		{
			feedResponse = feedService.getFeed(userId, profileId, profileId+"_Feed", clientTZ);
		}
		else
		{
			feedResponse = feedService.getFeed(userId, profileId, commId+"_Feed", clientTZ);
		}
		
		return createJsonObject(JSON.parse(feedResponse));
	}
	
	//get like Or Comnts List
	@RequestMapping(value = "/getLikeOrCmnts", method = RequestMethod.POST)
	@ResponseBody
	public String getLikeOrCmnts(@RequestParam String userId, @RequestParam String id, @RequestParam String type, @RequestParam String clientTZ) throws ParseException{
		String responseData = responseData = chatService.getLikeOrCmnts(userId, id, type, clientTZ);

		return createJsonObject(JSON.parse(responseData));
	}
	
	//get Chat Notification Count
	@RequestMapping(value = "/getChatNotification", method = RequestMethod.POST)
	@ResponseBody
	public String getChatNotification(@RequestParam String userId){
		int msgCount = chatService.getChatNotification(userId);
		chatService.setOnlineMember(userId);
		return createJsonObject(msgCount);
	}

	@RequestMapping(value = "/removeChatNotification", method = RequestMethod.POST)
	@ResponseBody
	public String removeChatNotification(@RequestParam String userId, @RequestParam String senderId){
		int msgCount = chatService.removeChatNotification(userId, senderId);
		
		return createJsonObject(msgCount);
	}

	@RequestMapping(value = "/addPhotos", method = RequestMethod.POST)
	@ResponseBody
	public void addPhotos(@RequestParam String userId, @RequestParam String desc, @RequestParam String imgUrl){
		chatService.addPhotos(userId, desc, imgUrl);
	}
	
	@RequestMapping(value = "/getPhotos", method = RequestMethod.POST)
	@ResponseBody
	public String getPhotos(@RequestParam String userId, @RequestParam String profileId, @RequestParam String clientTZ) throws ParseException{
		String photos = chatService.getPhotos(userId, profileId, profileId+"_Photos", clientTZ);
		
		return createJsonObject(JSON.parse(photos));
	}
	
	@RequestMapping(value = "/addTopic", method = RequestMethod.POST)
	@ResponseBody
	public void addTopic(@RequestParam String topicName, @RequestParam String imgUrl, @RequestParam String userId){
		chatService.addTopic(topicName, imgUrl, userId);
	}
	
	@RequestMapping(value = "/getTopics", method = RequestMethod.POST)
	@ResponseBody
	public String getTopics(@RequestParam String userId, @RequestParam String topicType){
		String topics = chatService.getTopics(userId, topicType);
		
		return createJsonObject(JSON.parse(topics));
	}
	
	@RequestMapping(value = "/getTopic", method = RequestMethod.POST)
	@ResponseBody
	public String getTopic(@RequestParam String topicId){
		JSONObject response = new JSONObject();

		DBObject topicData = chatService.getTopic(topicId);
		response.put("name", topicData.get("name"));
		response.put("imgUrl", topicData.get("imgUrl"));
		
		return createJsonObject(response);
	}
	
	@RequestMapping(value = "/addTopicFeed", method = RequestMethod.POST)
	@ResponseBody
	public void addTopicFeed(@RequestParam String topicId, @RequestParam String userId, @RequestParam String feedDesc, @RequestParam String imgUrl){
		chatService.addTopicFeed(topicId, userId, feedDesc, imgUrl);
	}
	
	@RequestMapping(value = "/getTopicFeed", method = RequestMethod.POST)
	@ResponseBody
	public String getTopicFeed(@RequestParam String topicId){
		String userFeed = chatService.getTopicFeed(topicId+"_Feed");
		
		return createJsonObject(JSON.parse(userFeed));
	}
	
	@RequestMapping(value = "/getUserHomeFeeds", method = RequestMethod.POST)
	@ResponseBody
	public String getUserHomeFeeds(@RequestParam String userId, @RequestParam int pageLevel, @RequestParam int limits, @RequestParam String clientTZ) throws ParseException{
		String userHomeFeed = feedService.geHomeFeeds(userId, pageLevel, limits, clientTZ);
		
		return createJsonObject(JSON.parse(userHomeFeed));
	}
	
	@RequestMapping(value = "/activateUser", method = RequestMethod.POST)
	@ResponseBody
	public void activateUser(@RequestParam String userId){
		chatService.activateUser(userId);
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public String  resetPassword(HttpSession session, @RequestParam String userId, @RequestParam String password) throws MessagingException{
		String response = chatService.resetPassword(session, userId, password);
		
		return createJsonObject(JSON.parse(response));
	}
	
	@RequestMapping(value = "/setPassword", method = RequestMethod.POST)
	@ResponseBody
	public String  setPassword(HttpSession session, @RequestParam String userId, @RequestParam String password, @RequestParam int OTP) throws MessagingException{
		String response = chatService.setPassword(session, userId, password, OTP);
		
		return createJsonObject(JSON.parse(response));
	}
	
	@RequestMapping(value = "/updateCommunities", method = RequestMethod.POST)
	@ResponseBody
	public void  updateCommunities(@RequestParam String commId, @RequestParam String name, @RequestParam String coverImg, @RequestParam String img, @RequestParam String address, @RequestParam String email, @RequestParam String phNo, @RequestParam String ownerId, @RequestParam boolean isEdit){
		chatService.updateCommunities(commId, name, coverImg, img, address, phNo, email, ownerId, isEdit);
	}
	
	@RequestMapping(value = "/getCommunity", method = RequestMethod.POST)
	@ResponseBody
	public String getCommunity(@RequestParam String commId, @RequestParam String userId){
		JSONObject responseJsonObj = chatService.getCommunity(commId, userId);
		String response = responseJsonObj.toString();
		return response;
	}
	
	@RequestMapping(value = "/giveLikeOrMemberCommunity", method = RequestMethod.POST)
	@ResponseBody
	public void giveLikeOrMemberCommunity(@RequestParam String commId, @RequestParam String userId, @RequestParam String type){
		chatService.giveLikeOrMemberCommunity(commId, userId, type);
	}
	
	@RequestMapping(value = "/getCommunityList", method = RequestMethod.POST)
	@ResponseBody
	public String  getCommunityList(@RequestParam String userId, @RequestParam String type){
		String response = chatService.getCommunityList(userId, type);
		
		return createJsonObject(JSON.parse(response));
	}
	
	@RequestMapping(value = "/communitiesInvite", method = RequestMethod.POST)
	@ResponseBody
	public void  communitiesInvite(@RequestParam String commId, @RequestParam String senderId, @RequestParam String senderName, @RequestParam List[] friendsListId, @RequestParam List[] friendsListName){
		chatService.communitiesInvite(commId, senderId, senderName, friendsListId, friendsListName);
	}
	
	/*@RequestMapping(value = "/setOnlineMember", method = RequestMethod.POST)
	@ResponseBody
	public void  setOnline(HttpSession session, @RequestParam String userId){
		chatService.setOnlineMember(userId);
	}*/
	
	@RequestMapping(value = "/getOnlineMembers", method = RequestMethod.POST)
	@ResponseBody
	public String getOnlineMembers(HttpSession session, @RequestParam String userId) throws ParseException{
		/*StringTokenizer strToken = new StringTokenizer(userIds, "|||");
		ArrayList<String> userList = new ArrayList<String>();
		while(strToken.hasMoreTokens())
		{
			userList.add(strToken.nextToken());
		}
		String response = chatService.getOnlineMembers(session, userList);*/
		
		boolean isOnline = chatService.isMemberOnline(userId);
		
		return createJsonObject(isOnline);
	}
	
	/*@RequestMapping(value = "/clearHomeFeeds", method = RequestMethod.GET)
	@ResponseBody
	public String  clearHomeFeeds() throws MessagingException{
		String response = chatService.clearHomeFeeds();
		
		return createJsonObject(response);
	}*/
	
	//Create Json Object
	public String createJsonObject(Object object){
		JSONObject data = new JSONObject();
		JSONObject prop = new JSONObject();
		
		data.put("data", object);
		prop.put("properties", data);
		return prop.toString();
	}
}