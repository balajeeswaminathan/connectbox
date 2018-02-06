<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Connecting Us</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="/WEB-INF/js/Utils/utils.js"></script>
    <script src="/WEB-INF/js/forgetPsw.js"></script>
    <link href="/WEB-INF/css/forgetPsw.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width" />
</head>
<body>
    <div class="connUs-header-cont">
        <div class="connUs-header-label">Connecting Us</div>
    </div>
    <div class="connUsform">
	    <div class="connUs-forget-cont">
	    	<input type="password" id="forget-Password" placeholder="New Password"/>
	        <input type="password" id="forget-retype-password" placeholder="Retype New Password"/>
	        <button class="psw-reset-btn">Update</button>
	    </div>
	    <div class="connUs-OTP-cont">
	    	<div class="connUs-OTP-content"></div>
	    	<input type="tel" id="OTP-value" placeholder="OTP" maxlength="5"/>
	    	<button class="otp-send-btn">Send</button>
	    </div>
	    <div class="connUs-OTP-success">
	    	<div class="connUs-OTP-content">New Password has been updated successfully!</div>
	    	<button class="otp-success-btn">Ok</button>
	    </div>
	</div>
</body>