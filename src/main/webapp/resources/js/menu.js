$( document ).ready(function() {
	userId = utilClass.getCookie("userId");
	menuClass.init();
});


var menuClass = {
	
	init : function()
	{
		var _self = this;
		_self.getuserData();
		_self.RegisterEvents();
	},
	
	RegisterEvents : function()
	{
		var _self = this;
		
		$(".connUs-menu").off("click").on("click",function(events){
			_self.menuNavigation(events);
		});

		$(".menu-icon, .connUs-menu-close").off("click").on("click",function(){
			_self.openMenu();
		});
	},
	
	getuserData : function()
    {
		var userId = utilClass.getCookie("userId");
	    $(".connUs-profile-name").attr("userId",userId);
	   
	   var config = {};
		config.callType = "userBasicInfo"
		config.type = "post";
		config.data= ({
			"userId" : userId
		});
	   
		var homeCbk = function(response)
		{
			var obj = JSON.parse(response);
			var img = obj.properties.data.imgPath;
			var userName = obj.properties.data.userName;
			$(".connUs-profile-name").text(userName);
			utilClass.userName = userName;
			if(img)
			{
				$(".connUs-profile-img").attr("src", img);
			}
		};
		
		utilClass.makeAjaxCall(config, homeCbk);
    },
	
	menuNavigation : function(events){
		var _self = this;
		var menuDom = events.target;
		var index = $(menuDom).attr("index");
		$(".connUs-menu").removeClass("active");
		$(menuDom).addClass("active");
		_self.gotoMyProfile(index);
	},
	
	gotoMyProfile : function(type)
	{
		var _self = this;
		var profileId = utilClass.getCookie("userId");
		var myProfileUrl = "profile.jsp?pId="+profileId;
		if(type == 1)
		{
			myProfileUrl += "&groupId=members";
		}
		else if(type == 2)
		{
			myProfileUrl += "&groupId=photos";
		}
		else if(type == 3)
		{
			myProfileUrl += "&groupId=topics";
		}
		window.location.href = myProfileUrl;
	},
	
	openMenu: function(){
		if($("body").hasClass("menu-active"))
		{
			utilClass.hideMask();
			$("body").removeClass("menu-active");
		}
		else
		{
			utilClass.showMask();
			$("body").addClass("menu-active");
		}
		
	}
}