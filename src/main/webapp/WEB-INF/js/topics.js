$( document ).ready(function() {
	topicsClass.init();
});

var topicsClass = {

	init : function()
	{
		var _self = this;		
		_self.RegisterEvents();
	},
	
	RegisterEvents : function()
	{
		var _self = this;

		$(".connUs-addTopics-btn").off("click").on("click",function(){
			_self.addTopic();
		});
		
		$("#connUs-topics-imgUpload").change(function(){
			_self.uploadImage();
		});

		$(".connUs-topicsMenu-member, .connUs-topicsMenu-liked, .connUs-topicsMenu-owner").off("click").on("click",function(events){
			_self.getTopics(events);
		});
		
		$(".connUs-topicsMenu-member").trigger("click");
	},
	
	addTopic: function(){
		var _self = this;
		var userId = _self.getCookie("userId");
		var topicName = $(".connUs-addTopics-name").val();
		var imgUrl = $(".connUs-addTopicsImgDisplay").attr("src") ? $(".connUs-addTopicsImgDisplay").attr("src") : "";

		var config = {};
		config.callType = "addTopic"
		config.type = "POST";
		config.data= ({
			"topicName" : topicName,
			"imgUrl" : imgUrl,
			"userId" : userId
		});
		
		_self.makeAjaxCall(config);
	},
	
	uploadImage : function(){
		var _self = this;
		var userId = _self.getCookie("userId");
		var formData = new FormData(document.getElementById("connUs-addTopics-img"));
		formData.append("userId", userId);
		formData.append("imgType", "topicImg");
		
		var config = {};
		config.callType = "uploadImage"
		config.type = "POST";
		config.enctype = 'multipart/form-data';
		config.processData =  false;
		config.contentType =  false;
		config.data = formData;
		
		var profileImgCbk = function(response){
			var obj = JSON.parse(response);
			$(".connUs-addTopicsImgDisplayCont").show();
			$(".connUs-addTopicsImgDisplay").attr("src", obj.properties.data.imgPath);
		};
		
		_self.makeAjaxCall(config,profileImgCbk);
	},
	
	getTopics : function(events){
		var _self = this;
		var userId = _self.getCookie("userId");
		var topicType = $(events.target).attr("topicType");

		var config = {};
		config.callType = "getTopics"
		config.type = "POST";
		config.data= ({
			"userId" : userId,
			"topicType" : topicType
		});

		var topicsListCbk = function(response){
			var obj = JSON.parse(response);
			_self.topicsListCbk(obj, topicType);
		};
		
		_self.makeAjaxCall(config, topicsListCbk);
	},
	
	topicsListCbk : function(obj, topicType){
		var _self = this;

		_self.callHandlebar("#topicsListData-hb-template", ".connUs-topic-" + topicType + "-wrapper", obj.properties);
		$(".connUs-topic-member-wrapper, .connUs-topic-liked-wrapper, .connUs-topic-owner-wrapper").removeClass("active");
		$(".connUs-topic-" + topicType + "-wrapper").addClass("active");
		
		$(".connUs-topicsMenu-member, .connUs-topicsMenu-liked, .connUs-topicsMenu-owner").removeClass("topicActive");
		$(".connUs-topicsMenu-" + topicType).addClass("topicActive");
		if(topicType == "owner")
		{
			$(".connUs-addTopics-cont").addClass("active");
		}
		else
		{
			$(".connUs-addTopics-cont").removeClass("active")
		}
		
		$(".connUs-topicsList-innerCont").off("click").on("click",function(events){
			_self.navigateToTopic(events);
		});
	},
	
	navigateToTopic : function(events){
		var topicId = $(events.target).parent().attr("topicId");
		window.location.href = "http://localhost:8080/chatUI/topic.jsp?"+topicId;
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