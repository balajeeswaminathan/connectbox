<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width" />
    <title>connecting Us</title>
    
    <link href="/WEB-INF/css/header.css" rel="stylesheet">
    <link href="/WEB-INF/css/chat.css" rel="stylesheet">
    <link href="/WEB-INF/css/menu.css" rel="stylesheet">
    <link href="/WEB-INF/css/feed.css" rel="stylesheet">
    <link href="/WEB-INF/css/photos.css" rel="stylesheet">
    <link href="/WEB-INF/css/communities.css" rel="stylesheet">
	<link href="/WEB-INF/css/unite-gallery.css" rel="stylesheet">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
    <script src="/WEB-INF/js/Utils/utils.js"></script>
    <script src="/WEB-INF/js/headerAndMenu.js"></script>
    <script src="/WEB-INF/js/chat.js"></script>
    <script src="/WEB-INF/js/feed.js"></script>
    <script src="/WEB-INF/js/communities.js"></script>
    
    <script src="/WEB-INF/js/plugins/unitegallery.js"></script>
    <script src="/WEB-INF/js/plugins/ug-theme-tiles.js"></script>
    
    <script src="/WEB-INF/js/photos.js"></script>
</head>
<body class="connUs-community">
    <%@ include file="/WEB-INF/views/header.jsp" %>
    <%@ include file="/WEB-INF/views/menu.jsp" %>
    <div class="connUs-page-outerWrapper">
    	<div id="connUs-page-wrapper">
    		<div id="connUs-comm-wrapper"></div>
    		<%@ include file="/WEB-INF/views/feed.jsp" %>
    	</div>
	</div>

	<%@ include file="/WEB-INF/views/chat.jsp" %>
	<%@ include file="/WEB-INF/views/friendsListHb.jsp" %>
	<%@ include file="/WEB-INF/views/profileDataHb.jsp" %>
	<%@ include file="/WEB-INF/views/feedListHb.jsp" %>
    <%@ include file="/WEB-INF/views/chatsList.jsp" %>
    <%@ include file="/WEB-INF/views/chatsListMgs.jsp" %>
    <%@ include file="/WEB-INF/views/likeListHb.jsp" %>
    <%@ include file="/WEB-INF/views/cmntsListHb.jsp" %>
    <%@ include file="/WEB-INF/views/viewPhotosHb.jsp" %>
    <%@ include file="/WEB-INF/views/communityHb.jsp" %>
</body>
</html>