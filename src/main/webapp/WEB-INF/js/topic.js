$( document ).ready(function() {
	topicClass.init();
});

var topicClass = {

	init : function()
	{
		var _self = this;		
		_self.RegisterEvents();
	},
	
	RegisterEvents : function()
	{
		var _self = this;

		_self.getTopicData();
	},
	
	getTopicData : function(){
		var _self = this;
		var userId = _self.getCookie("userId");
		var topicId =  window.location.search.replace("?","");

		var config = {};
		config.callType = "getTopic"
		config.type = "POST";
		config.data= ({
			"topicId" : topicId
		});
		
		var getTopicCbk = function(response){
			var obj = JSON.parse(response);
			$(".connUs-topicHead-img").attr("src", obj.properties.data.imgUrl);
			$(".connUs-topicHead-name").html(obj.properties.data.name);
		};
		
		_self.makeAjaxCall(config, getTopicCbk);
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