$( document ).ready(function() {
	messageClass.init();
});


var messageClass = {
	
	init : function()
	{
		var _self = this;
		_self.RegisterEvents();
	},
	
	RegisterEvents : function()
	{
		var _self = this;
		
		_self.madeChatListCall();
	},
	
	madeChatListCall : function(){
		var _self = this;
		var userId = _self.getCookie("userId");
		var frndsDomHeight = $(".connUs-friends-wrapper").outerHeight();
		if(frndsDomHeight > 0)
		{
			$(".connUs-friends-wrapper").hide();
		}
		
		var config = {};
		config.callType = "chatList"
		config.type = "get";
		config.data= ({
				"senderId" : userId
		});
		
		var chatListCbk = function(response){
			var obj = JSON.parse(response);
			_self.callHandlebar("#chatsList-hb-template", ".connUs-chatsList-inner-wrapper", obj.properties);
			
			$(".connUs-chatsList-inner-cont").off("click").on("click",function(event){
				_self.madeChatListDataCall(event);
			});
		};
		
		_self.makeAjaxCall(config, chatListCbk);
	},
	
	madeChatListDataCall : function(event){
		var _self = this;
		var userId = _self.getCookie("userId");
		var receiverId = $(event.target).attr("chatListId");
		
		$(".connUs-chatsListMgs-inner-wrapper").attr("activeChatListId", receiverId);
		$(".connUs-chatsListMgs-inner-wrapper").attr("activeChatListId", receiverId);
		var config = {};
		config.callType = "chatListData"
		config.type = "get";
		config.data= ({
				"senderId" : userId,
				"receiverId" : receiverId,
				"pageLevel": 1,
				"limits" : 10
		});
		
		var chatsListCbk = function(response){
			var obj = JSON.parse(response);
			_self.callHandlebar("#chatsListData-hb-template", ".connUs-chatsListMgs-wrapper", obj.properties);
			
			$(".connUs-chatsListMgs-submit").off("click").on("click",function(){
				_self.madeSendChat();
			});
		};
		
		_self.makeAjaxCall(config, chatsListCbk);
	},
	
	madeSendChat : function(){
		var _self = this;
		var userId = _self.getCookie("userId");
		var userName = _self.getCookie("userName");
		var receiverId = $(".connUs-chatsList-inner-cont").attr("chatListId");
		var receiverName = $(".connUs-chatsList-name").html();
		var message = $(".connUs-chatsListMgs-input").val();
		
		var config = {};
		config.callType = "sendChat"
		config.type = "get";
		config.data= ({
			"senderId" : userId,
			"senderName" : userName,
			"receiverId" : receiverId,
			"receiverName" : receiverName,
			"message" : message
		});
		
		_self.makeAjaxCall(config);
	},
	
	makeAjaxCall : function(config, cbk){
		 
		 config.url = streamDomain + config.callType;
		 
		 config.success = cbk;
		 $.ajax(config);
	 },
	
	getCookie : function(cname)
    {
    	var skCookieName = cname + "=";
    	var skAllCookie = document.cookie.split(';');
    	for(var i=0; i<skAllCookie.length; i++) 
    	{
    	  var tempCookie = skAllCookie[i].trim();
    	  if (tempCookie.indexOf(skCookieName)==0) return tempCookie.substring(skCookieName.length,tempCookie.length);
    	}
    	return "";
    },
	 
	callHandlebar : function(handlebarTemp, fillDom, data){
		// Grab the template script
		var theTemplateScript = $(handlebarTemp).html();
		// Compile the template
		var theTemplate = Handlebars.compile(theTemplateScript);
		
		var theCompiledHtml = theTemplate(data);
		 $(fillDom).html(theCompiledHtml);
	}
}