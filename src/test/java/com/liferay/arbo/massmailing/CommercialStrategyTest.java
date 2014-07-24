package com.liferay.arbo.massmailing;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.Message;
import com.massmailingcorp.api.CommercialMailingService;

public class CommercialStrategyTest
{

	@Before
	public void prepareMocks()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void send()
	{
		when(this.message.subject()).thenReturn("SUBJECT");
		when(this.message.body()).thenReturn("BODY");
		when(this.address1.address()).thenReturn("ADDRESS1");
		when(this.address2.address()).thenReturn("ADDRESS2");

		new CommercialStrategy(this.commercial).send(
				this.message, Stream.of(this.address1, this.address2));

		verify(this.commercial).send(
				"SUBJECT", "BODY", Arrays.asList("ADDRESS1", "ADDRESS2"));
	}

	@Mock Message message;
	@Mock Address address1;
	@Mock Address address2;

	@Mock CommercialMailingService commercial;

}
