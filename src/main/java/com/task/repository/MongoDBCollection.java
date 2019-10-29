package com.task.repository;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

/**
 * MongoDBCollection class
 * @author Jignesh
 * @version 1.0
 * 
 */
public class MongoDBCollection
{
	private static class MongoCollectionCreator
	{
		private static final MongoCollection<Document> mongoCollection = MongoDB.getMongoClient().getDatabase(Constants.MongoDBDatabase).getCollection(Constants.MongoDBCollection);
	}
	/**
	 * creates a singleton object of MongoCollection class 
	 *  
	 * @return MongoCollection return a singleton object of MongoCollection
	 */
	public static MongoCollection<Document> getMongoCollection()
	{
		return MongoCollectionCreator.mongoCollection;
	}
	public static void createDatabase()
	{
		
	}
	public static void initialiseCollection()
	{
		MongoDB.getMongoClient().getDatabase(Constants.MongoDBDatabase).getCollection(Constants.MongoDBCollection);
		
	}
	
	public static void createUniqueIndex()
	{
		MongoDB.getMongoClient().getDatabase(Constants.MongoDBDatabase).getCollection(Constants.MongoDBCollection)
		.createIndex(Indexes.ascending(Constants.WordField), new IndexOptions().unique(true));
	}
}
