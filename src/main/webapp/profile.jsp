<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width" />
    <title>connecting Us</title>
    
    <link href="<c:url value="/resources/css/header.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/chat.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/profile.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/feed.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/photos.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/unite-gallery.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/lib/croppie.css" />" rel="stylesheet">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
    <script src="<c:url value="/resources/js/Utils/utils.js" />"></script>
    <script src="<c:url value="/resources/js/headerAndMenu.js" />"></script>
    <script src="<c:url value="/resources/js/chat.js" />"></script>
    <script src="<c:url value="/resources/js/profile.js" />"></script>
    <script src="<c:url value="/resources/js/feed.js" />"></script>
    <script src="<c:url value="/resources/js/photos.js" />"></script>

    <script src="<c:url value="/resources/js/plugins/croppie.js" />"></script>
    <script src="<c:url value="/resources/js/plugins/location.js" />"></script>
    <script src="<c:url value="/resources/js/plugins/unitegallery.js" />"></script>
    <script src="<c:url value="/resources/js/plugins/ug-theme-tiles.js" />"></script>
</head>
<body class="connUs-profile">
    <%@ include file="/WEB-INF/views/header.jsp" %>
    <%@ include file="/WEB-INF/views/menu.jsp" %>
    
    <div id="connUs-page-wrapper">
    	<div class="connUs-profileTab-cont">
    		<div class="connUs-tab" index="1">Profile</div>
    		<div class="connUs-tab" index="2">Members</div>
    		<div class="connUs-tab" index="3">Photos</div>
    		<div class="connUs-tab" index="4">Communities</div>
    	</div>
    	<div class="connUs-profileData-wrapper"></div>
	    <div class="connUs-feedList-wrapper"></div>
	    <div class="connUs-friendsListAll-wrapper">
	        <div class="connUs-frndstext">Friends</div>
	        <div class="connUs-active-frndstext">Active Friends</div>
	        <div class="connUs-friendsList-innerAll-wrapper"></div>
	        <div class="connUs-friendsList-online-wrapper"></div>
	    </div>
	    <div class="connUs-friendsListRequest-wrapper"></div>
	    <div class="connUs-ptos-wrapper">
	        <form id="connUs-addPtos-uploadCont">
	        	<div class="connUs-addPtosIcon"></div>
	            <input id="connUs-addPtos-upload" type="file" name="file" />
	        </form>
	        <div class="connUs-addPtos-imgCont">
	            <img class="connUs-addPtos-imgDisplay"/>
	            <input class="connUs-addPtos-desc" placeholder="Add description to your image"/>
	            <div class="connUs-addPtos-btn">Add Photo</div>
	        </div>
	        <div class="connUs-ptosList-wrapper"></div>
    	</div>
    	
    	<div class="connUs-comm-wrapper">
    		<div class="connUs-commTab-cont">
	    		<div class="comm-tab" index="1">Liked</div>
	    		<div class="comm-tab" index="2">Members</div>
	    		<div class="comm-tab" index="3">Yours</div>
    		</div>
    		<div class="comm-all-list-cont">
	    		<div class="comm-liked-wrapper"></div>
	    		<div class="comm-member-wrapper"></div>
	    		<div class="comm-yours-wrapper"></div>
	    	</div>
	    		<div class="comm-new-wrapper"></div>
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
    <%@ include file="/WEB-INF/views/communitiesListHb.jsp" %>
    <%@ include file="/WEB-INF/views/communityHb.jsp" %>
</body>
</html>