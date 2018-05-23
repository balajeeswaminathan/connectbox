$( document ).ready(function() {
	chatClass.init();
	chatClass.audio = new Audio('/WEB-INF/audio/solemn.mp3');
	receiverId = "";
});


var chatClass = {
	
	init : function()
	{
		var _self = this;
		_self.RegisterEvents();
		utilClass.registerEmoji();
	},
	
	RegisterEvents : function()
	{
		var _self = this;
		
		$(".connUs-header-chat-icon").off("click").on("click",function(){
			_self.showdropDownBox();
			_self.madeChatListCall();
		});
		
		_self.getChatNotification();
	},

	getChatNotification : function(){
		var _self = this;
		
		setInterval(function(){
			var config = {};
			config.callType = "getChatNotification"
			config.type = "POST";
			config.data= ({
				"userId" : userId
			});
			
			var getChatNotificationCbk = function(response){
				var obj = JSON.parse(response);
				
				var msgCount = obj.properties.data;
				if(msgCount > 0)
				{
				    chatClass.audio.play();
					$(".connUs-header-chatCount-cont").addClass("showCount");
					$(".connUs-header-chatCount-cont").html(msgCount);
				}
				else
				{
					$(".connUs-header-chatCount-cont").removeClass("showCount");
				}
			};
			
			utilClass.makeAjaxCall(config, getChatNotificationCbk);
		}, 2000);
	},
	
	showdropDownBox : function(){
		$("body").addClass("connUs-chatDropBoxActive");
		utilClass.showMask();
		
		$(".connUs-Mask").off("click").on("click",function(){
			utilClass.hideMask();
			$("body").removeClass("connUs-chatDropBoxActive");
		});
	},
	
	madeChatListCall : function(){
		var _self = this;
		var frndsDomHeight = $(".connUs-friends-wrapper").outerHeight();
		if(frndsDomHeight > 0)
		{
			$(".connUs-friends-wrapper").hide();
		}
		
		var config = {};
		config.callType = "chatList"
		config.type = "POST";
		config.data= ({
				"senderId" : userId,
				"clientTZ" : utilClass.clientTZ
		});
		
		var chatListCbk = function(response){
			var obj = JSON.parse(response);
			utilClass.callHandlebar("#chatsList-hb-template", ".connUs-chatNotification-list", obj.properties);
			
			$(".connUs-chatsList-cont").off("click").on("click",function(event){
				_self.madeChatListDataCall(event);
			});
		};
		
		utilClass.makeAjaxCall(config, chatListCbk);
	},
	
	madeChatListDataCall : function(event){
		var _self = this;
		receiverId = $(event.target).attr("chatListId");
		
		utilClass.hideMask();
		$("body").removeClass("connUs-chatDropBoxActive");
		$(".connUs-dropBoxMask").detach();
		if(typeof(receiverId) == "undefined")
		{
			receiverId = $(event.target).parent().attr("chatListId");
		}
		if(typeof(receiverId) == "undefined")
		{
			receiverId = $(event.target).parent().parent().attr("chatListId");
		}
		
		$(".connUs-chatBox-wrapper").attr("receiverId", receiverId);
		_self.getChatListData();
		_self.removeChatNotification();
	},
	
	getChatListData : function()
	{
		var _self = this;
		var config = {};
		config.callType = "chatListData"
		config.type = "POST";
		config.data= ({
				"senderId" : userId,
				"receiverId" : receiverId,
				"pageLevel" : 1, 
				"limits": 10
		});
		var isLast = false;
		
		var chatsListDataCbk = function(response){
			var obj = JSON.parse(response);
			isLast = obj.properties.data.isLast;
			var chatName = obj.properties.data.userName;
			var chatImg = obj.properties.data.imgPath;
			chatImg = chatImg ? chatImg : "https://res.cloudinary.com/connectbox/image/upload/v1526567231/male-profile.png";
			
			if(config.data.pageLevel > 1)
			{
				utilClass.callHandlebar("#chatsListData-hb-template", ".connUs-chatBox-inner-wrapper", obj.properties, false, true);
			}
			else
			{
				utilClass.callHandlebar("#chatsListData-hb-template", ".connUs-chatBox-inner-wrapper", obj.properties);
			}
			
			if(config.data.pageLevel == 1)
			{
				$(".connUs-chatBox-inner-wrapper").animate({ scrollTop: $(document).height() }, 1000);
			    var onlineMemberCbk = function(response){
		            var obj = JSON.parse(response);
		            if(obj.properties.data.onlineMembers.length == 0)
	                {
	                    $(".connUs-chatBox-online").hide();
	                }
		        };
		        
			    utilClass.getOnlineMembers(receiverId, onlineMemberCbk);
			    
			    $(".connUs-chatBox-img").attr("src", chatImg);
				$(".connUs-chatBox-name").html(chatName);
				$("body").addClass("connUs-chatBoxActive");
				
				$(".connUs-chatBox-plus-icon").off("click").on("click",function(){
					$(".connUs-chatBox-location-cont").toggle();
				});
				
				$(".connUs-chatBox-send-cont").off("click").on("click",function(){
					_self.madeSendChat();
				});
				
				$(".connUs-chatBox-head").off("click").on("click",function(){
					$(".connUs-chatBox-wrapper").toggleClass("chat-close");
				});

				$(".connUs-chatBox-location").off("click").on("click",function(){
					_self.getCurrentLocation();
				});
				
				$(".connUs-chatBox-inner-wrapper").scroll(function() {
					if($(".connUs-chatBox-inner-wrapper").scrollTop() + $(".connUs-chatBox-inner-wrapper").height() == $(".connUs-chatBox-inner-wrapper").height()) {
						if(!isLast)
						{
							config.data.pageLevel++;
							_self.scrollTop(config, chatsListDataCbk);
						}
					}
				});
				
				$(".connUs-smiley").off("click").on("click",function(){
					  _self.renderSmileys();
				});
			}
		};
		utilClass.makeAjaxCall(config, chatsListDataCbk);
	},
	
	renderSmileys : function()
	{
		$(".connUs-chatBox-outerWrapper .emoji-menu").toggle();
	},
	
	removeChatNotification : function()
	{
		var senderId = $(".connUs-chatBox-wrapper").attr("receiverId");
		var config = {};
		config.callType = "removeChatNotification"
		config.type = "POST";
		config.data= ({
				"userId" : userId,
				"senderId" : senderId
		});

		var removeChatNotifCbk = function(response){
			var obj = JSON.parse(response);
			var msgCount = obj.properties.data;
			if(msgCount > 0)
			{
				$(".connUs-header-chatCount-cont").addClass("active");
				$(".connUs-header-chatCountLabel").html(msgCount);
			}
			else
			{
				$(".connUs-header-chatCount-cont").removeClass("active");
			}
			$(".connUs-header-chatCountLabel").html(msgCount);
		};

		utilClass.makeAjaxCall(config, removeChatNotifCbk);
	},
	
	madeSendChat : function(){
		var _self = this;
		var userName = $(".connUs-profile-name").html();
		var receiverId = $(".connUs-chatBox-wrapper").attr("receiverId");
		var receiverName = $(".connUs-chatBox-name").html();
		var message = $($(".connUs-chatBox-input")[1]).html();
		var commType = "";
		
		if($(".connUs-chatBox-outerWrapper").hasClass("chat-map-active"))
		{
			commType = "map";
			message = $("#connUs-chatBox-map-cont").attr("address");
		}
		
		var config = {};
		config.callType = "sendChat";
		config.type = "get";
		config.data= ({
			"senderId" : userId,
			"senderName" : userName,
			"receiverId" : receiverId,
			"receiverName" : receiverName,
			"message" : message,
			"commType" : commType
		});
		
		var sendChatCbk = function(){
			_self.getChatListData();
		};
		utilClass.makeAjaxCall(config, sendChatCbk);
	},
	
	getCurrentLocation : function(){
		var _self = this;
		var address = "";

		$(".connUs-chatBox-outerWrapper").addClass("chat-map-active");
		navigator.geolocation.getCurrentPosition(function(position) {
	            var geocoder = new google.maps.Geocoder();
	            geocoder.geocode({
	              "location": new google.maps.LatLng(position.coords.latitude, position.coords.longitude)
	            },
	            function(results, status) {
				 _self.mapInitialize(position.coords.latitude, position.coords.longitude);
	              if (status == google.maps.GeocoderStatus.OK)
	              {
	            	  address = results[0].formatted_address;
	            	  $("#connUs-chatBox-map-cont").attr("address", address);
	              }
	              else
	              {
	            	  $("#error").append("Unable to retrieve your address<br />");
	              }
	            });
	     });
	},
	
	mapInitialize : function(latitude, longitude){
		var latlng = new google.maps.LatLng(latitude, longitude);

        var myOptions = {
          zoom: 16,
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          mapTypeControl: false
        };
        var map = new google.maps.Map(document.getElementById("connUs-chatBox-map-cont"),myOptions);

        var marker = new google.maps.Marker({
          position: latlng,
          map: map,
          title:"location : Dublin"
        });
	},
	
	scrollTop : function(config, chatsListDataCbk){
		var _self = this;

		utilClass.makeAjaxCall(config, chatsListDataCbk);
	}
}