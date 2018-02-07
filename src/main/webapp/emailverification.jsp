<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Connecting Us</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/Utils/utils.js" />"></script>
    <script src="<c:url value="/resources/js/emailverify.js" />"></script>
    
    <link href="<c:url value="/resources/css/emailverify.css" />" rel="stylesheet">
    <meta name="viewport" content="width=device-width" />
</head>
<body>
    <div class="connUs-header-cont">
        <div class="connUs-header-label">Connecting Us</div>
    </div>
    <div class="connUs-emailVerify-cont">
        <div class="emailVerifyContent">Your account has been activated successfully<br>Click Ok to continue</div>
        <button class="emailVerify-btn">Ok</button>
    </div>
</body>