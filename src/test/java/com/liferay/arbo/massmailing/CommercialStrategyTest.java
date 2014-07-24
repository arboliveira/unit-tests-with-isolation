package com.liferay.arbo.massmailing;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.Message;
import com.massmailingcorp.api.CommercialMailingService;
import com.massmailingcorp.api.CommercialMailingServiceFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CommercialMailingServiceFactory.class })
public class CommercialStrategyTest
{

	@Before
	public void prepareMocks()
	{
		MockitoAnnotations.initMocks(this);

		mockStatic(
				CommercialMailingServiceFactory.class,
				CALLS_REAL_METHODS);
		stub(method(
				CommercialMailingServiceFactory.class,
				"getInstance"
				))
				.toReturn(this.commercial);
	}

	@Test
	public void send()
	{
		when(this.message.subject()).thenReturn("SUBJECT");
		when(this.message.body()).thenReturn("BODY");
		when(this.address1.address()).thenReturn("ADDRESS1");
		when(this.address2.address()).thenReturn("ADDRESS2");

		new CommercialStrategy().send(
				this.message, Arrays.asList(this.address1, this.address2));

		verify(this.commercial).send(
				"SUBJECT", "BODY", Arrays.asList("ADDRESS1", "ADDRESS2"));
	}

	@Mock Message message;
	@Mock Address address1;
	@Mock Address address2;

	@Mock CommercialMailingService commercial;
}
