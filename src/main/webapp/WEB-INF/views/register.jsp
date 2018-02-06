<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <div class="connUs-signup-Cont">   
          <div class="cont-label">Sign Up for Free</div>
          <input type="text" id="signup-Firstname" name="firstname" placeholder="Firstname Lastname"/>
          <input type="email" id="signup-Email" name="email" placeholder="Email"/>
          <input type="password" id="signup-Password" name="password" placeholder="Password"/>
          <input type="date" id="signup-Dob" name="dob" placeholder="Date Of Birth" min="1950-01-31" max="2006-12-31"/>
          <select onchange="print_state('connUs-state',this.selectedIndex);" id="connUs-country" name ="country"></select>
          <select name ="state" id ="connUs-state"><option value="">Select State</option></select>
          
          <div class="connUs-radio-Cont">
            <input class="gender-radio" type="radio" name="gender" value="male"/><div class="connUs-gender-label">Male</div>
            <input class="gender-radio" type="radio" name="gender" value="female"/><div class="connUs-gender-label">Female</div>
          </div>
          
          <div class="connUs-signUp-Btn"/></div>
    </div>
    <div class="connUs-emailVerification-cont">
          <div class="cont-label">Thank you for joining with Us</div>
          <div class="emailVerification-fun-label">Have Fun !</div>
          <div class="emailVerification-content">Please check your email to activate your account</div>
          <button type="submit" class="emailVerification-ok-Btn"/>Ok</button>
    </div>
</body>
</html>