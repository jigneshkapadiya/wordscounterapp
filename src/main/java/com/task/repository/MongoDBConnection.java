package com.task.repository;



import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;

/**
 * MongoDBConnection class
 * @author Jignesh
 * @version 1.0
 * 
 */
		
public class MongoDBConnection
{
//	public static final Document update = new Document("$inc", new Document("count",1));

	public static final UpdateOptions updateOptions = new UpdateOptions().upsert(true);
	
	
	private MongoDBConnection()
	{
		// avoids creating instance of this class 
	} 
	
	private static class MongoClientCreator{
		private static final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		//private static final MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27018),new ServerAddress("localhost", 27020)));
	}
	private static class MongoCollectionCreator{
		private static final MongoCollection<Document> mongoCollection = getMongoClient().getDatabase(Constants.MongoDBDatabase).getCollection(Constants.MongoDBCollection);
	}
/*	private static class MongoUpdateOptionsCreator{
		private static final UpdateOptions updateOptions = new UpdateOptions().upsert(true);
	}
*/
	/**
	 * creates a singleton object of MongoClient class 
	 * @return MongoClient return a singleton object of MongoClient
	 */
	public static MongoClient getMongoClient()
	{
		return MongoClientCreator.mongoClient;
	}
	/**
	 * creates a singleton object of MongoCollection class 
	 * @return MongoCollection return a singleton object of MongoCollection
	 */
	public static MongoCollection<Document> getMongoCollection()
	{
		return MongoCollectionCreator.mongoCollection;
	}
	public static void createIndex(MongoCollection<Document> mongoCollection)
	{
		mongoCollection.createIndex(Indexes.ascending("word"),new IndexOptions().unique(true));
	}


}
