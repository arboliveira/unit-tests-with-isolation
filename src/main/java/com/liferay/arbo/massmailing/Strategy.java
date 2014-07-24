package com.liferay.arbo.massmailing;

import java.util.Collection;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.Message;

interface Strategy
{

	void send(Message message, Collection<Address> targets);

}
