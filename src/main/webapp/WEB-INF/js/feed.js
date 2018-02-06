$( document ).ready(function() {
	setTimeout(function(){
		feedClass.init();
	},500);
});


var feedClass = {
	
	init : function()
	{
		var _self = this;
		
		_self.RegisterEvents();
		_self.getFeedCall();
	},

	RegisterEvents : function()
	{
		var _self = this;
		
		$(".youtube-url").off("keyup").on("keyup",function(events){
            _self.appendYoutubeVideo(events);
        });

        $(".add-youtubeIcon").off("click").on("click",function(){
            _self.openYoutubeInputCont();
        });

        $(".connUs-addFeed-btn").off("click").on("click",function(){
            _self.addFeed();
        });
        
        $("#connUs-feed-imgUpload").change(function(){
            _self.uploadImage();
        });
        
        $(".connUs-feed-smiley").off("click").on("click",function(events){
			_self.renderSmileys();
		});
	},
	
	appendYoutubeVideo: function(){
        var youtubeUrl = $(".youtube-url").val();
        if(youtubeUrl && (youtubeUrl.indexOf("youtube") > 0))
        {
            youtubeUrl = youtubeUrl.replace("watch?v=", "embed/");
            var youtubeDisplayDomHtml = "<iframe width='100%' height='260px'"+
                        "src='" + youtubeUrl + "'>"+
                        "</iframe>";
            $(".feed-youtubeVideo-display").empty();
            $(".feed-youtubeVideo-display").append(youtubeDisplayDomHtml);
            
            $(".youtube-url").attr("videoUrl", youtubeUrl);
        }
    },
    
    openYoutubeInputCont: function(){
        $(".connUs-youtubeAddCont").toggleClass("active");
    },
    
    addFeed: function(){
        var _self = this;
        var commId = $("body").hasClass("connUs-community") ?  utilClass.getQueryParam("id") : "";
        var feedDesc = $($(".connUs-addFeed-desc")[1]).html();
        var feedImgUrl = $(".connUs-addFeedImgDisplay").attr("src");
        feedImgUrl = feedImgUrl ? feedImgUrl : "";
        var videoUrl = $(".youtube-url").attr("videoUrl");
        videoUrl = videoUrl ? videoUrl : "";
        var config = {};
        config.callType = "addFeed"
        config.type = "POST";
        config.data= ({
            "userId" : userId,
            "commId" : commId,
            "feedDesc" : feedDesc,
            "feedImgUrl" : feedImgUrl,
            "videoUrl" : videoUrl
        });
        
        utilClass.makeAjaxCall(config);
    },

    uploadImage : function(){
        var _self = this;
        var formData = new FormData(document.getElementById("connUs-addFeed-img"));
        formData.append("userId", userId);
        formData.append("imgType", "feedImg");
        
        var profileImgCbk = function(response){
            var obj = JSON.parse(response);
            $(".connUs-addFeedImgDisplayCont").show();
            $(".connUs-addFeedImgDisplay").attr("src", obj.properties.data.imgPath);
        };
        
        utilClass.uploadImage(formData, profileImgCbk);
    },
	
	getFeedCall : function(){
		var _self = this;
		var userId = _self.getCookie("userId");
		var commId = utilClass.getQueryParam("id");
		var throttleDelay = 0;
		
		var config = {};		
		config.type = "POST";
		config.data = {};
		
		if($("body").hasClass("connUs-home"))
		{
			config.callType = "getUserHomeFeeds";
			config.data.userId = userId;
			config.data.pageLevel = 1;
			config.data.limits = 10;
			config.data.clientTZ = utilClass.clientTZ;
		}
		else if($("body").hasClass("connUs-profile"))
		{
			var profileId = profileClass.profileData.profileId;
			config.callType = "getFeed";
			config.data.userId = userId;
			config.data.profileId = profileId;
			config.data.commId = "";
			config.data.clientTZ = utilClass.clientTZ;
		}
		else if($("body").hasClass("connUs-community"))
        {
            var profileId = $("#comm-cont").attr("ownerid");
            config.callType = "getFeed";
            config.data.userId = userId;
            config.data.profileId = profileId;
            config.data.commId = commId;
        }
		
		var getFeedCbk = function(response){
			var obj = JSON.parse(response);
			isLast = obj.properties.data.isLast;
			if(config.data.pageLevel > 1)
            {
			    utilClass.callHandlebar("#feedListData-hb-template", ".connUs-feedList-wrapper", obj.properties, true);
            }
			else
			{
			    utilClass.callHandlebar("#feedListData-hb-template", ".connUs-feedList-wrapper", obj.properties);
			}
			_self.registerFeed();
			utilClass.registerEmoji();
			
			jQuery(document).ready(function(){
				jQuery("#gallery").unitegallery();
			});
			//$(".ug-tiles-rest-mode").css("Width", $(".ug-tile-clickable").outerWidth() + "px");
			
			if(config.data.pageLevel == 1)
			{
    			$(window).scroll(function() {
        			if($(window).scrollTop() + $(window).height() > $(document).height() - throttleDelay)
        			{
        			    if($("body").hasClass("connUs-home") && !isLast)
        		        {
        		            config.data.pageLevel++;
        		            utilClass.makeAjaxCall(config, getFeedCbk);
        		        }
        			}
    			});
			}
		};
		
		utilClass.makeAjaxCall(config, getFeedCbk);
	},
	
	registerFeed : function()
	{
		var _self = this;

		$(".connUs-giveLike").off("click").on("click",function(events){
			_self.giveFeedLike(events);
		});
		
		$(".connUs-cmnts-smiley").off("click").on("click",function(events){
			  _self.renderSmileys(events);
		});
		
		$(".connUs-addcomnts-btn").off("click").on("click",function(events){
			_self.addFeedCmnts(events);
		});
		
		$(".connUs-viewMoreLikes").off("click").on("click",function(events){
			_self.getFeedLikes(events);
		});
		
		$(".connUs-viewMoreCmnts").off("click").on("click",function(events){
			_self.getFeedCmnts(events);
		});
	},
	
	giveFeedLike : function(events)
	{
		var _self = this;
		var userId = _self.getCookie("userId");
		var commId = $("body").hasClass("connUs-community") ?  utilClass.getQueryParam("id") : "";
		var feedId = $(events.target).closest(".connUs-feed-cont").attr("feedId");
		if($("body").hasClass("connUs-profile"))
		{
			userFeedId = utilClass.getQueryParam("pId")
		}
		else
		{
			userFeedId = $(events.target).closest(".connUs-feed-cont").attr("userFeedId");
		}

		var config = {};
		config.callType = "giveLike";
		config.type = "POST",
		config.data = ({
			"userId" : userId,
			"commId" : commId,
			"userFeedId" : userFeedId,
			"feedId" : feedId
		});
		
		_self.makeAjaxCall(config);
	},
	
	addFeedCmnts : function(events){
		var _self = this;
		var userId = _self.getCookie("userId");
		var commId = $("body").hasClass("connUs-community") ?  utilClass.getQueryParam("id") : "";
		var feedId = $(events.target).closest(".connUs-feed-cont").attr("feedId");
		if($("body").hasClass("connUs-profile"))
		{
			userFeedId = utilClass.getQueryParam("pId")
		}
		else
		{
			userFeedId = $(events.target).closest(".connUs-feed-cont").attr("userFeedId");
		}
		var comments = $($(events.target).siblings(".connUs-cmntsTxt")[1]).html();
		
		var config = {};
		config.callType = "addComment";
		config.type = "POST",
		config.data = ({
			"userId" : userId,
			"commId" : commId,
			"userFeedId" : userFeedId,
			"feedId" : feedId,
			"comments" : comments
		});
		
		_self.makeAjaxCall(config);
	},
	
	getFeedLikes : function(events){
		var _self = this;
		var userId = _self.getCookie("userId");
		var feedId =  $(events.target).closest(".connUs-feed-cont").attr("feedId");
		
		var config = {};
		config.callType = "getLikeOrCmnts";
		config.type = "POST",
		config.data = ({
			"userId" : userId,
			"id" : feedId,
			"type" : "like",
			"clientTZ" : utilClass.clientTZ
		});
		
		getFeedLikeCbk = function(response){
			var obj = JSON.parse(response);
			_self.callHandlebar("#likeListData-hb-template", "#connUs-feedLike-wrapper-"+feedId, obj.properties);
		};
		
		_self.makeAjaxCall(config, getFeedLikeCbk);
	},
	
	getFeedCmnts : function(events){
		var _self = this;
		var userId = _self.getCookie("userId");
		var feedId =  $(events.target).closest(".connUs-feed-cont").attr("feedId");
		
		var config = {};
		config.callType = "getLikeOrCmnts";
		config.type = "POST",
		config.data = ({
			"userId" : userId,
			"id" : feedId,
			"type" : "comments",
			"clientTZ" : utilClass.clientTZ
		});
		
		getFeedCmntsCbk = function(response){
			var obj = JSON.parse(response);
			_self.callHandlebar("#cmntsListData-hb-template", "#connUs-feedCmnts-wrapper-"+feedId, obj.properties);
		};
		
		_self.makeAjaxCall(config, getFeedCmntsCbk);
	},
	
	renderSmileys : function(events)
	{
		var _self = this;
		if(events)
		{
			var emojiDom = $($(events.target).parent()).children(".emoji-menu");
			$(emojiDom).toggle();
			_self.feedCmntsSmileySetPos(events, emojiDom);
		}
		else
		{
			$(".connUs-addFeed-cont .emoji-menu").toggle();
		}
	},
	
	feedCmntsSmileySetPos : function(events, emojiDom)
	{
		var topPos = $(events.target).offset().top + $(events.target).height();
		$(emojiDom).css("top", topPos);
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