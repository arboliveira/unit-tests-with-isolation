package com.liferay.arbo.massmailing;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.LocalEmailSender;
import com.liferay.arbo.email.Message;
import com.liferay.arbo.global.GlobalSystemParameterConfigurationSettings;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
		GlobalSystemParameterConfigurationSettings.class,
		LocalEmailSender.class
})
public class MassMailingServiceTest
{

	@Test
	public void local()
	{
		mockStatic(
				GlobalSystemParameterConfigurationSettings.class,
				CALLS_REAL_METHODS);
		stub(method(
				GlobalSystemParameterConfigurationSettings.class,
				"getMassMailingTargetCountLocalLimit"
				))
				.toReturn(3);

		mockStatic(LocalEmailSender.class);

		Message message = mock(Message.class);
		Address address1 = mock(Address.class), address2 = mock(Address.class);

		List<Address> targets = Arrays.asList(address1, address2);

		new MassMailingService().send(message, targets);

		verifyStatic();
		LocalEmailSender.send(message, address1);
		verifyStatic();
		LocalEmailSender.send(message, address2);
	}

}
