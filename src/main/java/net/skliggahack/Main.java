package net.skliggahack;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		try
		{
			SkliggaHack.INSTANCE.init();
		}
		catch (Exception ignored)
		{

		}
	}
}
