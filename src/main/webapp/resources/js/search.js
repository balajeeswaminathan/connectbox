$( document ).ready(function() {
	searchClass.init();
});


var searchClass = {

	init : function()
	{
		var _self = this;
		_self.RegisterEvents();
	},

	RegisterEvents : function()
	{
		var _self = this;
		
		$(".connUs-search-txt").keyup(function(){
			_self.showdropDownBox();
			_self.madeSearchListCall();
		});

		$(".close-icon").off("click").on("click", function(){
		    _self.closeSearch();
		});
	},
	
	showdropDownBox : function(){
		var searchTermLen = $(".connUs-search-txt").val().length;
		$("body").addClass("connUs-searchDropBoxActive");
		utilClass.showMask();
		if(searchTermLen == 0)
		{
			$("body").removeClass("connUs-searchDropBoxActive");
			utilClass.hideMask();
		}
		$(".connUs-Mask").off("click").on("click",function(){
			$("body").removeClass("connUs-searchDropBoxActive");
			utilClass.hideMask();
		});
	},
	
	madeSearchListCall : function()
	{	
		var searchTerm = $(".connUs-search-txt").val();
		
		if(searchTerm.length > 0)
		{
		    $(".connUs-search-cont").addClass("active");
 
		    var config = {};
			config.callType = "searchList"
			config.type = "get";
			config.data= ({
					"searchTerm" : searchTerm
			});
			
			var searchCbk = function(response)
			{
				var obj = JSON.parse(response);
				utilClass.callHandlebar("#searchList-hb-template", ".connUs-search-list", obj.properties);
				$(".connUs-searchList-cont").off("click").on("click",function(){
					var profileId = $(this).attr("userId");
					window.location.href = "http://localhost:8080/chatUI/profile.jsp?pId=" + profileId;
				});
			};
			
			utilClass.makeAjaxCall(config, searchCbk);
		}
		else
		{
		    $(".connUs-search-cont").removeClass("active");
		    $(".connUs-search-wrapper").hide();
		}
	},
	
	closeSearch : function()
	{
		$(".connUs-search-txt").val("");
	    $(".connUs-search-cont").removeClass("active");
	    $(".connUs-search-wrapper").hide();
	}
};