package com.task.repository;

import java.util.function.Consumer;

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
	public static void mostCommon()
	{
		System.out.print("Most common word in the file is : ");
		Consumer<Document> consumer = d-> System.out.println(d.get(Constants.WordField)+" ("+d.get(Constants.CountField)+")");
		MongoDB.getMongoClient().getDatabase(Constants.MongoDBDatabase).getCollection(Constants.MongoDBCollection)
		.find(new Document()).sort(new Document(Constants.CountField, -1)).limit(1)
		.forEach(consumer);
	}
	public static void leastCommon()
	{
		System.out.print("Least common word in the file is : ");
		Consumer<Document> consumer = d-> System.out.println(d.get(Constants.WordField)+" ("+d.get(Constants.CountField)+")");
		MongoDB.getMongoClient().getDatabase(Constants.MongoDBDatabase).getCollection(Constants.MongoDBCollection)
		.find(new Document()).sort(new Document(Constants.CountField, 1)).limit(1)
		.forEach(consumer);
	}
}

