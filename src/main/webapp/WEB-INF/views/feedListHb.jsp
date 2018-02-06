<html>
<script src="//localhost/chatClient/js/HandlebarHelper.js"></script>
<script id="feedListData-hb-template" type="text/x-handlebars-template">
{{#if data}}
     <div class="connUs-feed-head">Feed</div>
        
     {{#if data.feedList}}
         {{#each data.feedList}}
             <div class="connUs-feed-cont" feedId="{{_id}}" userFeedId="{{#if userFeedId}}{{userFeedId}}{{else}}{{userId}}{{/if}}">
             	
             	<div class="feed-dateAndTime">{{likeOrCmntdateAndTime}}</div>
             	{{#isFeedLikeOrCmnts type}}
             	 <div class="connUs-feed-likeOrCmnt-cont">
             	    <img class="feed-userImg" src="{{#if userImg}}{{userImg}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}"/>
					<div class="feed-user-cont">
					   <div class="feed-userName">{{userName}}</div>
        				<div class="feed-likeOrCmntsLabel">{{type}}ed this</div>
        				{{#if comments}} 
        					<div class="feed-cmntsTxt">{{comments}}</div>
        				{{/if}}
        			</div>
					</div>
				{{/isFeedLikeOrCmnts}}
				 
				 <div class="connUs-feed-nameAndImg-cont">
				    <div class="feed-dateAndTime">{{dateAndTime}}</div>
				    <img class="feed-userImg" src="{{#if userFeedUserImg}}{{userFeedUserImg}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}"/>
    				<div class="feed-user-cont">
    					<div class="feed-userName">{{#if userFeedUserName}}{{userFeedUserName}}{{else}}{{userName}}{{/if}}</div>
    					
    					{{#isFeedCheckType type "updateProfile"}}
                            <div class="feed-desc">has updated profile </div>
                            {{#renderUpdatedKeys updatedKeys}}{{/renderUpdatedKeys}}
                            {{#renderUpdatedValues updatedKeys updatedValues}}{{/renderUpdatedValues}}
                        {{/isFeedCheckType}}
                        
                        {{#isFeedCheckType type "photo"}}
                           <div class="feed-desc">has uploaded new photo</div>
                        {{/isFeedCheckType}}
                        
                        {{#isCommFeedOrLikeOrCmnts type}}
                            <div class="feed-comm-arrow"> > </div>
                            <div class="feed-comm-cont">
                                <img class="feed-commImg" src="{{#if img}}{{img}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}"/>
                                <div class="feed-commName">{{#if name}}{{name}}{{/if}} community</div>
                            </div>
                        {{/isCommFeedOrLikeOrCmnts}}
                        
                        {{#isFeedCheckType type "createCommunity"}}
                           <div class="feed-comm-cont">
                                <div class="feed-desc">has created </div>
                                <div class="feed-commName">{{#if name}}{{name}}{{/if}} community</div>
                                {{#renderUpdatedKeys updatedKeys}}{{/renderUpdatedKeys}}
                                {{#renderUpdatedValues updatedKeys updatedValues}}{{/renderUpdatedValues}}
                            </div>
                        {{/isFeedCheckType}}
                        
                        {{#isFeedCheckType type "updateCommunity"}}
                           <div class="feed-comm-cont">
                                <div class="feed-desc">has updated </div>
                                <div class="feed-commName">{{#if name}}{{name}}{{/if}} community</div>
                                {{#renderUpdatedKeys updatedKeys}}{{/renderUpdatedKeys}}
                                {{#renderUpdatedValues updatedKeys updatedValues}}{{/renderUpdatedValues}}
                            </div>
                        {{/isFeedCheckType}}
    				</div>
			     </div>
			     
			     {{#if desc}}
                    <div class="connUs-feed-desc">{{{desc}}}</div>
                 {{/if}}
                 {{#if imgUrl}}
                     <div class="connUs-feed-imgCont" id="gallery">
                         <img class="connUs-feed-img" src="{{imgUrl}}" alt="{{desc}}" data-image="{{imgUrl}}" data-description="{{desc}}">
                     </div>
                 {{/if}}
                 
				 {{#if videoUrl}}
				 	<div class="feed-videoCont">
                     	<iframe width="100%" height="300px" src="{{videoUrl}}" allowfullscreen="allowfullscreen" mozallowfullscreen="mozallowfullscreen" msallowfullscreen="msallowfullscreen" oallowfullscreen="oallowfullscreen" webkitallowfullscreen="webkitallowfullscreen"></iframe>
                    </div>
                 {{/if}}
                 
                 <div class="feed-likeCmnts-cont">
	                 <div class="connUs-giveLike" {{#if isLiked}}id="connUs-liked"{{/if}}></div>
	                 <div class="connUs-moreLikesAndCmnts-cont">
						{{#if likeCount}}
	                     	<div class="connUs-viewMoreLikes" id="feed-like-{{_id}}">{{likeCount}} Likes</div>
						{{else}}
							<div class="connUs-noLikes">No Likes Yet!</div>
						{{/if}}
						
						{{#if cmntsCount}}
	                     	<div class="connUs-viewMoreCmnts" id="feed-cmnts-{{_id}}">{{cmntsCount}} Comments</div>
						{{else}}
							 <div class="connUs-noCmnts">No Comments Yet!</div>
						{{/if}}
	                 </div>
	             </div>
                 <div class="connUs-feedLike-wrapper" id="connUs-feedLike-wrapper-{{_id}}"></div>
                 <div class="connUs-feedCmnts-wrapper" id="connUs-feedCmnts-wrapper-{{_id}}"></div>
                 <div class="connUs-giveCmnts-cont">
                     <textarea class="connUs-cmntsTxt" type="text" placeholder="Add your Comments" data-emojiable="true"/></textarea>
                     <div class="connUs-cmnts-smiley"></div>
                     <div class="connUs-addcomnts-btn">Add</div>
                 </div>
             </div>
         {{/each}}
     {{/if}}

{{/if}}
</script>
</html>