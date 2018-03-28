<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Connecting Us</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/Utils/utils.js" />"></script>
    <script src="<c:url value="/resources/js/index.js" />"></script>
    <!--<script src="<c:url value="/resources/js/plugins/location.js" />"></script>-->
    <script src="<c:url value="/resources/js/plugins/countrystatecity.js" />"></script>
    
    <link href="<c:url value="/resources/css/index.css" />" rel="stylesheet">
    <meta name="viewport" content="width=device-width" />
</head>
<body>
    <div class="connUs-header-cont">
        <div class="connUs-header-label">Connect box</div>
        <div class="connUs-intro">Join now to make new friends, add photos, add youtube videos, chat, share with friends, and much more.</div>
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
</body>
</html>