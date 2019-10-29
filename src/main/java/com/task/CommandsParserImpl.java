package com.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandsParserImpl implements CommandsParser
{
	public Map<String,String> parseArgs(String [] args)
	{
		Map<String,String> map;
		Options options = new Options();

        Option source = new Option("source", true, "source filepath");
        source.setRequired(true);
        options.addOption(source);

        Option mongo = new Option("mongo", true, "mongo [hostname]:[port]");
        mongo.setRequired(true);
        options.addOption(mongo);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd=null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("", options);

            System.exit(1);
        }
        map = new HashMap<String, String>();
        map.put("source", cmd.getOptionValue("source"));
        map.put("mongo", cmd.getOptionValue("mongo"));
        return map;
	}
}
