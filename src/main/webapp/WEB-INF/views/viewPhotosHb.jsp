<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<script id="viewPhotos-hb-template" type="text/x-handlebars-template">
{{#if data}}
	{{#if data.photosList}}
       <div class="connUs-viewPtos-cont" id="gallery">
          {{#each data.photosList}}
               <img class="connUs-viewPtos-Img" alt="{{desc}}" src="{{imgUrl}}" data-image="{{imgUrl}}" data-description="{{desc}} <br>{{dateAndTime}}" imgIdx="{{@index}}"/>
          {{/each}}
       </div>

	<div class="connUs-imgSliderDesc-cont">
      {{#each data.photosList}}
        <div class="connUs-imgSliderDesc" descIdx="{{@index}}" index={{_id}}>
             	 <div class="connUs-ptosGiveLike" {{#if isLiked}}id="connUs-ptosLiked"{{/if}}></div>
                 <div class="connUs-ptosMoreLikesAndCmnts-cont">
					{{#if likeCount}}
                     	<div class="connUs-ptosViewMoreLikes" id="feed-like-{{_id}}">{{likeCount}} Likes</div>
					{{else}}
						<div class="connUs-ptosNoLikes">No Likes Yet!</div>
					{{/if}}
					{{#if cmntsCount}}
                     	<div class="connUs-ptosViewMoreCmnts" id="feed-cmnts-{{_id}}">{{cmntsCount}} Comments</div>
					{{else}}
						 <div class="connUs-ptosNoCmnts">No Comments Yet!</div>
					{{/if}}
                 </div>
                 <div class="connUs-ptosGiveCmnts-cont">
                     <input class="connUs-ptosCmntsTxt" type="text" placeholder="Give your Comments">
                     <div class="connUs-ptosAddcomnts-btn">Add Comments</div>
             	 </div>
        </div>
      {{/each}}
	  <div class="connUs-ptosLikeOrCmnts-wrapper"></div>
	</div>
   {{/if}}
{{/if}}
</script>
</body>
</html>