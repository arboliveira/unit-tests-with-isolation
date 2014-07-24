package com.liferay.arbo.massmailing;

import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.EmailSender;
import com.liferay.arbo.email.Message;

public class LocalStrategyTest
{

	@Before
	public void prepareMocks()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void send()
	{
		new LocalStrategy(this.emailSender).send(
				this.message, Arrays.asList(this.address1, this.address2));

		verify(this.emailSender).send(this.message, this.address1);
		verify(this.emailSender).send(this.message, this.address2);
	}

	@Mock Message message;
	@Mock Address address1;
	@Mock Address address2;

	@Mock EmailSender emailSender;

}
