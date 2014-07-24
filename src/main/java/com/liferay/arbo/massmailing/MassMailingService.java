package com.liferay.arbo.massmailing;

import java.util.Collection;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.LocalEmailSender;
import com.liferay.arbo.email.Message;
import com.liferay.arbo.global.GlobalSystemParameterConfigurationSettings;

public class MassMailingService
{

	public void send(Message message, Collection<Address> targets)
	{
		Strategy strategy = chooseStrategy(message, targets);
		strategy.send(message, targets);
	}

	Strategy chooseStrategy(Message message, Collection<Address> targets)
	{
		if (targets.size() <= this.targetCountLocalLimit
				&& message.lineCount() <= this.lineCountLocalLimit)
		{
			return this.local;
		}

		return this.commercial;
	}

	public MassMailingService()
	{
		this(
				new LocalStrategy(new LocalEmailSender()),
				new CommercialStrategy());
	}

	MassMailingService(Strategy local, Strategy commercial)
	{
		this.local = local;
		this.commercial = commercial;

		this.targetCountLocalLimit =
				GlobalSystemParameterConfigurationSettings
						.getMassMailingTargetCountLocalLimit();

		this.lineCountLocalLimit =
				GlobalSystemParameterConfigurationSettings
						.getMassMailingLineCountLocalLimit();
	}

	Strategy local;
	Strategy commercial;

	long targetCountLocalLimit;

	int lineCountLocalLimit;

}
