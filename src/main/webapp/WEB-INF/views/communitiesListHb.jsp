<html>
<script id="communitiesListData-hb-template" type="text/x-handlebars-template">
<div class="comm-list-cont">
    {{#if data}}
        {{#if data.isYours}}
             <div class="comm-add-new-cont">
             	<div class="comm-add-new-plus"></div>
             	<div class="comm-add-new-txt">Add New</div>
             </div>
        {{/if}}
        
        {{#each data.communityList}}
            <div class="comm-list">
                <img class="comm-cover-img" src="{{#if coverImg}}{{coverImg}}{{else}}//localhost/chatClient/img/coverImg.jpeg{{/if}}"/>
                <img class="comm-img" src="{{#if img}}{{img}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}"/>
                <div class="comm-details-cont">
                    <div class="comm-name">{{name}}</div>
                    <div class="comm-member-cont">
                        <span class="comm-member-count">{{memberCount}}</span>
                        <span class="comm-member-count-txt">Member</span>
                    </div>
                </div>
            </div>
        {{/each}}
    {{/if}}
</div> 
</script>
</html>