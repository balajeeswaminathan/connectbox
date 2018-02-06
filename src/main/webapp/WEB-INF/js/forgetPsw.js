$( document ).ready(function() {
	userId = utilClass.getQueryParam("pId");
	forgetPswClass.init();
});

var forgetPswClass = {
		
	init : function()
	{
		var _self = this;
		_self.RegisterEvents();
	},
	
    RegisterEvents : function()
	{
		var _self = this;
		
		$(".psw-reset-btn").off("click").on("click",function(){
			_self.resetPsw();
		});
		
		$(".otp-send-btn").off("click").on("click",function(){
			_self.setPsw();
		});
		
		$(".otp-success-btn").off("click").on("click",function(){
			_self.setPswSuccess();
		});
		
		$(".connUsform").addClass("connUs-forgetPsw-form");
	},
	
	resetPsw : function()
	{
		var _self = this;
		var psw1 = $("#forget-Password").val();
		var psw2 = $("#forget-retype-password").val();
		
		returnVal =  utilClass.formValidation(psw1, ".connUs-forget-cont", "Password", "forget");
		
		if(returnVal)
		{
			if(psw1 == psw2)
			{
				var password = $("#forget-Password").val();
				
				var config = {};
				config.callType = "resetPassword";
				config.type = "post";
				config.data= ({
						"userId" : userId,
						"password" : password
				});

				var resetPswCbk = function(response)
				{
					var obj = JSON.parse(response);
					var status = obj.properties.data.status;
					$(".connUs-forget-cont").removeAttr("style");
					if(status == 1)
					{
						utilClass.addjustDomHeight(".connUs-forget-cont", "#connUs-errCont");
						utilClass.showError(".connUs-forget-cont", "Password", "Old and New Password is Same", true);
					}
					else
					{
						$(".connUsform").removeClass("connUs-forgetPsw-form");
						$(".connUsform").addClass("connUs-OTP-form");
					}
				};
				
				utilClass.makeAjaxCall(config, resetPswCbk);
			}
			else
			{
				$(".connUs-forget-cont").removeAttr("style");
				utilClass.addjustDomHeight(".connUs-forget-cont", "#connUs-errCont");
				utilClass.showError(".connUs-forget-cont", "Password", "Passwords does not matched", true);
			}
		}
	},
	
	setPsw : function()
	{
		var OTP = $("#OTP-value").val();
		var password = $("#forget-Password").val();

		returnVal =  utilClass.formValidation(OTP, ".connUs-OTP-cont", "value", "OTP");
		
		if(returnVal)
		{
			var config = {};
			config.callType = "setPassword";
			config.type = "post";
			config.data= ({
					"userId" : userId,
					"password" : password,
					"OTP" : OTP
			});

			var sendOTPCbk = function(response)
			{
				var obj = JSON.parse(response);
				var status = obj.properties.data.status;
				if(status == 1)
				{
					utilClass.showError(".connUs-OTP-cont", "value", "OTP is wrong", true);
				}
				else if(status == 2)
				{
					utilClass.showError(".connUs-OTP-cont", "value", "OTP is expired", true);
				}
				else
				{
					$(".connUs-OTP-cont").removeAttr("style");
					$(".connUsform").removeClass("connUs-OTP-form");
					$(".connUsform").addClass("connUs-OTPSucess-form");
				}
			};
			
			utilClass.makeAjaxCall(config, sendOTPCbk);
		}
	},
	
	setPswSuccess : function()
	{
		utilClass.setCookie("userId", userId);
		window.location.href = "/WEB-INF/home.jsp";
	}
}