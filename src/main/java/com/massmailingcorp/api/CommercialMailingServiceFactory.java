package com.massmailingcorp.api;

public class CommercialMailingServiceFactory
{

	public static CommercialMailingService getInstance()
	{
		take5Seconds();

		throw new CommercialAPIException(
				"Could not reach the remote server.");
	}

	private static void take5Seconds()
	{
		System.out.println("5 seconds later.....");

		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

}
