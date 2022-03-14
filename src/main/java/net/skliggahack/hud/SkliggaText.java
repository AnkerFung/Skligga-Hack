package net.skliggahack.hud;

import net.minecraft.client.util.math.MatrixStack;

import static net.skliggahack.SkliggaHack.MC;

public class SkliggaText
{
	public void render(MatrixStack matrices)
	{
		matrices.push();
		matrices.translate(10, 60, 0);
		MC.textRenderer.drawWithShadow(matrices, "SkliggaHax 4.2.0", 0, 0, 0x00CC00);
		matrices.pop();
	}
}
