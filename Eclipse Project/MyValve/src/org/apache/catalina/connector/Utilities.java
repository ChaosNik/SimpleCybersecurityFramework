package org.apache.catalina.connector;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilities
{
	public static ArrayList<String> numberParameters = new ArrayList<>();
	public static int maxRequestsNumber = 100;
	public static int maxConnectionsNumber = 100;
	public static int requestsTime = 10;
	public static int connectionsTime = 10;
	
	public static void LoggSlowHTTP(Request request, Logger logger)
	{
		logger.log
		(
			Level.SEVERE,
			"SLOW HTTP" + "\n\t" + 
			"Address: " + request.getRemoteHost() + "\n\t"
		);
	}
	
	public static void LoggOther(Request request, Logger logger, int type, String name, String value)
	{
		if(type == 1)
			logger.log
			(
				Level.SEVERE,
				"SQL injection" + "\n\t" + 
				"Address: " + request.getRemoteHost() + "\n\t" +
				"Parameter: " + name + " = " + value
			);
		else if(type == 2)
			logger.log
			(
				Level.SEVERE,
				"XSS" + "\n\t" + 
				"Address: " + request.getRemoteHost() + "\n\t" +
				"Parameter: " + name + " = " + value
			);
		else
			logger.log
			(
				Level.SEVERE,
				"Parameter tampering" + "\n\t" + 
				"Address: " + request.getRemoteHost() + "\n\t" +
				"Parameter: " + name + " = " + value
			);
	}
}
