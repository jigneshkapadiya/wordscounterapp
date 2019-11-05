package com.task;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import com.task.repository.Constants;
import com.task.repository.MongoDBConnection;

public class WithoutThread
{
	public static void main (String[] args)
	{
		System.out.println("Hello World");
		//File file = new File("D:/testfile/enwiki-latest-pages-articles-multistream.xml");
//		File file = new File("D:/testfile/new.xml");
		//File file = new File("D:/testfile/test.xml");
		
		int lineCount = 0;
		String readLine = "";
		System.out.println("Reading file using BufferedReader");
		MongoClient mongoClient = MongoDBConnection.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("nothreadfinal");
		MongoCollection<Document> collection = database.getCollection("wordscount");
		collection.createIndex(Indexes.ascending("word"), new IndexOptions().unique(true));
		UpdateOptions updateOptions = new UpdateOptions().upsert(true);
		String[] words;
		Document update = new Document("$inc", new Document("count",1));
		
		Document filter;
		Instant countStart = Instant.now();
		Map<String,Integer> wordCount = new HashMap<String,Integer>();
		List<String> linesList = new ArrayList<String>();
		//try(BufferedReader b = new BufferedReader(new FileReader(file)))
		try(BufferedReader b = new BufferedReader( new InputStreamReader(new FileInputStream("D:/testfile/new.xml"),"UTF-8")))
		{
			while((readLine = b.readLine())!= null)
			{
				linesList.add(readLine);
				lineCount++;
				if(lineCount%50000 == 0)
				{
					
					Map<String, Long> map = Stream.of(linesList) // Stream<Stream<String>>
				            .flatMap(Collection::stream) // Stream<String> 
				            .flatMap(Pattern.compile("\\P{L}+")::splitAsStream).filter(w->!w.isEmpty())
				            .collect(groupingBy(name -> name, counting()));
					List<WriteModel<Document>> list = new ArrayList<WriteModel<Document>>();
					for(Map.Entry<String, Long> entry: map.entrySet())
					{
						list.add(new UpdateOneModel<>(new Document("word",entry.getKey())
													, new Document("$inc", new Document("count",entry.getValue()))
													, new UpdateOptions().upsert(true)
													));
						//System.out.println(entry.getKey()+"/"+entry.getValue());
					}
					collection.bulkWrite(list,new BulkWriteOptions().ordered(false));
					
					linesList = new ArrayList<String>();
				}
				
				//System.out.println(readLine);
//				words = readLine.split("\\P{L}+");   // \\p{L}+ \\P{Alpha}+  \\W+  \\P{L}+
				//for (String string : words)
//				{
//					if(!string.isEmpty())
//					{
//						wordCount.merge(string, 1, Integer::sum);
//					filter = new Document("word",string);
///////					modifiedObject.put("$inc", new BasicDBObject().append("Count", 1));
//					collection.updateOne(filter, update, updateOptions);
//					}
//				}

			}
			
			if (linesList.size()>0)
			{
				Map<String, Long> map = Stream.of(linesList) // Stream<Stream<String>>
			            .flatMap(Collection::stream) // Stream<String> 
			            .flatMap(Pattern.compile("\\P{L}+")::splitAsStream).filter(w->!w.isEmpty())
			            .collect(groupingBy(name -> name, counting()));
				List<WriteModel<Document>> list = new ArrayList<WriteModel<Document>>();
				for(Map.Entry<String, Long> entry: map.entrySet())
				{
					list.add(new UpdateOneModel<>(new Document("word",entry.getKey())
												, new Document("$inc", new Document("count",entry.getValue()))
												, new UpdateOptions().upsert(true)
												));
					//System.out.println(entry.getKey()+"/"+entry.getValue());
				}
				collection.bulkWrite(list,new BulkWriteOptions().ordered(false));
			}
			b.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//mongoClient.close();
		Instant countEnd = Instant.now();
		System.out.println("Count start = "+countStart.toString());
		System.out.println("Count end = "+countEnd.toString());
		Duration timeElapsed = Duration.between(countStart, countEnd);
		System.out.println("Time taken "+timeElapsed.toMillis()+ " milliseconds");
		System.out.println("Time taken "+timeElapsed.getSeconds()+ " seconds");
		System.out.println("No of lineCount = "+lineCount);
		System.out.println("Size = "+wordCount.size());
		wordCount=null;
		System.out.println("Bye");
	}
}
