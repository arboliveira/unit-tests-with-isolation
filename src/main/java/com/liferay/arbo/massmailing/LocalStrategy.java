package com.liferay.arbo.massmailing;

import java.util.stream.Stream;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.EmailSender;
import com.liferay.arbo.email.Message;

class LocalStrategy implements Strategy
{

	@Override
	public void send(Message message, Stream<Address> targets)
	{
		targets.forEach(address ->
		{
			this.localEmailSender.send(message, address);
		});
	}

	LocalStrategy(EmailSender localEmailSender)
	{
		this.localEmailSender = localEmailSender;
	}

	EmailSender localEmailSender;
}
