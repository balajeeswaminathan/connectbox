<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script id="topicsListData-hb-template" type="text/x-handlebars-template">
<div class="connUs-topicsList-cont">
    {{#if data}}
        {{#if data.topicsList}}
            {{#each data.topicsList}}
                 <div class="connUs-topicsList-innerCont" topicId="{{_id}}">
                     <img class="connUs-topicsList-img" src="{{imgUrl}}"/>
                     <div class="connUs-topicsList-name">{{name}}</div>
                 </div>
            {{/each}}
        {{/if}}
    {{/if}}
</div>
</script>
</html>