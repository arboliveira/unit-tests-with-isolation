package com.liferay.arbo.massmailing;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.liferay.arbo.email.Address;
import com.liferay.arbo.email.Message;

public class MassMailingServiceTest
{

	@Before
	public void prepareMocks()
	{
		MockitoAnnotations.initMocks(this);

		this.targets = Stream.of(this.address1, this.address2);
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
		when(this.settings.targetCountLocalLimit()).thenReturn(limit);
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

	void stubLineCountLocalLimit(int limit)
	{
		when(this.settings.lineCountLocalLimit()).thenReturn(limit);
	}

	void send()
	{
		new MassMailingService(this.settings, this.local, this.commercial)
				.send(this.message, this.targets);
	}

	void verifyStrategyUsed(Strategy used)
	{
		verify(used).send(this.message, this.targets);

		Strategy unused = used == this.local ? this.commercial : this.local;
		Mockito.verifyZeroInteractions(unused);
	}

	@Mock Settings settings;
	@Mock Strategy local;
	@Mock Strategy commercial;

	@Mock Message message;
	@Mock Address address1;
	@Mock Address address2;

	Stream<Address> targets;

}
