<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<script>
getCookie = function(cname)
{
    var skCookieName = cname + "=";
    var skAllCookie = document.cookie.split(';');
   for(var i=0; i<skAllCookie.length; i++) 
   {
	   var tempCookie = skAllCookie[i].trim();
	   if (tempCookie.indexOf(skCookieName)==0) return tempCookie.substring(skCookieName.length,tempCookie.length);
   }
   return "";
}
Handlebars.registerHelper('userIsYou', function(userId, userName) {

	var user_Id = getCookie("userId");
	if(userId == user_Id)
	{
		return "You";
	}
	else
	{
		return userName;
	}
});
</script>
<script id="likeListData-hb-template" type="text/x-handlebars-template">
{{#if data}}
	{{#if data.likeOrCmntsList}}
        {{#each data.likeOrCmntsList}}
	    	<div class="connUs-cmntsOrLike-cont" likeUserId="{{_id}}">
		   		<div class="connUs-cmntsOrLike-userImg-cont">
			  		<img class="connUs-cmntsOrLike-img" src="{{#if userImg}}{{userImg}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}">
		   		</div>
		   		<div class="connUs-data-cont">
		   			<div class="connUs-cmntsOrLike-name">{{#userIsYou userId userName}}{{/userIsYou}}</div>
	      		</div>
	    	</div>
        {{/each}}
    {{else}}
        <div class="connUs-noCmntsOrLikeTxt">No Likes Yet!</div>
	{{/if}}
{{/if}}
</script>
</body>
</html>