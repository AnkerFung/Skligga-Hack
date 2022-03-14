package net.skliggahack;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		SkliggaHack.INSTANCE.init();
	}
}
