$( document ).ready(function() {
    communityClass.init();
    communityClass.commData = {};
});

var communityClass = {
    init : function()
    {
        var _self = this;

        _self.renderCommunityPage();
    },
    
    renderCommunityPage : function(){
        var _self = this;
        var commId = utilClass.getQueryParam("id");
        var userId = utilClass.getCookie("userId");
        var commData = [];
        if(commId == "new")
        {
            commData.isNew = true;
            utilClass.callHandlebar("#communityData-hb-template", "#connUs-comm-wrapper", commData);
            _self.registerEvents();
        }
        else
        {
            var config = {};
            config.callType = "getCommunity"
            config.type = "post";
            config.data= ({
                    "commId" : commId,
                    "userId" : userId
            });
            
            var getCommcbk = function(response){
                commData = JSON.parse(response);
                communityClass.commData = commData;
                utilClass.callHandlebar("#communityData-hb-template", "#connUs-comm-wrapper", commData);
                _self.registerEvents();
            };
            
            utilClass.makeAjaxCall(config, getCommcbk);
        }
    },
    
    registerEvents : function(){
        var _self = this;

        $(".comm-create, .comm-update").off("click").on("click",function(){
            _self.addOrUpdatCommunity();
        });
        
        $("#comm-upload-img").change(function(){
            _self.uploadCommImage();
        });
        
        $("#comm-upload-cover-img").change(function(){
            _self.uploadCommCoverImage();
        });
        
        $(".comm-like-btn").off("click").on("click",function(){
            _self.giveLikeOrMemberCommunity("liked");
        });
        
        $(".comm-connect-btn").off("click").on("click",function(){
            _self.giveLikeOrMemberCommunity("member");
        });
        
        $(".comm-disconnect-btn").off("click").on("click",function(){
            _self.giveLikeOrMemberCommunity("removeMember");
        });
        
        $(".comm-edit-btn").off("click").on("click",function(){
            $(".comm-left-cont").addClass("edit-active");
        });
    },
    
    uploadCommImage : function()
    {
        var _self = this;
        var cbk = function(response)
        {
            var obj = JSON.parse(response);
            var imgUrl = obj.properties.data.imgPath;
            
            $(".comm-img").attr("src", imgUrl);
            $(".comm-img").attr("isAdded", true);
            
            if($("#comm-cont").hasClass("comm-owned"))
            {
            	_self.addOrUpdatCommunity();
            }
        }
        _self.uploadImage("comm-upload-img", "communityImg", cbk);
    },
    
    uploadCommCoverImage : function()
    {
        var _self = this;
        var cbk = function(response)
        {
            var obj = JSON.parse(response);
            var imgUrl = obj.properties.data.imgPath;
            
            $(".comm-cover-img").attr("src", imgUrl);
            $(".comm-cover-img").attr("isAdded", true);
            
            if($("#comm-cont").hasClass("comm-owned"))
            {
            	_self.addOrUpdatCommunity();
            }
        }
        _self.uploadImage("comm-upload-cover-img", "communityCoverImg", cbk);
    },
    
    addOrUpdatCommunity : function(){
        var _self = this;
        var userId = utilClass.getCookie("userId");
        var commId = "", name, img = "", coverImg = "", address, email, phNo, type = false;

        name = $(".comm-name").val();
        address = $(".comm-address").val();
        email = $(".comm-email").val();
        phNo = $(".comm-phNo").val();

        if($("#comm-cont").hasClass("comm-owned"))
        {
        	commId = utilClass.getQueryParam("id");
        	type = true;
        }
        name = (name != communityClass.commData.name) ? name : "";
        if($(".comm-img").attr("isAdded"))
        {
            img = $(".comm-img").attr("src");
            img = (img != communityClass.commData.img) ? img : "";
        }
        if($(".comm-cover-img").attr("isAdded"))
        {
            coverImg = $(".comm-cover-img").attr("src");
            coverImg = (img != communityClass.commData.coverImg) ? coverImg : "";
        }
        address = (address != communityClass.commData.address) ? address : "";
        email = (email != communityClass.commData.email) ? email : "";
        phNo = (phNo != communityClass.commData.phNo) ? phNo : "";

        var config = {};
        config.callType = "updateCommunities"
        config.type = "post";
        config.data= ({
                "commId" : commId,
                "name" : name,
                "coverImg" : coverImg, 
                "img": img,
                "address": address,
                "email": email,
                "phNo": phNo,
                "ownerId": userId,
                "isEdit": type
        });
        
        utilClass.makeAjaxCall(config);
    },
    
    giveLikeOrMemberCommunity : function(type){
        var commId = utilClass.getQueryParam("id");
        var userId = utilClass.getCookie("userId");
        
        var config = {};
        config.callType = "giveLikeOrMemberCommunity"
        config.type = "post";
        config.data= ({
                "commId" : commId,
                "userId" : userId,
                "type"   : type
        });
        
        var likeOrMemberCbk = function(){
            if(type == "liked")
            {
                $("#comm-cont").addClass("comm-liked");
            }
            else if(type == "member")
            {
                $("#comm-cont").removeClass("comm-non-member");
                $("#comm-cont").addClass("comm-member");
            }
            else if(type == "removeMember")
            {
                $("#comm-cont").removeClass("comm-member");
                $("#comm-cont").addClass("comm-non-member");
            }
        };
        
        utilClass.makeAjaxCall(config, likeOrMemberCbk);
    },
    
    uploadImage : function(formId, imgType, uploadImgCbk){
        var _self = this;
        var userId = utilClass.getCookie("userId");
        var formData = new FormData(document.getElementById(formId));
        formData.append("userId", userId);
        formData.append("imgType", imgType);
        
        var profileImgCbk = function(response){
            var obj = JSON.parse(response);
            var imgUrl = obj.properties.data.imgPath;
           
        };
        
        utilClass.uploadImage(formData, uploadImgCbk);
    },
};