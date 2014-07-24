package com.liferay.arbo.massmailing;

import java.util.stream.Stream;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.Message;

interface Strategy
{

	void send(Message message, Stream<Address> targets);

}
