$( document ).ready(function() {
	homeClass.init();
});


var homeClass = {
	
	init : function()
	{
		var _self = this;
		_self.RegisterEvents();
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
		
	}
}