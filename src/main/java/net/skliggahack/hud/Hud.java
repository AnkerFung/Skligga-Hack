package net.skliggahack.hud;

import net.minecraft.client.util.math.MatrixStack;
import net.skliggahack.SkliggaHack;
import net.skliggahack.event.events.RenderHudListener;

public class Hud implements RenderHudListener
{

	private final SkliggaLogo logo = new SkliggaLogo();
	private final SkliggaText text = new SkliggaText();

	public Hud()
	{
		SkliggaHack.INSTANCE.getEventManager().add(RenderHudListener.class, this);
	}

	@Override
	public void onRenderHud(MatrixStack matrices, double partialTicks)
	{
		logo.render(matrices);
		text.render(matrices);
	}
}
