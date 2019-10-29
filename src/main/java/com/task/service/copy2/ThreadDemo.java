package com.task.service.copy2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.task.repository.MongoDBConnection;


public class ThreadDemo {
	public static void main (String[] args)
	{
/*		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		t1.setName("First");
		t2.setName("Second");
		t1.start();
		t2.start();
*/		
		System.out.println("Hello World");
		//File file = new File("D:/testfile/enwiki-latest-pages-articles-multistream.xml");
//		File file = new File("D:/Jignesh/xmls/last.xml");
		File file = new File("D:/testfile/new.xml");
		//File file = new File("D:/testfile/test.xml");
		
		Instant countStart = Instant.now();
		int lineCount = 0;
		String readLine = "";
		System.out.println("Reading file using BufferedReader");

		/*
		try//(Stream<String> lines = Files.lines(Paths.get("c:/myfile.txt")))
		{
			Stream<String> lines = Files.lines(Paths.get("D:/testfile/enwiki-latest-pages-articles-multistream.xml"));
			
			lines.forEach(l -> {
				
			
			});
			lines.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/

		WorkerThread workerThread = new WorkerThread();
		//workerThread.setName("I AM 0");
		ArrayList<String> linesVector = new ArrayList<String>();
		MongoDBConnection.getMongoClient().getDatabase("abcde").getCollection("xyz").createIndex(Indexes.ascending("word"),new IndexOptions().unique(true));
		ThreadPoolExecutor executer = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
		try(BufferedReader b = new BufferedReader(new FileReader(file)))
		{
			//while((workerThread.line = b.readLine())!= null)
			while((readLine = b.readLine())!= null)
			{
				//executer.execute(workerThread);
				//workerThread = new WorkerThread();
				//System.out.println(workerThread.getName()+" created");
				linesVector.add(readLine);
				if(lineCount%50000 == 0)
				{
					//workerThread.setLinesVector(linesVector);
					workerThread.linesVector = linesVector;
					executer.execute(workerThread);
					workerThread = new WorkerThread();
//					workerThread.setName("I AM "+lineCount/100);
					//System.out.println(workerThread.getName()+" created");
					 linesVector = new ArrayList<String>();
				}

				//linesVector.add(readLine);

				lineCount++;

//				if(lineCount<100000000)
//				{
					//System.out.println(readLine);
				//	String[] words = readLine.split("\\P{L}+");   // \\p{L}+ \\P{Alpha}+  \\W+  \\P{L}+
					//System.out.println(words.length);
					//for (String string : words)
					//{
					//	System.out.println(string);
					//}
				//String[] words = Stream.of(readLine.split("\\P{L}+")).filter(w->!w.isEmpty()).toArray(String[]::new);
				//Stream.of(Pattern.compile("\\P{L}+")::splitAsStream).filter(w->!w.isEmpty()).toArray(String[]::new);
					//System.out.println("------------------------");
//				}
//				else
//				{
//					break;
//				}
			}
			if(linesVector.size()>0)
			{
				workerThread.linesVector = linesVector;
				executer.execute(workerThread);
			}
			b.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		executer.shutdown();
		while(!executer.isTerminated())
		{

		}
		
		/*
String encoding = "UTF-8";
int maxlines = 100;
BufferedReader reader = null;
BufferedWriter writer = null;

try {
    reader = new BufferedReader(new InputStreamReader(new FileInputStream("/bigfile.txt"), encoding));
    int count = 0;
    for (String line; (line = reader.readLine()) != null;) {
        if (count++ % maxlines == 0) {
            close(writer);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/smallfile" + (count / maxlines) + ".txt"), encoding));
        }
        writer.write(line);
        writer.newLine();
    }
} finally {
    close(writer);
    close(reader);
}
		 */
		//MongoDBConnection.getMongoClient().close();
		Instant countEnd = Instant.now();

		System.out.println("Count start = "+countStart.toString());
		System.out.println("Count end = "+countEnd.toString());
		Duration timeElapsed = Duration.between(countStart, countEnd);
		System.out.println("Time taken "+timeElapsed.toMillis()+ " milliseconds");
		System.out.println("Time taken "+timeElapsed.getSeconds()+ " seconds");
		System.out.println("No of lineCount = "+lineCount);
		System.out.println("Bye");
	}
}
