<body>
<script id="chatsList-hb-template" type="text/x-handlebars-template">
    {{#if data}}
		<div class="connUs-chatsList-wrapper">
          {{#if data.chats}}
             {{#each data.chats}}
                    <div class="connUs-chatsList-cont" chatListId="{{chatListId}}">
                          <img class="connUs-chatsList-img" src={{#if imgPath}} imgPath {{else}} https://res.cloudinary.com/connectbox/image/upload/v1526567231/male-profile.png {{/if}}></img>
                          <div class="connUs-chatData-cont">
                                <div class="connUs-chatsList-name">{{chatNames}}</div>
                                <div class="connUs-chatsList-dateAndTime">{{dateAndTime}}</div>
                                <div class="connUs-chatsList-lastMgs">{{{lastMgs}}}</div>
								{{#if newMsgCount}}
									<div class="connUs-chatsList-lastMgs">{{{newMsgCount}}}</div>
								{{/if}}
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