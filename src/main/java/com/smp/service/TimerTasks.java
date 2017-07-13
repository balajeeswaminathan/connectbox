package com.smp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * No need to implement any interface
 * */
public class TimerTasks {
 
	@Autowired
	private MongoTemplate mongoTemplate;
    //Define the method to be called as configured
    public void clearHomeFeeds()
    {
    	mongoTemplate.remove(new Query(), "bala_2016_09_30_19_52_13_ChatList");
        System.out.println("Executed task on :: ");
    }
}