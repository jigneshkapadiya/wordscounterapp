package com.task;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.task.repository.Constants;
import com.task.repository.MongoDB;
import com.task.repository.MongoDBCollection;
/**
 * Main - start execution of the program here
 * @author Jignesh
 *
 */
public class Main
{
	public static void main(String args[])
	{
		try
		{
		System.out.println("Extracting arguments...");
		CommandsParser cmdParser = new CommandsParserImpl();
		Map<String,String> commandsMap = cmdParser.parseArgs(args);
		
		int lineCount = 0;
		String readLine;
		System.out.println("Reading file...");
		ThreadPoolExecutor executer = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
		Instant countStart = Instant.now();

		try(BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(commandsMap.get("source")),"UTF-8")))
		{
			WorkerThread workerThread = new WorkerThread();
			ArrayList<String> linesList = new ArrayList<String>();
			MongoDB.setConnection(commandsMap.get("mongo"));
			// Creates Mongodb database and collection if does not exist
			MongoDB.getMongoClient().getDatabase(Constants.MongoDBDatabase).getCollection(Constants.MongoDBCollection);
			MongoDBCollection.createUniqueIndex();
			while((readLine = br.readLine())!= null)
			{
				linesList.add(readLine);
				lineCount++;
				if(lineCount%50000 == 0)
				{
					workerThread.linesList = linesList;
					executer.execute(workerThread);
					workerThread = new WorkerThread();
					linesList = new ArrayList<String>();
				}
			}
			if(linesList.size()>0)
			{
				workerThread.linesList = linesList;
				executer.execute(workerThread);
			}
			br.close();
		}
		catch (FileNotFoundException e1)
		{
			System.out.println("File "+commandsMap.get("source")+" not found ");
			return;
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		executer.shutdown();
		while(!executer.isTerminated())
		{

		}
		Instant countEnd = Instant.now();

		System.out.println("Count start = "+countStart.toString());
		System.out.println("Count end = "+countEnd.toString());
		Duration timeElapsed = Duration.between(countStart, countEnd);
		System.out.println("Time taken "+timeElapsed.toMillis()+ " milliseconds");
		System.out.println("Time taken "+timeElapsed.getSeconds()+ " seconds");
		System.out.println("No of lineCount = "+lineCount);
		System.out.println("Bye");
		}
		finally {
			MongoDB.getMongoClient().close();
		}
	}
}
