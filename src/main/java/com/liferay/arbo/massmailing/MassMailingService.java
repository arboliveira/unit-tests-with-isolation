package com.liferay.arbo.massmailing;

import java.util.ArrayList;
import java.util.Collection;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.LocalEmailSender;
import com.liferay.arbo.email.Message;
import com.liferay.arbo.global.GlobalSystemParameterConfigurationSettings;
import com.massmailingcorp.api.CommercialMailingService;
import com.massmailingcorp.api.CommercialMailingServiceFactory;

public class MassMailingService
{

	public void send(Message message, Collection<Address> targets)
	{
		if (targets.size() <= this.targetCountLocalLimit)
		// && message.lineCount() <= this.lineCountLocalLimit
		{
			for (Address address : targets)
			{
				LocalEmailSender.send(message, address);
			}
		}
		else
		{
			ArrayList<String> targetsList = new ArrayList<>(targets.size());
			for (Address address : targets)
			{
				targetsList.add(address.address());
			}

			CommercialMailingService commercial =
					CommercialMailingServiceFactory.getInstance();

			commercial.send(
					message.subject(), message.body(), targetsList);
		}
	}

	public MassMailingService()
	{
		this.targetCountLocalLimit =
				GlobalSystemParameterConfigurationSettings
						.getMassMailingTargetCountLocalLimit();
	}

	long targetCountLocalLimit;

}
