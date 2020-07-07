package org.apache.catalina.connector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

public class MyValve extends ValveBase
{

	public static ArrayList<Parameter> paramterTamperingList = new ArrayList<>();
	private static boolean error = false;
	public static Logger logger;
	{
		try
		{
			logger = Logger.getLogger("AttackAttempts");
			logger.addHandler(new FileHandler("attackAttempts.log"));
			logger.setLevel(Level.ALL);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			System.out.println("Logger error");
		}
	}
	SlowHTTP slow = new SlowHTTP();
	public MyValve()
	{
		loadConfiguration();
		loadParameters();
		slow.runTimers();
	}
	private void loadParameters()
	{
		FileInputStream file = null;
		Properties property = new Properties();
		try
		{
			file = new FileInputStream("parameters.properties");
			property.load(file);

			Iterator<Entry<Object, Object>> iterator = property.entrySet().iterator();
			Parameter parameter = null;
			String[] array = null;
			while (iterator.hasNext())
			{
				Entry<Object, Object> item = iterator.next();
				parameter = new Parameter();
				parameter.setName(item.getKey().toString());
				array = item.getValue().toString().split(":");
				parameter.setType(array[0]);
				parameter.setMin(Integer.parseInt(array[1]));
				parameter.setMax(Integer.parseInt(array[2]));
				paramterTamperingList.add(parameter);
			}
			file.close();
			System.out.println("Loading properties");
		}
		catch (Exception e)
		{
			error = true;
			//e.printStackTrace();
			System.out.println("Properties loading error");
			
		}
	}

	private void loadConfiguration()
	{
		System.out.println("Loading configuration");
		Properties prop = new Properties();
		InputStream input = null;

		try
		{
			input = new FileInputStream("configuration.properties");
			prop.load(input);

			String value = prop.getProperty("NUMBER_PARAMETERS");
			if (value != null) Utilities.numberParameters = new ArrayList<>(Arrays.asList(value.split(",")));
			value = prop.getProperty("CONNECTIONS");
			if (value != null) Utilities.maxConnectionsNumber = Integer.parseInt(value);
			value = prop.getProperty("REQUESTS");
			if (value != null) Utilities.maxRequestsNumber = Integer.parseInt(value);
			value = prop.getProperty("REQUESTS_TIME");
			if (value != null) Utilities.requestsTime = Integer.parseInt(value);
			value = prop.getProperty("CONNECTIONS_TIME");
			if (value != null) Utilities.connectionsTime = Integer.parseInt(value);
			input.close();
		}
		catch (IOException e)
		{
			error = true;
			//e.printStackTrace();
			System.out.println("Configuration loading error");
		}
	}

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException
	{
		String name, value;
		if (!error)
		{
			if ("GET".equals(request.getMethod()) || "POST".equals(request.getMethod()))
			{
				if (SlowHTTP.isSlowHTTP(request)) Utilities.LoggSlowHTTP(request, logger);
				Enumeration<String> names = request.getParameterNames();
				while (names.hasMoreElements())
				{
					name = names.nextElement();
					String[] values = request.getParameterValues(name);
					for (int i = 0; i < values.length; i++)
					{
						value = values[i];
						if (SQLInjection.isSQLInjection(name, value)) Utilities.LoggOther(request, logger, 1, name, value);
						if (XSSAttack.isXSSAttack(value)) Utilities.LoggOther(request, logger, 2, name, value);
						if (ParameterTampering.isParameterTampering(name, value)) Utilities.LoggOther(request, logger, 3, name, value);
					}
				}
			}
			Cookie[] cookie = request.getCookies();
			if (cookie != null)
			{
				for (int i = 0; i < cookie.length; i++)
				{
					name = cookie[i].getName();
					value = cookie[i].getValue();
					if (SQLInjection.isSQLInjection(name, value)) Utilities.LoggOther(request, logger, 1, name, value);
					if (XSSAttack.isXSSAttack(value)) Utilities.LoggOther(request, logger, 2, name, value);
					if (ParameterTampering.isParameterTampering(name, value)) Utilities.LoggOther(request, logger, 3, name, value);
				}
			}
		}
		else logger.log(Level.SEVERE,"InternetSequrityValve doesn't work!!!");
		if(getNext() != null) getNext().invoke(request, response);
	}

}