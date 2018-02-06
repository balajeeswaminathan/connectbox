<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<body>
<script>
Handlebars.registerHelper('compare', function(lvalue, rvalue, options) {

    if (arguments.length < 3)
        throw new Error("Handlerbars Helper 'compare' needs 2 parameters");

    operator = options.hash.operator || "==";

    var operators = {
        '==':       function(l,r) { return l == r; },
        's==':       function(l,r) { return ''+l == ''+r; },
        '===':      function(l,r) { return l === r; },
        '!=':       function(l,r) { return l != r; },
        's!=':       function(l,r) { return ''+l != ''+r; },
        '<':        function(l,r) { return l < r; },
        '>':        function(l,r) { return l > r; },
        '<=':       function(l,r) { return l <= r; },
        '>=':       function(l,r) { return l >= r; },
        '||':       function(l,r) { return l || r; },
        '&&':       function(l,r) { return l && r; }, 
        'typeof':   function(l,r) { return typeof l == r; },
        '%':   function(l,r, miv) {
            return ((parseInt(l) % parseInt(r) == 0) ? true : false); 
        },
        '%+1':   function(l,r, miv) {
            var currnetValue = parseInt(l) + 1;
            return ((currnetValue % parseInt(r) == 0) ? true : false); 
        }
    };

    if (!operators[operator])
        throw new Error("Handlerbars Helper 'compare' doesn't know the operator "+operator);

    var result = operators[operator](lvalue,rvalue);

    if( result ) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});
</script>
<script id="profileData-hb-template" type="text/x-handlebars-template">
{{#if data}}
    {{#if data.profileDataList}}
		<div class="connUs-profileData-cont">
			{{#if data.profileDataList.myProfile}}
				<div class="profile-edit-icon"></div>
				<div class="profile-edit-saveBtn"></div>
			{{/if}}
			<div class="connUs-profileData-img-cont">
				<img class="connUs-profileData-img" src="{{#if data.profileDataList.profileImgUrl}}{{data.profileDataList.profileImgUrl}}{{else}}//localhost/chatClient/img/photo.jpg{{/if}}"/>
				{{#if data.profileDataList.myProfile}}
    				<div class="profileImg-edit-btn"></div>
                {{/if}}
				{{#compare  data.profileDataList.myProfile true operator="!="}}
				    {{#compare data.profileDataList.friendStatus null operator="=="}}
				    	<div class="connUs-connect-btn" type="connect">Connect</div>
				    {{/compare}}
				    {{#compare data.profileDataList.friendStatus -1 operator="=="}}
				    	<div class="connUs-connect-btn" type="requested">Requested</div>
				    {{/compare}}
				    {{#compare data.profileDataList.friendStatus 0 operator="=="}}
				    	<div class="connUs-connect-btn" type="connected">Connected</div>
				    {{/compare}}
				    {{#compare data.profileDataList.friendStatus 1 operator="=="}}
				    	<div class="connUs-connect-btn" type="accept">Accept</div>
				    {{/compare}}
				{{/compare}}
			<div class="connUs-msg">Message</div>
			</div>
			<div class="connUs-profileData-innerCont">
				<input type="text" class="profile-name" value="{{data.profileDataList.profileName}}"/>
				<input type="text" class="profile-gender" value="{{data.profileDataList.gender}}"/>
				<input type="text" class="profile-age" value="{{data.profileDataList.age}}"/>
				<input type="date" class="profile-dob" min="1950-01-31" max="2006-12-31" value="{{data.profileDataList.dob}}"/>
				
				<select onchange="print_state('connUs-state',this.selectedIndex);" id="connUs-country" name ="country"></select>
				<select name ="state" id ="connUs-state"></select>
			</div>
		</div>
		<div class="connUs-upload-picCont">
		    <div class="upload-pic-close-icon">X</div>
		    <div class="upload-crop-cont">
		      <div id="upload-crop-img"></div>
		      <div class="upload-crop-img-close"></div>
		      <div class="upload-crop-img-conform"></div>
		    </div>
		    <div class="upload-wrapper">
    		    <div class="upload-cont">
        		    <form id="profileImg-edit">
        		        <div class="upload-icon"></div>
                        <div class="upload-txt">upload from your device</div>
                        <input id="profileImgfile" type="file" name="file" />
                    </form>
        		    <div class="upload-line"></div>
        		    <div class="upload-or-txt">or</div>
        		    <div class="upload-line"></div>
    		    </div>
		        <div class="display-img-wrapper"></div>
		   </div>
		</div>
    {{/if}}
{{/if}}
</script>
<script id="displayPhotos-hb-template" type="text/x-handlebars-template">
{{#if data}}
    {{#if data.photosList}}
       <div class="connUs-display-img-cont">
          {{#each data.photosList}}
               <img class="connUs-display-Img" alt="{{desc}}" src="{{imgUrl}}" data-image="{{imgUrl}}" data-description="{{desc}} <br>{{dateAndTime}}"/>
          {{/each}}
       </div>
    {{/if}}
{{/if}}
</script>
</body>
</html>