package org.apache.catalina.connector;

public class Parameter {

	private String name;
	private String type;
	private int min;
	private int max;

	public Parameter()
	{
		super();
	}

	public Parameter(String name)
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getMin()
	{
		return min;
	}

	public void setMin(int min)
	{
		this.min = min;
	}

	public int getMax()
	{
		return max;
	}

	public void setMax(int max)
	{
		this.max = max;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		Parameter other = (Parameter) obj;
		if (name.equals(other.name)) return true;
		return false;
	}

	@Override
	public String toString()
	{
		return type + " parameter: " + name + " [" + min + ", " + max + "]";
	}

}
