package com.task.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * MongoDB class helps to create a singleton object of MongoClient
 * @author Jignesh
 * @version 1.0
 * 
 */
public class MongoDB
{
	private static String connection=Constants.MongoDBConnection;
	
	private MongoDB()
	{
		// avoids creating instance of this class 
	}
	/**
	 * 
	 * @param connectionURL connection url of mongodb eg. localhost:27017
	 */
	public static void setConnection(String connectionURL)
	{
		if(!connectionURL.isEmpty())
		{
			connection = connectionURL;
		}
	}
	private static class MongoClientCreator{
		private static final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://"+connection));
		//private static final MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27018),new ServerAddress("localhost", 27020)));
	}
	/**
	 * creates a singleton object of MongoClient class 
	 * @return MongoClient return a singleton object of MongoClient
	 */
	public static MongoClient getMongoClient()
	{
		return MongoClientCreator.mongoClient;
	}

}
