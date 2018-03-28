package com.smp.service;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.smp.utils.Util;

public class Pagination{
	DB database = Util.mongoClientInit();

	public List getPaginationData(String collName,  boolean isReverse, int skipLevel, int limits){
		DBCollection collection = database.getCollection(collName);
		DBCursor resultSet;
		List<DBObject> DBList = new ArrayList<DBObject>();
		
		resultSet = collection.find().skip(skipLevel).limit(limits);
		DBList = resultSet.toArray();
		if(isReverse)
		{
			Collections.reverse(DBList);
		}
		return DBList;
	}
	
	public int getSkipLevel(String collName, int pageLevel, int limits){
		DBCollection collection = database.getCollection(collName);
		DBCursor resultSet = collection.find();
		int docCount = resultSet.count();
		int skipLevel = (docCount - limits * pageLevel);
		
		return skipLevel;
	}
	
	public int getLimits(int skipLevel, int limits){
		limits = skipLevel + limits;
		
		return limits;
	}
}