$( document ).ready(function() {
	userId = utilClass.getCookie("userId");
	headerAndMenuClass.init();
});


var headerAndMenuClass = {
	
	init : function()
	{
		var _self = this;
		_self.userData();
		_self.RegisterEvents();
	},
	
	RegisterEvents : function()
	{
		var _self = this;
		
		$(".connUs-menu").off("click").on("click",function(events){
			_self.menuNavigation(events);
		});

		$(".pancake-cont, .connUs-Mask").off("click").on("click",function(){
			_self.openPancake();
		});
	},
	
	userData : function()
    {
		var userId = utilClass.getCookie("userId");
	    $(".connUs-profile-name").attr("userId",userId);
	   
	   var config = {};
		config.callType = "home"
		config.type = "get";
		config.data= ({
			"userId" : userId
		});
	   
		var homeCbk = function(response)
		{
			var obj = JSON.parse(response);
			var img = obj.properties.data[0].imgPath;
			var userName = obj.properties.data[0].username;
			$(".connUs-profile-name").text(userName);
			utilClass.userName = userName;
			if(img)
			{
				$(".connUs-profile-img").attr("src", img);
			}
			
			setTimeout(function(){
				if($("body").hasClass("connUs-myProfile"))
				{
					var groupId = utilClass.getQueryParam("groupId");
					if(!groupId)
					{
						$($(".connUs-menu")[0]).addClass("active");
					}
					if(groupId == "members")
					{
						$($(".connUs-menu")[1]).addClass("active");
					}
					else if(groupId == "photos")
					{
						$($(".connUs-menu")[2]).addClass("active");
					}
					else if(groupId == "communities")
                    {
                        $($(".connUs-menu")[4]).addClass("active");
                    }
				}
			},400);
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
		var myProfileUrl = "http://localhost:8080/chatUI/profile.jsp?pId="+profileId;
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
		else if(type == 4)
        {
            myProfileUrl += "&groupId=communities";
        }
		window.location.href = myProfileUrl;
	},
	
	openPancake: function(){
		var _self = this;
		if(!$("body").hasClass("pancake-active"))
		{
			$("body").addClass("pancake-active");
			utilClass.showMask("#000", 0.7);
			_self.RegisterEvents();
		}
		else
		{
			$("body").removeClass("pancake-active");
			utilClass.hideMask();
		}
	}
}