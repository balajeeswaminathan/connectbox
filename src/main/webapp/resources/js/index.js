$( document ).ready(function() {
	indexClass.init();
});

var indexClass= {
		
	init : function()
	{
		var _self = this;
		_self.RegisterEvents();
	},
	
    RegisterEvents : function()
	{
		var _self = this;
		
		$(".connUs-login-Btn").off("click").on("click",function(){
			_self.madeLoginAjaxCall();
		});
			
		$(".connUs-signUp-Btn").off("click").on("click",function(){
			_self.madeRegisterAjaxCall();
		});
			
		$(".tab-group").off("click").on("click",function(e){
			_self.classActive(e);
		});
		
		$(".forgotPsw-btn").off("click").on("click",function(){
			_self.showOrHideForgetPsw(true);
		});
		
		$(".cont-close-btn").off("click").on("click",function(){
			_self.showOrHideForgetPsw(false);
		});

		$(".sendEmail-btn").off("click").on("click",function(){
			_self.forgetPsw();
		});
		
		$(".emailVerification-ok-Btn, .forgetPswSuccess-ok-Btn").off("click").on("click",function(){
			_self.showOrHideForgetPsw(false);
		});
		
		$(".connUsform").addClass("login-form");
		$(".login").addClass("active");
		//print_country("connUs-country");
	},
	
	madeLoginAjaxCall: function(){
		var _self = this;
		var email = $("#login-Email").val();
		var password = $("#login-Password").val();
		
		returnVal1 = utilClass.formValidation(email, ".connUs-login-cont", "Email", "login");
		returnVal2 = utilClass.formValidation(password, ".connUs-login-cont", "Password", "login");
		
		if(returnVal1 && returnVal2)
		{
			$(".connUs-login-cont").removeAttr("style");
			var config = {};
			config.callType = "login"
			config.type = "post";
			config.data= ({
					"email" : email,
					"password" : password
			});
			
			var loginCbk = function(response){
				var obj = JSON.parse(response);
				  if(obj.properties.data.status == 1)
				   {
					    alert(obj.properties.data.errorMessage);
				   }
				  else
				   {
					  var userId = obj.properties.data.user_Id;
					  var userName = obj.properties.data.userName;
					  utilClass.setCookie("userId", userId);
					  //utilClass.setCookie("userName", userName);
					  window.location.href = "../connectbox/home.jsp";
				   }
			};
			
			utilClass.makeAjaxCall(config, loginCbk);
		}
	   },
	
	madeRegisterAjaxCall : function(){
		var _self = this;
		var username = $("#signup-Firstname").val();
		var email = $("#signup-Email").val();
		var password = $("#signup-Password").val();
		var dob = $("#signup-Dob").val();
		var gender = $('input[name="gender"]:checked').val();
		
		returnVal1 = utilClass.formValidation(username, ".connUs-signup-Cont", "Firstname", "signup");
		returnVal2 = utilClass.formValidation(email, ".connUs-signup-Cont", "Email", "signup");
		returnVal3 = utilClass.formValidation(password, ".connUs-signup-Cont", "Password", "signup");
		returnVal4 = utilClass.formValidation(dob, ".connUs-signup-Cont", "Dob", "signup");
		returnVal5 = _self.validateGender(gender, ".connUs-signup-Cont", ".connUs-gender-label");
		returnVal6 = utilClass.validateStateAndCountry("#connUs-country", "#connUs-state");

		if(returnVal1 && returnVal2 && returnVal3 && returnVal4 && returnVal5 && returnVal6)
		{
			$(".connUs-signup-cont").removeAttr("style");
			
			var userData = {};
	        userData.userId = "";
	        userData.userName = username;
	        userData.email = email;
	        userData.password = password;
	        userData.dob = dob;
	        userData.city = $("#cityId").val();
	        userData.countryState = $("#stateId").val();
	        userData.country = $("#countryId").val();
	        userData.gender = gender;
	        userData.imgUrl = "";
	        
			var registerCbk = function(response){
				var obj = JSON.parse(response);
				var userId = obj.properties.data.user_Id;
				var mailDom = "<div class='connUs-mail-cont'>"+
							"<div class='connUs-header-cont'style='height: 50px;background-color: #616161;padding: 7px'>"+
							"<div class='connUs-header-label' style='font-weight: 600;font-size: 23px;width: 200px;margin: 0 auto;color: #fff;'>Connecting Us</div></div>"+
							"<h2>Thanks for joining with us !</h2>"+
							"<div class='connUs-mailContent-cont'>Please click this below link to verify your email address.</div>"+
							"<a href='http://localhost:8080/chatUI/emailverification.jsp?pId=" + userId + "'>//localhost:8080/chatUI/emailverification.jsp</a>"+
							"</div>";
	
				var sendMailCbk = function(){
					$(".connUsform").removeClass("login-form forgetPsw-form register-form");
					$(".connUsform").addClass("emailVerification-form");
				};
				utilClass.sendMail(email, "Email Verification", mailDom, sendMailCbk);
			};
			
			utilClass.updateUser(userData, false, registerCbk);
		}
	},

	classActive : function(e){
		 var _self = this;
		 var $this = $(e.target);
		 var className = $this.attr("class");
		 if(className == "login")
		 {
		 	$("#connUs-errCont").remove();
		 	$(".connUs-signup-cont").children().removeClass("input-error");
		 	$(".connUs-signup-cont").removeAttr("style");
		 	$(".connUs-gender-label").removeClass("gender-error");
		 	$(".connUsform").removeClass("register-form emailVerification-form");
		 	$(".register").removeClass("active");
		 }
		 else if(className == "register")
		 {
		 	$("#connUs-errCont").remove();
		 	$(".connUs-login-cont").children().removeClass("input-error");
		 	$(".connUs-login-cont, .connUs-forgetPsw-Cont").removeAttr("style");
		 	_self.showOrHideForgetPsw(false);
		 	$(".connUsform").removeClass("login-form");
		 	$(".login").removeClass("active");
		 }
		 $(".connUsform").addClass(className+"-form");
		 $this.addClass("active");
	 },
	 
	 showOrHideForgetPsw : function(isShow)
	 {
		 $(".connUsform").removeClass("login-form forgetPsw-form emailVerification-form forgetPswSuccess-form");
		 $("#connUs-errCont").remove();
		 $(".connUs-login-cont").children().removeClass("input-error");
		 $(".connUs-login-cont, .connUs-forgetPsw-Cont").removeAttr("style");
		 if(isShow)
		 {
			 $(".connUsform").addClass("forgetPsw-form");
		 }
		 else
		 {
			 $(".connUsform").addClass("login-form");
		 }
	 },
	 
	 forgetPsw : function(){
		var _self = this;
		var email = $("#forget-Email").val();
		
		returnVal = utilClass.formValidation(email, ".connUs-forgetPsw-Cont", "Email", "forget");
		
		if(returnVal)
		{
			var config = {};
			config.callType = "forgetPsw";
			config.type = "post";
			config.data= ({
				"email" : email
			});
			
			var forgetPswCbk = function(response){
				var obj = JSON.parse(response);
				var status = obj.properties.data.status;
				var userId = obj.properties.data.userId;
				if(status)
				{
					$(".connUs-err").remove();
					$(".connUs-forgetPsw-Cont").removeAttr("style");
					var mailDom = "<div class='connUs-mail-cont'>"+
								"<div class='connUs-header-cont'style='height: 50px;background-color: #616161;padding: 7px'>"+
								"<div class='connUs-header-label' style='font-weight: 600;font-size: 23px;width: 200px;margin: 0 auto;color: #fff;'>Connecting Us</div></div>"+
								"<h2>Password Reset link</h2>"+
								"<div class='connUs-mailContent-cont'>Please click this below link to reset your password</div>"+
								"<a href='http://localhost:8080/chatUI/forgotpassword.jsp?pId=" + userId + "'>//localhost:8080/chatUI/forgotpassword.jsp</a>"+
								"</div>";
		
					var sendMailCbk = function(){
						$(".connUsform").removeClass("login-form forgetPsw-form register-form");
						$(".connUsform").addClass("forgetPswSuccess-form");
					};
					utilClass.sendMail(email, "Password Reset", mailDom, sendMailCbk);
				}
				else
				{
					utilClass.showError(".connUs-forgetPsw-Cont", "forget", "Your email is not registered with us", true);
				}
			};
			
			utilClass.makeAjaxCall(config, forgetPswCbk);
		}
	 },
	
	 validateGender : function(gender, parentIdorClass, genderLabelDom)
	 {
		 var _self = this;
		 if(gender)
		 {
			 $("#connUs--err").remove();
			 $(genderLabelDom).removeClass("gender-error");
			 return true;
		 }
		 else
		 {
			 utilClass.showError(parentIdorClass, "gender", "Select your gender", true);
			 $(genderLabelDom).addClass("gender-error");
			 return false;
		 }
	 }
}