package com.task;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import com.task.repository.Constants;
import com.task.repository.MongoDB;
import com.task.repository.MongoDBConnection;

public class WorkerThread extends Thread
{
	ArrayList<String> linesList;
	
	public void run()
	{
		Map<String, Long> map = Stream.of(linesList) // Stream<Stream<String>>
	            .flatMap(Collection::stream) // Stream<String> 
	            .flatMap(Pattern.compile("\\P{L}+")::splitAsStream).filter(w->!w.isEmpty())
	            .collect(groupingBy(name -> name, counting()));

		//System.out.println("map size="+map.size());
		List<WriteModel<Document>> list = new ArrayList<WriteModel<Document>>();
		for(Map.Entry<String, Long> entry: map.entrySet())
		{
			list.add(new UpdateOneModel<>(new Document(Constants.WordField,entry.getKey())
									, new Document("$inc", new Document(Constants.CountField,entry.getValue()))
									, new UpdateOptions().upsert(true)
									));
		}
		//System.out.println("list size="+list.size());
		MongoDB.getMongoClient().getDatabase(Constants.MongoDBDatabase).getCollection(Constants.MongoDBCollection).bulkWrite(list,new BulkWriteOptions().ordered(false));
	}
}
