package com.liferay.arbo.massmailing;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.Message;
import com.liferay.arbo.global.GlobalSystemParameterConfigurationSettings;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GlobalSystemParameterConfigurationSettings.class })
public class MassMailingServiceTest
{

	@Before
	public void prepareMocks()
	{
		MockitoAnnotations.initMocks(this);

		this.targets = Arrays.asList(this.address1, this.address2);

		mockStatic(
				GlobalSystemParameterConfigurationSettings.class,
				CALLS_REAL_METHODS);
	}

	@Test
	public void local()
	{
		stubTargetCount_acceptable();
		stubLineCount_acceptable();

		send();

		verifyStrategyUsed(this.local);
	}

	@Test
	public void tooManyTargets_commercial()
	{
		stubTargetCount_tooMany();
		stubLineCount_acceptable();

		send();

		verifyStrategyUsed(this.commercial);
	}

	@Test
	public void tooManyLines_commercial()
	{
		stubTargetCount_acceptable();
		stubLineCount_tooMany();

		send();

		verifyStrategyUsed(this.commercial);
	}

	void stubTargetCount_acceptable()
	{
		stubTargetCountLocalLimit(3);
	}

	void stubTargetCount_tooMany()
	{
		stubTargetCountLocalLimit(1);
	}

	void stubTargetCountLocalLimit(long limit)
	{
		stub(method(
				GlobalSystemParameterConfigurationSettings.class,
				"getMassMailingTargetCountLocalLimit"
				))
				.toReturn(limit);
	}

	void stubLineCount_acceptable()
	{
		stubLineCountLocalLimit(10);
		when(this.message.lineCount()).thenReturn(9);
	}

	void stubLineCount_tooMany()
	{
		stubLineCountLocalLimit(10);
		when(this.message.lineCount()).thenReturn(11);
	}

	void stubLineCountLocalLimit(long limit)
	{
		stub(method(
				GlobalSystemParameterConfigurationSettings.class,
				"getMassMailingLineCountLocalLimit"
				))
				.toReturn(limit);
	}

	void send()
	{
		new MassMailingService(this.local, this.commercial)
				.send(this.message, this.targets);
	}

	void verifyStrategyUsed(Strategy used)
	{
		verify(used).send(this.message, this.targets);

		Strategy unused = used == this.local ? this.commercial : this.local;
		Mockito.verifyZeroInteractions(unused);
	}

	@Mock Strategy local;
	@Mock Strategy commercial;

	@Mock Message message;
	@Mock Address address1;
	@Mock Address address2;

	List<Address> targets;
}
