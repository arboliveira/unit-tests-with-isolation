package com.liferay.arbo.massmailing;

import java.util.Collection;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.EmailSender;
import com.liferay.arbo.email.LocalEmailSender;
import com.liferay.arbo.email.Message;
import com.liferay.arbo.global.GlobalSystemParameterConfigurationSettings;

public class MassMailingService
{

	public void send(Message message, Collection<Address> targets)
	{
		if (targets.size() <= this.targetCountLocalLimit
				&& message.lineCount() <= this.lineCountLocalLimit)
		{
			new LocalStrategy(this.localEmailSender).send(message, targets);
		}
		else
		{
			new CommercialStrategy().send(message, targets);
		}
	}

	public MassMailingService()
	{
		this(new LocalEmailSender());
	}

	MassMailingService(EmailSender localEmailSender)
	{
		this.localEmailSender = localEmailSender;

		this.targetCountLocalLimit =
				GlobalSystemParameterConfigurationSettings
						.getMassMailingTargetCountLocalLimit();

		this.lineCountLocalLimit =
				GlobalSystemParameterConfigurationSettings
						.getMassMailingLineCountLocalLimit();
	}

	long targetCountLocalLimit;

	int lineCountLocalLimit;

	EmailSender localEmailSender;

}
