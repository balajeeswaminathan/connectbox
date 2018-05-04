<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Connect Box</title>
    <meta name="viewport" content="width=device-width" />
    <link href="https://fonts.googleapis.com/css?family=Lily Script One" rel="stylesheet">    
    <link href="<c:url value="/resources/css/index.css" />" rel="stylesheet">
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <!--<script src="<c:url value="/resources/js/plugins/location.js" />"></script>-->
    <script src="<c:url value="/resources/js/Utils/utils.js" />"></script>
    <script src="<c:url value="/resources/js/index.js" />"></script>
    <script src="<c:url value="/resources/js/plugins/countrystatecity.js" />"></script>
</head>
<body>
    <div class="connUs-header-cont">
        <div class="connUs-header-label">Connect box</div>
        <div class="connUs-intro">Friends, feeds, chats, pictures and much more</div>
        <div class="connUs-join-now">Join Now</div>
    </div>
    
    <div class="connUsform">
      
	      <ul class="tab-group loginOrRegister">
	        <li class="login">Sign In</li>
	        <li class="register">Sign Up</li>
	      </ul>
	      
	      <div class="tab-content">
	         <%@ include file="/WEB-INF/views/login.jsp" %>
	         <%@ include file="/WEB-INF/views/register.jsp" %>
	      </div>
      
    </div>
    <div class="connUs-grabApp-txt">Grab the app.</div>
    <div class="connUs-playstore-icon"></div>
    <div class="connUs-copyright">© 2018 Connect Box</div>
</body>
</html>