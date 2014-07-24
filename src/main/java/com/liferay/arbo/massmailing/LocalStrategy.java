package com.liferay.arbo.massmailing;

import java.util.Collection;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.EmailSender;
import com.liferay.arbo.email.Message;

class LocalStrategy implements Strategy
{

	@Override
	public void send(Message message, Collection<Address> targets)
	{
		for (Address address : targets)
		{
			this.localEmailSender.send(message, address);
		}
	}

	LocalStrategy(EmailSender localEmailSender)
	{
		this.localEmailSender = localEmailSender;
	}

	EmailSender localEmailSender;
}
