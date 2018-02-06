$( document ).ready(function() {
	photosClass.init();
});


var photosClass = {
	
	init : function()
	{
		var _self = this;		
		_self.RegisterEvents();
	},
	
	RegisterEvents : function()
	{
		var _self = this;
		
		$("#connUs-addPtos-upload").change(function(){
			_self.uploadPhotos();
		});
		
		$(".connUs-addPtos-btn").off("click").on("click",function(){
			_self.addPhotos();
		});
	},
	
	uploadPhotos : function(){
		var _self = this;
		var userId = _self.getCookie("userId");
		var formData = new FormData(document.getElementById("connUs-addPtos-uploadCont"));
		formData.append("userId", userId);
		formData.append("imgType", "photos");

		var photosImgCbk = function(response){
			var obj = JSON.parse(response);
			$(".connUs-addPtos-imgCont").addClass("active");
			$(".connUs-addPtos-imgDisplay").attr("src", obj.properties.data.imgPath);
		};
		
		utilClass.uploadImage(formData, photosImgCbk)
	},
	
	addPhotos : function(){
		var _self = this;
		var userId = _self.getCookie("userId");
		var desc = $(".connUs-addPtos-desc").val();
		var imgUrl = $(".connUs-addPtos-imgDisplay").attr("src");
		
		var config = {};
		config.callType = "addPhotos"
		config.type = "POST";
		config.data= ({
				"userId" : userId,
				"desc" : desc,
				"imgUrl" : imgUrl
		});
		
		_self.makeAjaxCall(config);
	},
	
	getPhotos : function(){
		var _self = this;
		var userId = _self.getCookie("userId");
		
		$("#connUs-page-wrapper").removeClass();
		$("#connUs-page-wrapper").addClass("photos-active");
		var photosWrapperHeight = $(".connUs-ptosList-wrapper").outerHeight();

		if(photosWrapperHeight == 0)
		{
			var config = {};
			config.callType = "getPhotos"
			config.type = "POST";
			config.data= ({
				"userId" : userId,
				"profileId" : profileClass.profileData.profileId,
				"clientTZ" : utilClass.clientTZ
			});
			
			var getPhotosCbk = function(response){
				var obj = JSON.parse(response);
				_self.callHandlebar("#viewPhotos-hb-template", ".connUs-ptosList-wrapper", obj.properties);
				
				jQuery(document).ready(function(){
					jQuery("#gallery").unitegallery();
				});
			}
			
			_self.makeAjaxCall(config, getPhotosCbk);
		}
	},
	
	givePhotoLike : function(events){
		var _self = this;
		var userId = _self.getCookie("userId");
		var imgId = $(events.target).closest(".connUs-imgSliderDesc").attr("index");
		
		var config = {};
		config.callType = "giveLike";
		config.type = "POST",
		config.data = ({
			"userId" : userId,
			"commId" : "",
			"userFeedId" : profileClass.profileData.profileId,
			"feedId" : imgId
		});
		
		_self.makeAjaxCall(config);
	},
	
	addPhotoCmnts : function(events){
		var _self = this;
		var userId = _self.getCookie("userId");
		var imgId = $(events.target).closest(".connUs-imgSliderDesc").attr("index");
		var comments = $(events.target).siblings().val();
		
		var config = {};
		config.callType = "addComment";
		config.type = "POST",
		config.data = ({
			"userId" : userId,
			"commId" : "",
			"userFeedId" : profileClass.profileData.profileId,
			"feedId" : imgId,
			"comments" : comments
		});
		
		_self.makeAjaxCall(config);
	},
	
	getPhotoLikes : function(events){
		var _self = this;
		var userId = _self.getCookie("userId");
		var imgId = $(events.target).closest(".connUs-imgSliderDesc").attr("index");
		
		var config = {};
		config.callType = "getLikeOrCmnts";
		config.type = "POST",
		config.data = ({
			"userId" : userId,
			"id" : imgId,
			"type" : "like",
			"clientTZ" : utilClass.clientTZ
		});
		
		getPhotoLikeCbk = function(response){
			var obj = JSON.parse(response);
			_self.callHandlebar("#likeListData-hb-template", ".connUs-ptosLikeOrCmnts-wrapper", obj.properties);
			_self.showPopUp(".connUs-ptosLikeOrCmnts-wrapper");
			_self.setCenter(".connUs-ptosLikeOrCmnts-wrapper");
			
			$(window).resize(function(){
				_self.setCenter(".connUs-ptosLikeOrCmnts-wrapper");
			});
		};
		
		_self.makeAjaxCall(config, getPhotoLikeCbk);
	},
	
	getPhotoCmnts : function(events){
		var _self = this;
		var userId = _self.getCookie("userId");
		var imgId = $(events.target).closest(".connUs-imgSliderDesc").attr("index");
		
		var config = {};
		config.callType = "getLikeOrCmnts";
		config.type = "POST",
		config.data = ({
			"userId" : userId,
			"id" : imgId,
			"type" : "comments",
			"clientTZ" : utilClass.clientTZ
		});
		
		getPhotoCmntsCbk = function(response){
			var obj = JSON.parse(response);
			_self.callHandlebar("#cmntsListData-hb-template", ".connUs-ptosLikeOrCmnts-wrapper", obj.properties);
			_self.showPopUp(".connUs-ptosLikeOrCmnts-wrapper");
			_self.setCenter(".connUs-ptosLikeOrCmnts-wrapper");
			
			$(window).resize(function(){
				_self.setCenter(".connUs-ptosLikeOrCmnts-wrapper");
			});
		};
		
		_self.makeAjaxCall(config, getPhotoCmntsCbk);
	},
	
	showPopUp : function(){
		$("body").addClass("connUs-popUpActive");
		$("body").append("<div class='connUs-popUpMask'><div>");
		$(".connUs-popUpMask").off("click").on("click",function(){
			$("body").removeClass("connUs-popUpActive");
			$(".connUs-popUpMask").detach();
		});
	},
	
	setCenter : function(popUpDom) {
		$(popUpDom).css("top", ($(window).height()/2 - $(popUpDom).height()/2) + "px");
		$(popUpDom).css("left", ($(window).width()/2 - $(popUpDom).width()/2) + "px");
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