package com.smp.utils;

import java.net.UnknownHostException;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Util {
		
	public static DB mongoClientInit()
	{
		try
		{
			//MongoClientURI uri = new MongoClientURI("mongodb://smpadmin:manihigher@cluster0-shard-00-00-iisbh.mongodb.net:27017,cluster0-shard-00-01-iisbh.mongodb.net:27017,cluster0-shard-00-02-iisbh.mongodb.net:27017/smp?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");
			//MongoClientURI uri = new MongoClientURI("mongodb://smpadmin:manihigher@cluster0-iisbh.mongodb.net/test");
			MongoClientURI uri = new MongoClientURI("mongodb://balajee:manihigher@ds035485.mlab.com:35485/connectbox");
			MongoClient mongoClient;
			mongoClient = new MongoClient(uri);
			return mongoClient.getDB("connectbox");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}