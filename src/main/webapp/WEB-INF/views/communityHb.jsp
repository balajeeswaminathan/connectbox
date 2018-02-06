<html>
<script id="communityData-hb-template" type="text/x-handlebars-template">
 <div id="comm-cont" class="{{#if isNew}} comm-new{{/if}}{{#if isOwned}} comm-owned{{/if}}{{#if isMember}} comm-member{{/if}}{{#if isNonMember}} comm-non-member{{/if}}{{#if isLiked}} comm-liked{{/if}}" ownerId="{{ownerId}}">
 	<div class="comm-img-cont">
	 	<img class="comm-cover-img" src="{{#if coverImg}}{{coverImg}}{{else}}//localhost/chatClient/img/coverImg.jpeg{{/if}}"/>
	 	<form id="comm-upload-cover-img">
	        <div class="comm-add-img-icon"></div>
	        <input id="comm-upload-cover-img-input" type="file" name="file" />
	    </form>
	 	<img class="comm-img" src="{{#if img}}{{img}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}"/>
	 	<form id="comm-upload-img">
	        <div class="comm-add-img-icon"></div>
	        <input id="comm-upload-img-input" type="file" name="file" />
	    </form>
	</div>
	<div class="comm-outer-cont">
		<div class="comm-left-cont">
			<div class="comm-edit-btn"></div>
		 	<input class="comm-name" type="text" placeholder="Name" {{#if name}}value = "{{name}}"{{/if}}>
		 	<textarea class="comm-address" type="text" placeholder="Address">{{#if address}}{{address}}{{/if}}</textarea>
		 	<input class="comm-email" type="text" placeholder="Email" {{#if email}}value = "{{email}}"{{/if}}>
		 	<input class="comm-phNo" type="text" placeholder="Phone Number" {{#if phNo}}value = "{{phNo}}"{{/if}}>
		 	<div class="comm-update">Update</div>
		</div>
		<div class="comm-right-cont">
			<div class="comm-like-btn"></div>
			<div class="comm-connect-btn">Connect</div>
			<div class="comm-disconnect-btn">Disconnect</div>
			<div class="comm-like-cont">
				<span class="comm-like-count">{{likeCount}}</span>
				<span class="comm-like-count-txt">Likes</span>
			</div>
			<div class="comm-member-cont">
				<span class="comm-member-count">{{memberCount}}</span>
				<span class="comm-member-count-txt">Member</span>
			</div>
		</div>
		<div class="comm-create">Create</div>
	</div>
 </div>
 
</script>
</html>