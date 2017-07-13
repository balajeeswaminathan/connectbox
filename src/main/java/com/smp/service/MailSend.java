package com.smp.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.*;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.*;

@SuppressWarnings("deprecation")
public class MailSend
{
	public void sendMail(String receiverEmail, String mailSubject, String mailDom) throws MessagingException
	{  
	      
		Resource resource = new ClassPathResource("applicationContext.xml");  
		BeanFactory beanFactory = new XmlBeanFactory(resource);  
		Mail mail = (Mail)beanFactory.getBean("mailMail");  
		String sender="pamsid07@gmail.com";//write here sender gmail id  
		mail.sendMail(sender, receiverEmail, mailSubject, mailDom);
	}
}  