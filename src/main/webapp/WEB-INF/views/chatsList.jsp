<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<body>
<script id="chatsList-hb-template" type="text/x-handlebars-template">
    {{#if data}}
		<div class="connUs-chatsList-inner-wrapper">
          {{#if data.chats}}
             {{#each data.chats}}
                    <div class="connUs-chatsList-inner-cont" chatListId="{{chatListId}}">
                          <div class="connUs-chatsList-img"></div>
                          <div class="connUs-chatData-cont">
                                <div class="connUs-chatsList-name">{{chatNames}}</div>
                                <div class="connUs-chatsList-dateAndTime">{{dateAndTime}}</div>
                                <div class="connUs-chatsList-lastMgs">{{{lastMgs}}}</div>
                         </div>
                    </div>
              {{/each}}
		  {{else}}
			  <div class="connUs-noChat">"No Chat Yet"</div>
          {{/if}}
		</div>
    {{/if}}
</script>
</body>
</html>