package com.liferay.arbo.global;

public class GlobalSystemParameterConfigurationSettings
{

	public static long getMassMailingTargetCountLocalLimit()
	{
		throw requireDatabaseConnection();
	}

	public static int getMassMailingLineCountLocalLimit()
	{
		throw requireDatabaseConnection();
	}

	private static ConnectionException requireDatabaseConnection()
	{
		return new ConnectionException(
				"You must be connected to the database"
						+ " to perform this operation!!");
	}

}
