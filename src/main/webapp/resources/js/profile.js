$( document ).ready(function() {
    profileClass.profileData = {},
    profileClass.init();
});


var profileClass = {
	
	init : function()
	{
		var _self = this;
		
		var profileCbk = function(response){
			var obj = JSON.parse(response);
			_self.profileCbkFn(obj);
			_self.RegisterEvents();
		};

		_self.profileCall(profileCbk);
	},

	RegisterEvents : function()
	{
		var _self = this;
		$(".connUs-connect-btn").off("click").on("click",function(events){
			_self.requestFriends(events);
		});

		$(".connUs-accept-friend").off("click").on("click",function(){
			_self.acceptFriends();
		});
		
		$(".connUs-msg").off("click").on("click",function(){
			_self.madeChatListDataCall();
		});
		
		$(".profileImg-edit-btn").off("click").on("click",function(){
		    _self.displayPhotos();
		});
		
		$("#profileImgfile").change(function(){
			_self.uploadImage();
		});
		
		$(".profile-edit-icon").off("click").on("click",function(){
			_self.enabelEditOption();
		});
		
		$(".connUs-tab").off("click").on("click",function(events){
			_self.selectTabOption(events);
		});
		
		$(".profile-edit-saveBtn").off("click").on("click",function(events){
		    _self.updateProfile();
		});
	},
	
	profileCbkFn : function(obj)
	{
		var _self = this;
		profileClass.profileData = obj.properties.data.profileDataList;
		$(".connUs-basicInfo").attr("profileId", profileClass.profileData.profileId);
		
		if(obj.properties.data.profileDataList.myProfile)
		{
			$("body").addClass("connUs-myProfile");
		}
		var groupId = utilClass.getQueryParam("groupId");
		if(!groupId)
		{
			$($(".connUs-tab")[0]).addClass("active");
			_self.showProfile();
		}
		if(groupId == "members")
		{
			$($(".connUs-tab")[1]).addClass("active");
			_self.makeFriendsCall(false);
		}
		else if(groupId == "photos")
		{
			$($(".connUs-tab")[2]).addClass("active");
			photosClass.getPhotos();
		}
		else if(groupId == "communities")
        {
            $($(".connUs-tab")[3]).addClass("active");
            _self.showCommunities();
        }
		utilClass.callHandlebar("#profileData-hb-template", ".connUs-profileData-wrapper", obj.properties);
		print_country("connUs-country", obj.properties.data.profileDataList.country);
		print_state('connUs-state',"", obj.properties.data.profileDataList.countryState);
		
		feedClass.getFeedCall();
	},
	
	profileCall : function(cbk)
	{
		var _self = this;
		var userId = utilClass.getCookie("userId");
		var profileData =  decodeURIComponent(utilClass.getQueryParam("pId")); //TODO
		
		var config = {};
		config.callType = "profile";
		config.type = "get";
		config.data= ({
				"userId" : userId,
				"profileId" : profileData
		});
		
		utilClass.makeAjaxCall(config, cbk);
	},
	
	showAndHideMoreOption : function(events)
	{
		var feedId = $(events.target).parent().attr("feedid");
		if(!$("#more-cont-" + feedId).hasClass("active"))
		{
			$("#more-cont-" + feedId).addClass("active");
		}
		else
		{
			$("#more-cont-" + feedId).removeClass("active");
		}
	},
	
	enabelEditOption : function()
	{
		$(".connUs-profileData-cont").addClass("edit_active");
	},
	
	requestFriends : function(events)
	{
		var _self = this;
		var userId = utilClass.getCookie("userId");
		var userName = utilClass.getCookie("userName");
		var profileId = profileClass.profileData.profileId;
		var profileName = profileClass.profileData.profileName;
		var Dom = $(events.target);
		var type = Dom.attr("type");
		
		var config = {};
		config.type = "get";
		config.data= ({
				"requesterId" : userId,
				"requesterName" : userName,
				"accepterId" : profileId,
				"accepterName" : profileName
		});
		
		if(type == "connect")
		{
			config.callType = "requestFriends";
		}
		else if(type == "accept")
		{
			config.callType = "acceptFriends";
		}
			
		var frndsCbk = function(response){
			var obj = JSON.parse(response);
			var status = obj.properties.status;
			if(status == -1)
			{
				Dom.html("Requested");
				Dom.attr("type", "requested");
			}
			else if(status == 1)
			{
				Dom.html("Connected");
				Dom.attr("type", "accept");
			}
		};
		
		utilClass.makeAjaxCall(config, frndsCbk);
	},
	
	acceptFriends : function(cbk)
	{
		var _self = this;
		var userId = utilClass.getCookie("userId");
		var userName = utilClass.getCookie("userName");
		var profileId = profileClass.profileData.profileId;
		var profileName = profileClass.profileData.profileName;
		
		var config = {};
		config.callType = "acceptFriends"
		config.type = "get";
		config.data= ({
				"accepterId" : userId,
				"accepterName" : userName,
				"requesterId" : profileId,
				"requesterName" : profileName
		});
		
		var acceptFrndsCbk = function(response){
			var obj = JSON.parse(response);
			var status = obj.properties.status;
			if(status == 0)
			{
				$(".connUs-friends-tab").addClass("connUs-already-friend");
				$(".connUs-friends").text("Friends");
			}
		};
		
		utilClass.makeAjaxCall(config, acceptFrndsCbk);
	},
	
	madeChatListDataCall : function(){
		var _self = this;
		var userId = utilClass.getCookie("userId");
		var receiverId = profileClass.profileData.profileId;
		
		var config = {};
		config.callType = "chatListData"
		config.type = "get";
		config.data= ({
				"senderId" : userId,
				"receiverId" : receiverId,
				"pageLevel" : 1, 
				"limits": 10
		});
		
		var chatsListCbk = function(response){
			var obj = JSON.parse(response);
			utilClass.callHandlebar("#chatsListData-hb-template", ".connUs-chatsListMgs-inner-wrapper", obj.properties);
			
			$(".connUs-chatBox-head").html(profileClass.profileData.profileName);
			$(".connUs-chatBox-wrapper").attr("receiverId", profileClass.profileData.profileId);
			$("body").addClass("connUs-chatBoxActive");
			
			$(".connUs-chatsListMgs-submit").off("click").on("click",function(){
				chatClass.madeSendChat();
			});
		};
		
		utilClass.makeAjaxCall(config, chatsListCbk);
	},
	
	uploadImage : function(){
		var _self = this;
		var userId = utilClass.getCookie("userId");
		if(utilClass.checkImageSize(document.getElementById("profileImgfile")))
		{
    		var formData = new FormData(document.getElementById("profileImg-edit"));
    		formData.append("userId", userId);
    		formData.append("imgType", "profileImg");
    		
    		var profileImgCbk = function(response){
    			var obj = JSON.parse(response);
    			var imgUrl = obj.properties.data.imgPath;
    			_self.updateProfilePicture(imgUrl);
    		};
    		
    		utilClass.uploadImage(formData, profileImgCbk);
		}
		else
		{
		    utilClass.showAlertBox("Please upload image within 5MB size only!");
		    utilClass.showMask("#000");
		}
	},
	
	updateProfile: function(){
	    var userName =  $(".profile-name").val();
	    var dob =  $(".profile-dob").val();
	    var userId = utilClass.getCookie("userId");
	    
	    userName = (userName != profileClass.profileData.profileName) ? userName : "";
	    dob = (dob != profileClass.profileData.dob) ? dob : "";
	    
	    var userData = {};
	    userData.userId = userId;
	    userData.userName = userName;
	    userData.email = "";
	    userData.password = "";
	    userData.dob = dob;
	    userData.countryState = $("#connUs-state").val();
        userData.country = $("#connUs-country").val();
	    userData.gender = "";
	    userData.imgUrl = "";
	    
	    utilClass.updateUser(userData, true);
	},
	
	selectTabOption : function(events){
		var _self = this;
		var index = $(events.target).attr("index");
		
		$(".connUs-tab").removeClass("active");
		$($(".connUs-tab")[index - 1]).addClass("active");
		utilClass.deleteQueryParam("groupId");
		var currentUrl = window.location.href;
		if(index == 1)
		{
			_self.showProfile();
		}
		else if(index == 2)
		{
			_self.makeFriendsCall(false);
			currentUrl += "&groupId=members";
		}
		else if(index == 3)
		{
			photosClass.getPhotos();
			currentUrl += "&groupId=photos";
		}
		else if(index == 4)
        {
            _self.showCommunities();
            currentUrl += "&groupId=communities";
        }
		window.history.pushState('obj', 'newtitle', currentUrl);
	},
	
	showProfile : function(){
		$("#connUs-page-wrapper").removeClass();
		$("#connUs-page-wrapper").addClass("profile-active");
	},
	
	makeFriendsCall : function(isOnline){
		var _self = this;
		var userId = profileClass.profileData.profileId;
		var isDomReady = false;
		
		if(isOnline)
		{
		    $(".connUs-friendsList-innerAll-wrapper").hide();
		    if($(".connUs-friendsList-online-wrapper").height() > 0)
            {
                $(".connUs-friendsList-online-wrapper").show();
                isDomReady = true;
            }
		}
		else
		{
		    $(".connUs-friendsList-online-wrapper").hide();
		    if($(".connUs-friendsList-innerAll-wrapper").height() > 0)
		    {
		        $(".connUs-friendsList-innerAll-wrapper").show();
		        isDomReady = true;
		    }
		}
		
		if(!isDomReady)
		{
    		var config = {};
    		config.callType = "friendList"
    		config.type = "get";
    		config.data= ({
    			"userId" : userId,
    			"friendsType" : 0,
    			"isOnline" : isOnline
     		});
    		
    		var frndsListCbk = function(response){
    			var obj = JSON.parse(response);
    			if(isOnline)
    			{
    			    utilClass.callHandlebar("#friendsList-hb-template", ".connUs-friendsList-online-wrapper", obj.properties);
    			}
    			else
    			{
    			    utilClass.callHandlebar("#friendsList-hb-template", ".connUs-friendsList-innerAll-wrapper", obj.properties);
    			}
    			$("#connUs-page-wrapper").removeClass();
    			$("#connUs-page-wrapper").addClass("friends-active");
    			
    			$(".connUs-frndstext").off("click").on("click",function(){
                    _self.makeFriendsCall(false);
                });
    			
    			$(".connUs-active-frndstext").off("click").on("click",function(){
    	            _self.makeFriendsCall(true);
    	        });
    		};
    
    		utilClass.makeAjaxCall(config, frndsListCbk);
		}
	},
	
	showCommunities : function(){
	    var _self = this;
	    $($(".comm-tab")[0]).addClass("active");
	    _self.renderCommunities(1);
	    $("#connUs-page-wrapper").removeClass();
	    $("#connUs-page-wrapper").addClass("communities-active");

        $(".comm-tab").off("click").on("click",function(events){
            _self.selectCommTabOption(events);
        });
	},
	
	selectCommTabOption : function(events){
	    var _self = this;
	    var index = $(events.target).attr("index");
	    
	    $(".comm-tab").removeClass("active");
	    $(".comm-all-list-cont").show();
	    $($(".comm-tab")[index - 1]).addClass("active");
	    _self.renderCommunities(index);
	},
	
	renderCommunities : function(type){
	    var _self = this;
	    var userId = profileClass.profileData.profileId;
	    
	    var config = {};
        config.callType = "getCommunityList"
        config.type = "POST";
        config.data = {};
        config.data.userId = userId;
        
	    if(type == 1)
        {
	        config.data.type = "liked";
        }
	    else if(type == 2)
        {
	        config.data.type = "member";
        }
        else if(type == 3)
        {
            config.data.type = "owned";
        }
	    var commCbk = function(response){
	        var obj = JSON.parse(response);
	        $(".comm-all-list-cont").children().removeClass("active");
	        $($(".comm-all-list-cont").children()[type - 1]).addClass("active");
	        
	        if(type == 1)
	        {
	            utilClass.callHandlebar("#communitiesListData-hb-template", ".comm-liked-wrapper", obj.properties);
	        }
	        else if(type == 2)
	        {
	            utilClass.callHandlebar("#communitiesListData-hb-template", ".comm-member-wrapper", obj.properties);
	        }
	        else if(type == 3)
	        {
	            obj.properties.data.isYours = true;
	            utilClass.callHandlebar("#communitiesListData-hb-template", ".comm-yours-wrapper", obj.properties);
	            
	            $(".comm-add-new-cont").off("click").on("click",function(events){
	                window.location.href = "../chatUI/communites.jsp?id=new";
	            });
	        }
	    };
	    
	    utilClass.makeAjaxCall(config, commCbk);
	},
	
	displayPhotos : function(){
        var _self = this;
        
        var config = {};
        config.callType = "getPhotos"
        config.type = "POST";
        config.data= ({
            "userId" : utilClass.userId,
            "profileId" : profileClass.profileData.profileId,
            "clientTZ" : utilClass.clientTZ
        });
            
        var displayPhotosCbk = function(response){
            var obj = JSON.parse(response);
            utilClass.callHandlebar("#displayPhotos-hb-template", ".display-img-wrapper", obj.properties);
            $(".connUs-upload-picCont").show();
            utilClass.showMask("#000");
            _self.displayPhotosRegisterEvents();
        }
        
        utilClass.makeAjaxCall(config, displayPhotosCbk);
    },
    
    displayPhotosRegisterEvents: function(){
        var _self = this;
        
        $(".upload-pic-close-icon").off("click").on("click",function(){
            $(".connUs-upload-picCont").hide();
            $(".connUs-upload-picCont").removeClass("img-crop-active");
            utilClass.hideMask();
        });
        
        $(".connUs-display-Img").off("click").on("click",function(event){
            var imgUrl = $(event.target).attr("src");
            _self.updateProfilePicture(imgUrl);
        });
    },
    
    updateProfilePicture : function(imgUrl){
        var _self = this;
        $("#upload-crop-img").children().remove();
        $(".connUs-upload-picCont").addClass("img-crop-active");
        
        var imgCrop = new Croppie(document.getElementById("upload-crop-img"),{
            viewport: { width: 200, height: 200 , type:'circle'},
            boundary: { width: 400, height: 400 },
            showZoom: false
        });
        
        imgCrop.bind(imgUrl);

        $(".upload-crop-img-close").off("click").on("click",function(){
            $(".connUs-upload-picCont").removeClass("img-crop-active");
            $("#upload-crop-img").children().remove();
        });
        
        $(".upload-crop-img-conform").off("click").on("click",function(){
            _self.setCroppedImage(imgCrop);
        });
    },
    
    setCroppedImage : function(imgCrop)
    {
        var corpImgCbk = function(imgUrl){
            var userData = {};
            userData.userId = userId;
            userData.userName = "";
            userData.email = "";
            userData.password = "";
            userData.dob = "";
            userData.countryState = "";
            userData.country = "";
            userData.gender = "";
            userData.imgUrl = imgUrl;
            
            utilClass.updateUser(userData, true);
            
            $(".connUs-profile-img").attr("src", imgUrl);
            $(".connUs-upload-picCont").removeClass("img-crop-active");
            utilClass.hideMask(); 
        };
        
        imgCrop.result('canvas','original').then(function(src) {
            console.log(src);
            corpImgCbk(src);
        });
    }
}