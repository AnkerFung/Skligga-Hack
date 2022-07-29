package net.skliggahack.module.modules.render;

import net.skliggahack.event.events.PlayerTickListener;
import net.skliggahack.module.Category;
import net.skliggahack.module.Module;

import static net.skliggahack.SkliggaHack.MC;

public class GammaOverride extends Module implements PlayerTickListener
{

	private double prevGamma;

	public GammaOverride()
	{
		super("GammaOverride", "override gamma value", false, Category.RENDER);
	}

	@Override
	public void onEnable()
	{
		super.onEnable();
		eventManager.add(PlayerTickListener.class, this);
		prevGamma = MC.options.gamma;
	}

	@Override
	public void onDisable()
	{
		super.onDisable();
		eventManager.remove(PlayerTickListener.class, this);
		MC.options.gamma = prevGamma;
	}

	@Override
	public void onPlayerTick()
	{
		MC.options.gamma = 64.0;
	}
}
