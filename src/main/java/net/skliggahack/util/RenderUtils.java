package net.skliggahack.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public enum RenderUtils
{
	;

	public static void drawQuad(float x1, float y1, float x2, float y2, MatrixStack matrixStack)
	{
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

		bufferBuilder.vertex(matrix, x1, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y1, 0).next();
		bufferBuilder.vertex(matrix, x1, y1, 0).next();

		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
	}

	public static void drawQuad(double x1, double y1, double x2, double y2, MatrixStack matrixStack)
	{
		drawQuad((float) x1, (float) y1, (float) x2, (float) y2, matrixStack);
	}

	public static void drawOutlinedQuad(float x1, float y1, float x2, float y2, MatrixStack matrixStack)
	{
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);

		bufferBuilder.vertex(matrix, x1, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y1, 0).next();
		bufferBuilder.vertex(matrix, x2, y2, 0).next();
		bufferBuilder.vertex(matrix, x1, y1, 0).next();
		bufferBuilder.vertex(matrix, x1, y2, 0).next();
		bufferBuilder.vertex(matrix, x1, y1, 0).next();
		bufferBuilder.vertex(matrix, x2, y1, 0).next();

		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
	}

	public static void drawOutlinedQuad(double x1, double y1, double x2, double y2, MatrixStack matrixStack)
	{
		drawOutlinedQuad((float) x1, (float) y1, (float) x2, (float) y2, matrixStack);
	}

	public static boolean isHoveringOver(double mouseX, double mouseY, double x1, double y1, double x2, double y2)
	{
		return mouseX > Math.min(x1, x2) && mouseX < Math.max(x1, x2) && mouseY > Math.min(y1, y2) && mouseY < Math.max(y1, y2);
	}

}
