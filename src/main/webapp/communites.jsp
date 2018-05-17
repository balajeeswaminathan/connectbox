<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width" />
    <title>connecting Us</title>
    
    <link href="<c:url value="/resources/css/header.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/chat.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/feed.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/photos.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/communities.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/unite-gallery.css" />" rel="stylesheet">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
    <script src="<c:url value="/resources/js/Utils/utils.js" />"></script>
    <script src="<c:url value="/resources/js/menu.js" />"></script>
    <script src="<c:url value="/resources/js/chat.js" />"></script>
    <script src="<c:url value="/resources/js/feed.js" />"></script>
    <script src="<c:url value="/resources/js/communities.js" />"></script>
    <script src="<c:url value="/resources/js/photos.js" />"></script>
    
    <script src="<c:url value="/resources/js/plugins/unitegallery.js" />"></script>
    <script src="<c:url value="/resources/js/plugins/ug-theme-tiles.js" />"></script>
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
    <%@ include file="/WEB-INF/views/chatsListMgs.jsp" %>
    <%@ include file="/WEB-INF/views/likeListHb.jsp" %>
    <%@ include file="/WEB-INF/views/cmntsListHb.jsp" %>
    <%@ include file="/WEB-INF/views/viewPhotosHb.jsp" %>
    <%@ include file="/WEB-INF/views/communityHb.jsp" %>
</body>
</html>