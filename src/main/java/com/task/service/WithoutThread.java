package com.task.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Instant;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.task.repository.MongoDBConnection;

public class WithoutThread
{
	public static void main (String[] args)
	{
		System.out.println("Hello World");
		//File file = new File("D:/testfile/enwiki-latest-pages-articles-multistream.xml");
		File file = new File("D:/testfile/myfile.xml");
		//File file = new File("D:/testfile/test.xml");
		Instant countStart = Instant.now();
		int lineCount = 0;
		String readLine = "";
		System.out.println("Reading file using BufferedReader");
		MongoClient mongoClient = MongoDBConnection.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("nothread");
		MongoCollection<Document> collection = database.getCollection("wordscount");
		UpdateOptions updateOptions = new UpdateOptions().upsert(true);
		String[] words;
		Document update = new Document("$inc", new Document("Count",1));
		Document filter;
		try(BufferedReader b = new BufferedReader(new FileReader(file)))
		{
			while((readLine = b.readLine())!= null)
			{
				lineCount++;
				System.out.println(readLine);
				words = readLine.split("\\P{L}+");   // \\p{L}+ \\P{Alpha}+  \\W+  \\P{L}+
				for (String string : words)
				{
					filter = new Document("Word",string);
//					modifiedObject.put("$inc", new BasicDBObject().append("hits", 1));
					collection.updateOne(filter, update, updateOptions);
				}
			}
			b.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mongoClient.close();
		Instant countEnd = Instant.now();
		System.out.println("Count start = "+countStart.toString());
		System.out.println("Count end = "+countEnd.toString());
		System.out.println("No of lineCount = "+lineCount);
		System.out.println("Bye");
	}
}
