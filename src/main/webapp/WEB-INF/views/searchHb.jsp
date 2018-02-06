<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script src="//localhost/chatclient/js/search.js"></script>
    </head>
<body>
<script id="searchList-hb-template" type="text/x-handlebars-template">
    <div class="connUs-search-wrapper">
        {{#if data.userSearchList}}
		     <div class="people-head">People</div>
             {{#each data.userSearchList}}
                <div class="connUs-searchList-cont" userId="{{userId}}">
                    <img class="connUs-search-img" src="{{#if userImgUrl}}{{userImgUrl}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}"></img>
					<div class="userData-cont">
                    	<div class="connUs-search-userName">{{userName}}</div>
						<div class="connUs-search-gender">{{gender}}</div>
					</div>
                 </div>
             {{/each}}
        {{/if}}
		{{#if data.topicSearchList}}
		     <div class="topic-head">Topics</div>
             {{#each data.topicSearchList}}
                <div class="connUs-searchList-cont" topicId="{{topicId}}">
                    <img class="connUs-search-img" src="{{#if topicImgUrl}}{{topicImgUrl}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}"></img>
                    <div class="connUs-search-topicName">{{topicName}}</div>
                 </div>
             {{/each}}
        {{/if}}
		{{#if data.errorMessage}}
			<div class="errorMsg">{{data.errorMessage}}</div>
		{{/if}}
    </div>
</script>
</body>
</html>