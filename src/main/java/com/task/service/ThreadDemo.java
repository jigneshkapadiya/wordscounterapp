package com.task.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Instant;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

import com.task.WorkerThread;

class MyThread extends Thread
{
	public void run()
	{
		for(int i=0;i<20;i++)
		{
			System.out.println(Thread.currentThread().getName()+" = "+i);
		}
	}
}
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
		File file = new File("D:/testfile/myfile.xml");
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
				if(lineCount<100000000)
				{
					
				}
				else
				{
					return;
				}
			
		});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
		WorkerThread workerThread = new WorkerThread();
		workerThread.setName("I AM 0");
		//Vector<String> linesVector = new Vector<>();
		ThreadPoolExecutor executer = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
		try(BufferedReader b = new BufferedReader(new FileReader(file)))
		{
//			while((workerThread. = b.readLine())!= null)
			{
				executer.execute(workerThread);
				workerThread = new WorkerThread();
				System.out.println(workerThread.getName()+" created");
				
				/*if(lineCount%100 == 0)
				{
					workerThread.setLinesVector(linesVector);
					executer.execute(workerThread);
					workerThread = new WorkerThread();
//					workerThread.setName("I AM "+lineCount/100);
					System.out.println(workerThread.getName()+" created");
					 linesVector = new Vector<>();
				}
				*/
				//linesVector.add(readLine);
				
				lineCount++;

				/*if(lineCount<100000000)
				{
					//System.out.println(readLine);
					//String[] words = readLine.split("\\P{L}+");   // \\p{L}+ \\P{Alpha}+  \\W+  \\P{L}+
					//System.out.println(words.length);
					//for (String string : words)
					//{
					//	System.out.println(string);
					//}
					
					//System.out.println("------------------------");
				}
				else
				{
					break;
				}*/
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

		Instant countEnd = Instant.now();
		System.out.println("Count start = "+countStart.toString());
		System.out.println("Count end = "+countEnd.toString());
		System.out.println("No of lineCount = "+lineCount);
		System.out.println("Bye");
	}
}
