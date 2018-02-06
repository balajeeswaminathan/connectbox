<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<!-- Begin emoji-picker Stylesheets -->
	    <link href="//localhost/chatClient/css/lib/emoji.css" rel="stylesheet">
	<!-- End emoji-picker Stylesheets -->

    <!-- Begin emoji-picker JavaScript -->
    <script src="//localhost/chatClient/js/lib/config.js"></script>
    <script src="//localhost/chatClient/js/lib/util.js"></script>
    <script src="//localhost/chatClient/js/lib/jquery.emojiarea.js"></script>
    <script src="//localhost/chatClient/js/lib/emoji-picker.js"></script>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyDs8OiIs_wwNnZ_kKeLsIP3aAZv096jXtM"></script>
    <!-- End emoji-picker JavaScript -->

<body>
    <div class="connUs-chatsListhb">
        <%@ include file="/WEB-INF/views/chatsList.jsp" %>
    </div>
    
    <div class="connUs-chatsListMgsthb">
        <%@ include file="/WEB-INF/views/chatsListMgs.jsp" %>
    </div>
    
    <div class="connUs-chatNotification-wrapper"></div>
    
    <div class="connUs-chatBox-outerWrapper">
    	<div class="connUs-chatBox-head-cont">
    	    <div class="connUs-chatBox-online"></div>
        	<div class="connUs-chatBox-head">Chat</div>
        	<div class="connUs-chatBox-location"></div>
       	</div>
        <div class="connUs-chatBox-wrapper">
			<div class="connUs-chatsListMgs-inner-wrapper"></div>
            <div id="map-cont"></div>
			<div class="connUs-chatsListMgs-input-cont">
			     <div class="connUs-smiley"></div>
			    <input type="text" class="connUs-chatsListMgs-input" placeholder="Send a Message" data-emojiable="true"/>
			    <div class="connUs-chatsListMgs-submit">></div>
			</div>
        </div>
    </div>

</body>
</html>