package com.liferay.arbo.massmailing;

import java.util.stream.Stream;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.LocalEmailSender;
import com.liferay.arbo.email.Message;
import com.massmailingcorp.api.CommercialMailingServiceFactory;

public class MassMailingService
{

	public void send(Message message, Stream<Address> targets)
	{
		Strategy strategy = chooseStrategy(message, targets);
		strategy.send(message, targets);
	}

	Strategy chooseStrategy(Message message, Stream<Address> targets)
	{
		if (targets.count() <= this.targetCountLocalLimit
				&& message.lineCount() <= this.lineCountLocalLimit)
		{
			return this.local;
		}

		return this.commercial;
	}

	public MassMailingService()
	{
		this(
				new SettingsFromGlobals(),
				new LocalStrategy(new LocalEmailSender()),
				new CommercialStrategy(
						CommercialMailingServiceFactory.getInstance()));
	}

	MassMailingService(Settings settings, Strategy local, Strategy commercial)
	{
		this.local = local;
		this.commercial = commercial;

		this.targetCountLocalLimit = settings.targetCountLocalLimit();
		this.lineCountLocalLimit = settings.lineCountLocalLimit();
	}

	Strategy local;
	Strategy commercial;

	long targetCountLocalLimit;

	int lineCountLocalLimit;

}
