package com.task.service.copy2;


import java.util.function.Consumer;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.task.repository.Constants;
import com.task.repository.MongoDB;

public class MostCommonUsed
{
	public static void main(String args[])
	{
		try
		{
			MongoCollection<Document> coll = MongoDB.getMongoClient().getDatabase(Constants.MongoDBDatabase).getCollection(Constants.MongoDBCollection);
			FindIterable<Document> most = coll.find(new Document()).sort(new Document("count", -1)).limit(1);
			//Consumer<String> consume = System.out::println;
			System.out.println("Print"+(most==null?"me":"no"));
			System.out.println(most.toString());
			//Iterator<> itr = most.iterator();
			Consumer<Document> consumer = d-> System.out.println(d.get("word"));
			Block<Document> printBlock = new Block<Document>() {
			       @Override
			       public void apply(final Document document) {
			           System.out.println(document.toJson());
			       }
			};
			most.forEach(consumer);
			//most.forEach(printBlock);
			System.out.println("Print done");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
