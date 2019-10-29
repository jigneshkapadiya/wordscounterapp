package com.task;

import java.util.Map;

/**
 * A class that implements <code>CommandsParser</code> interface 
 * can parse a String array 
 * @author Jignesh
 * 
 */
public interface CommandsParser
{
	public Map<String,String> parseArgs(String[] args);
}
