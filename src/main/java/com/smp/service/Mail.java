package com.smp.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
   
public class Mail{
	private JavaMailSender mailSender;  
	   
    public void setMailSender(JavaMailSender mailSender) {  
        this.mailSender = mailSender;  
    }  

    public void sendMail(String to, String subject, String messageDom) throws MessagingException {  
    	 MimeMessage message = mailSender.createMimeMessage(); 
    	 MimeMessageHelper helper = new MimeMessageHelper(message, true);  
    	 helper.setTo(to);
    	 helper.setSubject(subject);

    	 helper.setText(messageDom,true);
    	 mailSender.send(message);
    }  
}  