<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<body>
<script id="friendsList-hb-template" type="text/x-handlebars-template">
{{#if data}}
    {{#if data.friendsList}}
       {{#each data.friendsList}}
           <div class="connUs-frnds">
              <div class="connUs-frnds-img-cont">
                    <div class="connUs-frnds-online"></div>
                    <img class="connUs-frnds-img" src="{{#if imgPath}}{{imgPath}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}"/>
              </div>
              <div class="connUs-frnds-name">{{friendsName}}</div>
			  <div class="connUs-frnds-gender">{{gender}}</div>
           </div>
        {{/each}}
    {{else}}
        <div class="connUs-frnds-no">No Friends Yet</div>
    {{/if}}
{{/if}}
</script>
</body>
</html>