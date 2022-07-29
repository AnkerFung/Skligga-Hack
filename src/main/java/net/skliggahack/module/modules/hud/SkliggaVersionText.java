package net.skliggahack.module.modules.hud;

import net.minecraft.client.util.math.MatrixStack;
import net.skliggahack.event.events.RenderHudListener;
import net.skliggahack.module.Category;
import net.skliggahack.module.Module;

import static net.skliggahack.SkliggaHack.MC;

public class SkliggaVersionText extends Module implements RenderHudListener
{

	public SkliggaVersionText()
	{
		super("SkliggaVersionText", "SkliggaHax 4.2.0", false, Category.HUD);
	}

	@Override
	public void onEnable()
	{
		super.onEnable();
		eventManager.add(RenderHudListener.class, this);
	}

	@Override
	public void onDisable()
	{
		super.onDisable();
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
