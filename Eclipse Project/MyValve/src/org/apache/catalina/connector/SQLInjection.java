package org.apache.catalina.connector;

public class SQLInjection
{
	public static boolean isSQLInjection(String name, String value)
	{
		String input = value.toLowerCase().replace(" ", "");
		if
		(
			input.contains("mysql") ||
			input.contains("sys") ||
			input.contains("sleep(")||
			input.contains("extractvalue(")||
			input.contains("rand(")||
			input.contains("select")||
			input.contains("insert") ||
			input.contains("update") ||
			input.contains("shutdown") ||
			input.contains("delete") ||
			input.contains("drop") ||
			input.contains("--") ||
			input.contains("#") ||
			input.contains(" or ") ||
			input.contains("/*") ||
			input.contains("*/") ||
			input.contains("1=1") ||
			input.contains("true")
		)
			return true;

		if (Utilities.numberParameters.contains(name.trim()))
		{
			if (value != null && value.trim().length() > 0)
			{
				try
				{
					Double.parseDouble(value);
				}
				catch (Exception e)
				{
					return true;
				}
			}
		}
		return false;
	}
}
