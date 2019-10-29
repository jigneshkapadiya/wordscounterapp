package com.task.service.copy2;

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
import com.task.repository.MongoDBConnection;

public class WorkerThread extends Thread
{
	String line;
	
	ArrayList<String> linesVector=new ArrayList<String>();
	
	
//	public Vector<String> getLinesVector() {
//		return linesVector;
//	}
//
//
//	public void setLinesVector(Vector<String> linesVector) {
//		this.linesVector = linesVector;
//	}


	public void run()
	{
		/*String[] words;

//		Document update = new Document("$inc", new Document("Count",1));
//		modifiedObject.put("$inc", new BasicDBObject().append("hits", 1));
		//System.out.println(Thread.currentThread().getName()+" in running");
//		MongoDatabase database = MongoDBConnection.getMongoClient().getDatabase("floowtask");
//		MongoCollection<Document> collection = database.getCollection("wordscount");
		//UpdateOptions updateOptions = new UpdateOptions().upsert(true);
		//for(String readLine:linesVector)
		//{
//		Document update = new Document("$inc", new Document("Count",1));
//		UpdateOptions updateOptions = new UpdateOptions().upsert(true);
			//System.out.println(Thread.currentThread().getName()+" "+line);
			words =  line.split("\\P{L}+");
			
			for (String string : words)
			{
				//if(!string.isEmpty())
				//{
					//Document update = new Document("$inc", new Document("count",1));
					//UpdateOptions updateOptions = new UpdateOptions().upsert(true);
					//Document filter = new Document("word",string);
					//MongoDBConnection.getMongoClient().getDatabase("wordscount").getCollection("words").updateOne(filter, update, updateOptions);
					//callme(string);
					
					
				//}
				//callme(collection,filter,update,updateOptions);
				//callme(string, update, updateOptions);
				//callme(string);
			}
			*/

			Map<String, Long> map = Stream.of(linesVector) // Stream<Stream<String>>
            .flatMap(Collection::stream) // Stream<String> 
            .flatMap(Pattern.compile("\\P{L}+")::splitAsStream).filter(w->!w.isEmpty())
            .collect(groupingBy(name -> name, counting()));

			System.out.println("map size="+map.size());

			List<WriteModel<Document>> list = new ArrayList<WriteModel<Document>>();
			for(Map.Entry<String, Long> entry: map.entrySet())
			{
				list.add(new UpdateOneModel<>(new Document("word",entry.getKey())
											, new Document("$inc", new Document("count",entry.getValue()))
											, new UpdateOptions().upsert(true)
											));
				//System.out.println(entry.getKey()+"/"+entry.getValue());
			}

			System.out.println("list size="+list.size());
			
			MongoDBConnection.getMongoClient().getDatabase("abcde").getCollection("xyz").bulkWrite(list,new BulkWriteOptions().ordered(false));
			
			

			//Arrays.asList()
			//System.out.println("size = "+map.size());
//			MongoDBConnection.getMongoClient().getDatabase("wordscount").getCollection("words").updateOne(filter, map, updateOptions);
			
		//}
		//System.out.println(Thread.currentThread().getName()+" in done");
	}
	/*public synchronized void callme (String string)
	{
		//synchronized (collection) {
		Document update = new Document("$inc", new Document("count",1));
		UpdateOptions updateOptions = new UpdateOptions().upsert(true);
			Document filter = new Document("word",string);
			MongoDBConnection.getMongoCollection().updateOne(filter, update, updateOptions);
//			update =null;
//			updateOptions = null;
//			filter = null;
			
			//collection.updateOne(filter, update, updateOptions);
		//}
		
	}*/
}
