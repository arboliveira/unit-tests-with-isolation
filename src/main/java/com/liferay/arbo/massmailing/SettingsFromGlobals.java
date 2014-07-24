package com.liferay.arbo.massmailing;

import com.liferay.arbo.global.GlobalSystemParameterConfigurationSettings;

public class SettingsFromGlobals implements Settings
{

	@Override
	public long targetCountLocalLimit()
	{
		return GlobalSystemParameterConfigurationSettings
				.getMassMailingTargetCountLocalLimit();
	}

	@Override
	public int lineCountLocalLimit()
	{
		return GlobalSystemParameterConfigurationSettings
				.getMassMailingLineCountLocalLimit();
	}

}
