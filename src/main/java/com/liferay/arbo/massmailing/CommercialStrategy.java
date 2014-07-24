package com.liferay.arbo.massmailing;

import java.util.ArrayList;
import java.util.Collection;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.Message;
import com.massmailingcorp.api.CommercialMailingService;
import com.massmailingcorp.api.CommercialMailingServiceFactory;

class CommercialStrategy implements Strategy
{

	@Override
	public void send(Message message, Collection<Address> targets)
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
