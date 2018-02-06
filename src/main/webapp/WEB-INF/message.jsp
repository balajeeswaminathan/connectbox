<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Message</title>
	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="/cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
    <script src="/WEB-INF/js/message.js"></script>
    
    <link href="/WEB-INF/css/header.css" rel="stylesheet">
    <link href="/WEB-INF/css/home.css" rel="stylesheet">
    <link href="/WEB-INF/css/friendsList.css" rel="stylesheet">
</head>
<body>


    <%@ include file="/WEB-INF/views/header.jsp" %>
    
    <div class="connUs-basicInfo">
        <div class="connUs-profile-img">
            <img class="connUs-profile-img" src="//localhost/chatClient/img/photo.jpg"/>
        </div>
        <div class="connUs-profile-name"></div>
    </div>
    <div class="connUs-addFeed-cont">
        <textarea class="connUs-addFeed-desc" type="text" placeholder="Feed Anything"/></textarea>
        <form id="connUs-addFeed-img">
            <input id="connUs-feed-imgUpload" type="file" name="file" />
        </form>
        <div class="connUs-addFeed-btn">Feed</div>
    </div>
    <div class="connUs-menu-cont">
        <div class="connUs-menu connUs-profile">Profile</div>
        <div class="connUs-menu connUs-friends">Friends</div>
        <div class="connUs-menu connUs-photos">Photos</div>
        <div class="connUs-menu connUs-msg">messages</div>
    </div>
    
    <div class="connUs-chatsListhb">
        <%@ include file="/WEB-INF/views/chatsList.jsp" %>
    </div>
    
    <div class="connUs-chatsListMgsthb">
        <%@ include file="/WEB-INF/views/chatsListMgs.jsp" %>
    </div>
    
    <div class="connUs-chats-wrapper">
	     <div class="connUs-chatsList-cont">
		    <div class="connUs-chatsList-head">Chats Members</div>
		    <div class="connUs-chatsList-inner-wrapper"></div>
		    <div class="connUs-chatsListMgs-wrapper"></div>
		    <div class="connUs-chatsListMgs-input-cont">
               <input type="text" class="connUs-chatsListMgs-input"/>
               <div class="connUs-chatsListMgs-submit">Send<div>
            </div>
        </div>
    </div>

</body>
</html>