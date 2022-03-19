package net.skliggahack.module.modules.render;

import net.skliggahack.SkliggaHack;
import net.skliggahack.event.EventManager;
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
		EventManager eventManager = SkliggaHack.INSTANCE.getEventManager();
		eventManager.add(PlayerTickListener.class, this);
		prevGamma = MC.options.gamma;
	}

	@Override
	public void onDisable()
	{
		EventManager eventManager = SkliggaHack.INSTANCE.getEventManager();
		eventManager.remove(PlayerTickListener.class, this);
		MC.options.gamma = prevGamma;
	}

	@Override
	public void onPlayerTick()
	{
		MC.options.gamma = 64.0;
	}
}
