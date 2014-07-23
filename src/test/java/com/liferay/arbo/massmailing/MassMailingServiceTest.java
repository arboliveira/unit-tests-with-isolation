package com.liferay.arbo.massmailing;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
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
import com.liferay.arbo.email.LocalEmailSender;
import com.liferay.arbo.email.Message;
import com.liferay.arbo.global.GlobalSystemParameterConfigurationSettings;
import com.massmailingcorp.api.CommercialMailingService;
import com.massmailingcorp.api.CommercialMailingServiceFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
		GlobalSystemParameterConfigurationSettings.class,
		LocalEmailSender.class,
		CommercialMailingServiceFactory.class
})
public class MassMailingServiceTest
{

	@Before
	public void prepareMocks()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void local()
	{
		stubTargetCountLocalLimit(3);

		mockStatic(LocalEmailSender.class);

		send();

		verifyStatic();
		LocalEmailSender.send(this.message, this.address1);
		verifyStatic();
		LocalEmailSender.send(this.message, this.address2);
	}

	@Test
	public void commercial()
	{
		stubTargetCountLocalLimit(1);

		CommercialMailingService commercial =
				mock(CommercialMailingService.class);

		mockStatic(
				CommercialMailingServiceFactory.class,
				CALLS_REAL_METHODS);
		stub(method(
				CommercialMailingServiceFactory.class,
				"getInstance"
				))
				.toReturn(commercial);

		when(this.message.subject()).thenReturn("SUBJECT");
		when(this.message.body()).thenReturn("BODY");
		when(this.address1.address()).thenReturn("ADDRESS1");
		when(this.address2.address()).thenReturn("ADDRESS2");

		send();

		verify(commercial).send(
				"SUBJECT", "BODY", Arrays.asList("ADDRESS1", "ADDRESS2"));
	}

	void stubTargetCountLocalLimit(long limit)
	{
		mockStatic(
				GlobalSystemParameterConfigurationSettings.class,
				CALLS_REAL_METHODS);

		stub(method(
				GlobalSystemParameterConfigurationSettings.class,
				"getMassMailingTargetCountLocalLimit"
				))
				.toReturn(limit);
	}

	void send()
	{
		new MassMailingService().send(
				this.message, Arrays.asList(this.address1, this.address2));
	}

	@Mock Message message;
	@Mock Address address1;
	@Mock Address address2;

}
