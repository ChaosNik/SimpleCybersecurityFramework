package org.apache.catalina.connector;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SlowHTTP
{
	private Timer requestsTimer;
	private Timer connectionsTimer;
	private static HashMap<String, Integer> requestsMap = new HashMap<>();
	private static HashMap<String, Integer> connectionsMap = new HashMap<>();

	public static boolean isSlowHTTP(Request request)
	{
		String address = request.getRemoteHost();
		Integer requestsCount = getMapOfRequests().get(address);
		if (requestsCount != null)
		{
			if (requestsCount >= Utilities.maxRequestsNumber)
			{
				return true;
			}
			getMapOfRequests().put(address, requestsCount + 1);
		}
		else
		{
			getMapOfRequests().put(address, 1);
		}

		if (request.getSession() != null)
		{
			if (request.getSession().isNew())
			{
				Integer connectionsCount = getMapOfConnections().get(address);
				if (connectionsCount != null)
				{
					if (connectionsCount >= Utilities.maxConnectionsNumber)
					{
						return true;
					}
					getMapOfConnections().put(address, connectionsCount + 1);
				}
				else
				{
					getMapOfConnections().put(address, 1);
				}
			}
		}
		return false;
	}

	public static synchronized HashMap<String, Integer> getMapOfRequests()
	{
		return requestsMap;
	}

	public static synchronized HashMap<String, Integer> getMapOfConnections()
	{
		return connectionsMap;
	}

	public void runTimers()
	{
		requestsTimer = new Timer();
		requestsTimer.scheduleAtFixedRate
		(
			new TimerTask()
			{
	
				@Override
				public void run()
				{
					getMapOfRequests().clear();
				}
			},
			new Date(),
			Utilities.requestsTime * 1000
		);

		connectionsTimer = new Timer();
		connectionsTimer.scheduleAtFixedRate
		(
			new TimerTask()
			{
	
				@Override
				public void run()
				{
					getMapOfRequests().clear();
				}
			},
			new Date(),
			Utilities.connectionsTime * 1000
		);
	}
}
