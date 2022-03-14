package net.skliggahack.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class SkliggaLogo
{
	private static final Identifier logoId = new Identifier("skliggas", "logo.png");

	public void render(MatrixStack matrices)
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		matrices.push();
		matrices.translate(27, 10, 0);
		matrices.scale(0.5f, 0.5f, 1);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, logoId);
		DrawableHelper.drawTexture(matrices, 0, 3, 0, 0, 75, 81, 75 , 81);
		matrices.pop();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
