package com.liferay.arbo.massmailing;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.Message;
import com.massmailingcorp.api.CommercialMailingService;

class CommercialStrategy implements Strategy
{

	@Override
	public void send(Message message, Stream<Address> targets)
	{
		ArrayList<String> targetsList =
				targets
						.map(Address::address)
						.collect(
								ArrayList::new, ArrayList::add,
								ArrayList::addAll);

		this.commercial.send(message.subject(), message.body(), targetsList);
	}

	CommercialStrategy(CommercialMailingService commercial)
	{
		this.commercial = commercial;
	}

	CommercialMailingService commercial;

}
