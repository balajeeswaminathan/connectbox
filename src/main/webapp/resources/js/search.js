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
		
		$(".connUs-mobile-search-icon, .connUs-mobile-search-back-icon").off("click").on("click", function(){
		    _self.openSearchInput();
		});
		
		$(".connUs-search-txt").keyup(function(){
			_self.madeSearchListCall();
		});

		$(".search-close-icon").off("click").on("click", function(){
		    _self.closeSearch();
		});
	},
	
	openSearchInput : function(){
		$(".connUs-search-cont").toggleClass("mobile-search-active");
	},
	
	madeSearchListCall : function()
	{	
		var _self = this;
		var searchTerm = $(".connUs-search-txt").val().toLowerCase();
		
		if(searchTerm.length > 0)
		{
			$("body").addClass("connUs-searchDropBoxActive");
			utilClass.showMask();
			
			$(".connUs-Mask").off("click").on("click",function(){
				_self.closeSearch();
			});
			
		    var config = {};
			config.callType = "searchList"
			config.type = "POST";
			config.data= ({
					"searchTerm" : searchTerm
			});
			
			var searchCbk = function(response)
			{
				var obj = JSON.parse(response);
				utilClass.callHandlebar("#searchList-hb-template", ".connUs-search-list", obj.properties);
				$(".connUs-searchList-cont").off("click").on("click",function(){
					var profileId = $(this).attr("userId");
					window.location.href = window.streamDomain + "profile.jsp?pId=" + profileId;
				});
			};
			
			utilClass.makeAjaxCall(config, searchCbk);
		}
		else
		{
			$("body").removeClass("connUs-searchDropBoxActive");
			utilClass.hideMask();
			$(".connUs-search-wrapper").remove();
		}
	},
	
	closeSearch : function()
	{
		utilClass.hideMask();
		$(".connUs-search-txt").val("");
	    $("body").removeClass("connUs-searchDropBoxActive");
	    $(".connUs-search-wrapper").remove();
	}
};