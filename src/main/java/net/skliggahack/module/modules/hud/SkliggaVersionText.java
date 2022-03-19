package net.skliggahack.module.modules.hud;

import net.minecraft.client.util.math.MatrixStack;
import net.skliggahack.SkliggaHack;
import net.skliggahack.event.EventManager;
import net.skliggahack.event.events.RenderHudListener;
import net.skliggahack.module.Category;
import net.skliggahack.module.Module;

import static net.skliggahack.SkliggaHack.MC;

public class SkliggaVersionText extends Module implements RenderHudListener
{

	public SkliggaVersionText()
	{
		super("SkliggaVersionText", "SkliggaHax 4.2.0", true, Category.HUD);
	}

	@Override
	public void onEnable()
	{
		EventManager eventManager = SkliggaHack.INSTANCE.getEventManager();
		eventManager.add(RenderHudListener.class, this);
	}

	@Override
	public void onDisable()
	{
		EventManager eventManager = SkliggaHack.INSTANCE.getEventManager();
		eventManager.remove(RenderHudListener.class, this);
	}

	@Override
	public void onRenderHud(MatrixStack matrices, double partialTicks)
	{
		matrices.push();
		matrices.translate(10, 60, 0);
		MC.textRenderer.drawWithShadow(matrices, "SkliggaHax 4.2.0", 0, 0, 0x00CC00);
		matrices.pop();
	}
}
