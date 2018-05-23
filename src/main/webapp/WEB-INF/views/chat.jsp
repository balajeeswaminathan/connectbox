<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<!-- Begin emoji-picker Stylesheets -->
	    <link href="<c:url value="/resources/css/emoji.css" />" rel="stylesheet">
	<!-- End emoji-picker Stylesheets -->

    <!-- Begin emoji-picker JavaScript -->
    <script src="<c:url value="/resources/js/lib/config.js" />"></script>
    <script src="<c:url value="/resources/js/lib/util.js" />"></script>
    <script src="<c:url value="/resources/js/lib/jquery.emojiarea.js" />"></script>
    <script src="<c:url value="/resources/js/lib/emoji-picker.js" />"></script>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyDs8OiIs_wwNnZ_kKeLsIP3aAZv096jXtM"></script>
    <!-- End emoji-picker JavaScript -->

<body>
    <div class="connUs-chatsListhb">
        <%@ include file="/WEB-INF/views/chatListHb.jsp" %>
    </div>
    
    <div class="connUs-chatsListMgsthb">
        <%@ include file="/WEB-INF/views/chatsListMgs.jsp" %>
    </div>
    
    <div class="connUs-chatNotification-list"></div>
    
    <div class="connUs-chatBox-outerWrapper">
    	<div class="connUs-chatBox-head-cont">
    		<div class="connUs-chatBox-back-icon"></div>
    	    <div class="connUs-chatBox-online"></div>
    	    <img class="connUs-chatBox-img" src=""></img>
        	<div class="connUs-chatBox-name-cont">
	        	<div class="connUs-chatBox-name"></div>
	        	<div class="connUs-chatBox-online-txt">online</div>
	        </div>
       	</div>

        <div class="connUs-chatBox-wrapper">
			<div class="connUs-chatBox-inner-wrapper"></div>
            <div id="connUs-chatBox-map-cont"></div>
            
            <div class="connUs-chatBox-location-cont">
				<div class="connUs-chatBox-location"></div>
			</div>
			<div class="connUs-chatBox-input-cont">
				<div class="connUs-chatBox-plus-icon"></div>
				<input type="text" class="connUs-chatBox-input" placeholder="Type a message" data-emojiable="true"/>
				<div class="connUs-smiley"></div>
			</div>
			<div class="connUs-chatBox-send-cont">
				<div class="connUs-chatBox-send"></div>
			</div>
        </div>
    </div>

</body>
</html>