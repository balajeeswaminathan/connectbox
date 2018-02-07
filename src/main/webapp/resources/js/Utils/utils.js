$( document).ready(function() {
	streamDomain = "../connectbox/";
	utilClass.userId = utilClass.getCookie("userId");
	utilClass.clientTZ = utilClass.getTimeZone();
});

var utilClass = {

    getOnlineMembers : function(userId, cbk){
        var config = {};
        config.callType = "getOnlineMembers";
        config.type = "post";
        config.data= ({
            "userId" : userId,
        });
    
        utilClass.makeAjaxCall(config, cbk);
    },
    
	sendMail : function(email, mailSubject, mailDom, sendMailCbk){
		var _self = this;

		var config = {};
		config.callType = "sendMail";
		config.type = "post";
		config.data= ({
			"email" : email,
			"mailSubject" : mailSubject,
			"mailDom" : mailDom
		});

		_self.makeAjaxCall(config, sendMailCbk);
	},
	
	formValidation : function(val, parentIdorClass, type, formType)
	 {
		 var _self = this;
		 var returnVal;
		 if(type == "Email" || type == "Password")
		 {
			 returnVal = _self.validateField(val, 6, type);
		 }
		 else
		 {
			 returnVal = _self.validateField(val, 3, type);
		 }
		 
		 if(returnVal == "empty") //empty check
		 {
			_self.showError(parentIdorClass, type, "is Empty");
			_self.highLightInputError("#" + formType + "-" + type, true);
		 }
		 else if(returnVal == "minLength") //min length check
		 {
			 if(type == "Email" || type == "Password")
			 {
				 _self.showError(parentIdorClass, type, "should contains atleast 6 characters");
			 }
			 else
			 {
				 _self.showError(parentIdorClass, type, "should contains atleast 3 characters");
			 }
			 _self.highLightInputError("#" + formType + "-" + type, true);
		 }
		 else if(returnVal == "invalid") //invalid check
		 {
			 _self.showError(parentIdorClass, type, "is invalid");
			 _self.highLightInputError("#" + formType + "-" + type, true);
		 }
		 else if(returnVal == true)
		 {
			 _self.highLightInputError("#" + formType + "-" + type, false);
			$("#connUs-" + type + "-err").remove();
			return returnVal;
		 }
	 },
	 
	 validateField : function(val, minLen, type)
	 {
		 var valLen = val.length;
		 var regExEmail = /^[A-Z0-9._%+-]+@([A-Z0-9-]+\.)+[A-Z]{2,4}$/i;
		 if(valLen == 0)
		 {
			 return "empty";
		 }
		 else if(valLen < minLen)
		 {
			 return "minLength";
		 }
		 else if(type == "Email")
		 {
			 if(regExEmail.test(val))
			 {
				 return true;
			 }
			 else
			 {
				return "invalid";
			 }
		 }
		 else
		 {
			 return true;
		 }
	 },
	 
	 validateStateAndCountry : function(stateId, countryId)
	 {
	     var _self = this;
	     var isValid = true;
	     if($(countryId).val() == "")
         {
             _self.showError(".connUs-signup-Cont", "country", "Select your country", true);
             isValid = false;
         }
	     if($(stateId).val() == "")
	     {
	         _self.showError(".connUs-signup-Cont", "state", "Select your state", true);
	         isValid = false;
	     }
	     return isValid;
	 },
	 
	 showError : function(parentIdorClass, type, errStr, showErrOnly)
	 {
		 var _self = this;
		 if($(parentIdorClass).find("#connUs-errCont").length == 0)
		 {
			 var errParentDom = document.createElement('div');
			 errParentDom.setAttribute("id", "connUs-errCont");
			 $(parentIdorClass).prepend(errParentDom);
		 }
		 if($("#connUs-errCont").find("#connUs-" + type + "-err").length == 0)
		 {
			 var errChildDom = document.createElement('div');
			 errChildDom.className = "connUs-err";
			 errChildDom.setAttribute("id", "connUs-" + type + "-err");
			 $("#connUs-errCont").append(errChildDom);
			 var adjustDom = true;
		 }
		 if(showErrOnly)
		 {
			 $("#connUs-" + type + "-err").html(errStr);
		 }
		 else
		 {
			 $("#connUs-" + type + "-err").html(type + " " + errStr);
		 }
		 if(adjustDom)
		 {
			 _self.addjustDomHeight(parentIdorClass, "#connUs-errCont");
		 }
		 $(parentIdorClass + ", .connUsform").addClass("adding-error-Cont");
		 setTimeout(function(){
			 $(parentIdorClass + ", .connUsform").removeClass("adding-error-Cont");
		 },700);
	 },
	 
	 addjustDomHeight : function(parentIdorClass, childDom)
	 {
		 $(parentIdorClass).css("height", $(parentIdorClass).outerHeight() + $(childDom).outerHeight());
	 },
	
	 highLightInputError : function(classOrId, showErr)
	 {
		 if(showErr)
		 {
			 $(classOrId).addClass("input-error");
		 }
		 else
		 {
			 $(classOrId).removeClass("input-error");
		 }
	 },
	 
	 checkImageSize: function(file)
	 {
	     var fileSize = file.files[0].size;
	     fileSize = fileSize / 1024;
	     fileSize = fileSize / 1024;
	     if(fileSize > 8)
	     {
	         return false;
	     }
	     return true;
	 },
	 
	 uploadImage : function(formData, photosCbk)
	 {
	        var _self = this;

	        var config = {};
	        config.callType = "uploadImage"
	        config.type = "POST";
	        config.enctype = 'multipart/form-data';
	        config.processData =  false;
	        config.contentType =  false;
	        config.data = formData;

	        utilClass.makeAjaxCall(config, photosCbk);
	  },
	 
	 registerEmoji : function(){
	       // Initializes and creates emoji set from sprite sheet
	       window.emojiPicker = new EmojiPicker({
	         emojiable_selector: '[data-emojiable=true]',
	         assetsPath: '../lib/img/',
	         popupButtonClasses: 'fa fa-smile-o'
	       });
	       // Finds all elements with `emojiable_selector` and converts them to rich emoji input fields
	       // You may want to delay this step if you have dynamically created input fields that appear later in the loading process
	       // It can be called as many times as necessary; previously converted input fields will not be converted again
	       window.emojiPicker.discover();
	 },  
	 
	 updateUser: function(userData, type, cbk){
	   var _self = this;
	   
	   var config = {}
       config.callType = "updateUser";
       config.type = "POST";
       config.data = {
           "userId": userData.userId,
           "userName": userData.userName,
           "email" : userData.email,
           "password": userData.password,
           "dob": userData.dob,
           "countryState" : userData.countryState,
           "country" : userData.country,
           "gender": userData.gender,
           "profileImgUrl": userData.imgUrl,
           "isEdit": type
       };
       _self.makeAjaxCall(config, cbk);
	 },
	 
	 makeAjaxCall : function(config, cbk){
		 
		 config.url = streamDomain + config.callType;
		 
		 config.success = cbk;
		 $.ajax(config);
	 },
	 
	 deleteQueryParam : function(name, url)
	 {
		if (!url) 
		{
			url = window.location.href;
		}
		name = name.replace(/[\[\]]/g, "\\$&");
		var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
		    results = regex.exec(url);
		if (!results)
		{
			return null;
		}
		else if(results[0])
		{
			window.history.pushState('obj', 'newtitle', url.replace(results[0], ""));
		}
	},
	 
	 getQueryParam : function(name, url)
	 {
		if (!url) url = window.location.href;
		name = name.replace(/[\[\]]/g, "\\$&");
		var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
		    results = regex.exec(url);
		if (!results) return null;
		if (!results[2]) return '';
		return decodeURIComponent(results[2].replace(/\+/g, " "));
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
	 
	 setCookie : function(name, value){
		 document.cookie = name + "=" + value;
	 },
	 
	 showAlertBox: function(alertMsg)
     {
         var htmlStr = "";
         htmlStr = "<div class='connBx-alert-cont'>"
                 + "<div class='connBx-alert-close'>x</div>"
                 + "<div class='connBx-alert-msg'>" + alertMsg + "</div>"
                 + "</div>";
         
         $("body").append(htmlStr);
         
         $(".connBx-alert-close").off("click").on("click",function(){
             utilClass.hideMask();
             $(".connBx-alert-cont").remove();
         });
     },
	 
	 showMask(bgColor, opacity){
		 $(".connUs-Mask").detach();
		 $("body").append("<div class='connUs-Mask'><div>");
		 if(bgColor)
		 {
			 $(".connUs-Mask").css("background-color", bgColor);
		 }
		 if(opacity)
		 {
			 $(".connUs-Mask").css("opacity", opacity);
		 }
	 },
	 
	 hideMask(){
		 $(".connUs-Mask").remove();
	 },
	 
	 getTimeZone : function(){
	     var offset = new Date().getTimezoneOffset();
	     var o = Math.abs(offset);
	     return (offset < 0 ? "+" : "-") + ("00" + Math.floor(o / 60)).slice(-2) + ":" + ("00" + (o % 60)).slice(-2);
	 },
	 
	 callHandlebar : function(handlebarTemp, fillDom, data, isAppend, isPrepend){
		// Grab the template script
		var theTemplateScript = $(handlebarTemp).html();
		// Compile the template
		var theTemplate = Handlebars.compile(theTemplateScript);
		
		var theCompiledHtml = theTemplate(data);
		if(isAppend)
        {
            $(fillDom).append(theCompiledHtml);
        }
		else if(isPrepend)
		{
			$(fillDom).prepend(theCompiledHtml);
		}
		else
		{
		    $(fillDom).html("");
		    $(fillDom).append(theCompiledHtml);
		}
	}
}