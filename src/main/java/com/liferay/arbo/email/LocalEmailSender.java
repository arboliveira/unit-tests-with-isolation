package com.liferay.arbo.email;

public class LocalEmailSender
{

	public static void send(Message message, Address target)
	{
		take5Seconds();

		System.out.println("You have 1 (one) new message.");
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
