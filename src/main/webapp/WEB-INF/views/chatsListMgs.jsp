<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<body>
<script>
	Handlebars.registerHelper('checkMap', function(commType, options) {
		if(commType == "map")
		{
			return options.inverse(this);
		}
		else
		{
			return options.fn(this);
		}
	});
	
	Handlebars.registerHelper('renderMap', function(commType, index, address) {
		if(commType == "map")
		{
		    var mapId = "chat-map" + index;
			var htmlStr = "<div class='chat-map' id=" + mapId + "></div>";
			setTimeout(function(){
				$($(".connUs-chatsListMgs-msg")[index]).before(htmlStr);
			
				var geocoder = new google.maps.Geocoder();
			    geocoder.geocode( { 'address': address}, function(results, status) {
    			      if (status == google.maps.GeocoderStatus.OK)
    			      {
    				        var latitude = results[0].geometry.location.lat();
    				        var longitude = results[0].geometry.location.lng();
    				        initialize(mapId, latitude, longitude);
    			      }
			    });
			},100);
		}
	});
	
	function initialize(mapId, latitude, longitude) {
        var latlng = new google.maps.LatLng(latitude,longitude);

        var myOptions = {
          zoom: 16,
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          mapTypeControl: false
        };
        var map = new google.maps.Map(document.getElementById(mapId), myOptions);

        var marker = new google.maps.Marker({
          position: latlng, 
          map: map, 
            title:"location : Dublin"
        }); 
      }
</script>
<script id="chatsListData-hb-template" type="text/x-handlebars-template">
    {{#if data}}
          {{#if data.chatList}}
             {{#each data.chatList}}
                    {{#if date}}<div class="connUs-chatListMsg-DateHead">{{date}}</div>{{/if}}
	                <div class="connUs-chatsListMgs-inner-cont {{#if sender}}connUs-chatsListMgs-sender{{/if}}">
	                    {{#renderMap commType @index message}}{{/renderMap}}
	                    <div class="connUs-chatsListMgs-msg">{{{message}}}</div>
	                    <div class="connUs-chatsListMgs-time">{{time}}</div>
	                </div>
              {{/each}}
		  {{/if}}
		  {{#if data.isEmpty}}
				<div class="connUs-emptyMsg">No Messages Yet</div>
          {{/if}}
    {{/if}}
</script>
</body>
</html>