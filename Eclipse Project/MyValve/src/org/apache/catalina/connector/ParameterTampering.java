package org.apache.catalina.connector;

public class ParameterTampering {
	public static boolean isParameterTampering(String name, String value)
	{
		int index = MyValve.paramterTamperingList.indexOf(new Parameter(name));
		if (index >= 0)
		{
			Parameter parameter = MyValve.paramterTamperingList.get(index);
			if (value != null && !"".equals(value.trim()))
			{
				if ("text".equals(parameter.getType()) && (value.length() < parameter.getMin() || value.length() > parameter.getMax())) return true;
				else if ("number".equals(parameter.getType()))
				{
					double parameterDouble;
					try
					{
						parameterDouble = Double.parseDouble(value);
					}
					catch (Exception e)
					{
						return true;
					}
					if (parameterDouble < parameter.getMin() || parameterDouble > parameter.getMax()) return true;
				}
			}
		}
		return false;
	}
}
