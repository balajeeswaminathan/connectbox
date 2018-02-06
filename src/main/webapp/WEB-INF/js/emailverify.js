$( document ).ready(function() {
	emailVerifyClass.init();
});

var emailVerifyClass = {
		
	init : function()
	{
		var _self = this;
		_self.RegisterEvents();
	},
	
    RegisterEvents : function()
	{
		var _self = this;
		
		_self.activateUser();
		$(".emailVerify-btn").off("click").on("click",function(){
			window.location.href = "/WEB-INF/home.jsp";
		});
	},
	
	activateUser : function()
	{
		var _self = this;
		var userId = utilClass.getQueryParam("pId");
		
		var config = {};
		config.callType = "activateUser"
		config.type = "POST";
		config.data= ({
				"userId" : userId
		});
		
		utilClass.makeAjaxCall(config);
	}
}