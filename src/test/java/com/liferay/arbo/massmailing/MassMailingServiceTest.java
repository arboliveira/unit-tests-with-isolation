package com.liferay.arbo.massmailing;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.liferay.arbo.global.GlobalSystemParameterConfigurationSettings;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GlobalSystemParameterConfigurationSettings.class })
public class MassMailingServiceTest
{

	@Test
	public void smoke()
	{
		mockStatic(GlobalSystemParameterConfigurationSettings.class);

		new MassMailingService();
	}

}
