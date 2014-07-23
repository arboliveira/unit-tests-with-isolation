package com.liferay.arbo.global;

public class GlobalSystemParameterConfigurationSettings
{

	public static long getMassMailingTargetCountLocalLimit()
	{
		throw new ConnectionException(
				"You must be connected to the database"
						+ " to perform this operation!!");
	}

}
