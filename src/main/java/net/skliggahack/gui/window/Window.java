package net.skliggahack.gui.window;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.skliggahack.gui.ClickGui;
import net.skliggahack.gui.component.Component;
import net.skliggahack.util.RenderUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static net.skliggahack.SkliggaHack.MC;

public class Window
{
	public final ClickGui parent;
	private double x, y;
	private double width, length;
	private double scrollAmount = 0;
	protected boolean minimized = false;
	protected ArrayList<Component> components = new ArrayList<>();
	private boolean isDraggable = true;
	private boolean draggable = true;
	private boolean closable = true;
	private boolean minimizable = true;
	private boolean resizable = true;
	private boolean pinnable = true;

	public Window(ClickGui parent, double x, double y, double width, int length)
	{
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = width;
		this.length = length;
	}

	public void addComponent(Component component)
	{
		components.add(component);
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		if (!minimized)
		{
			RenderSystem.setShader(GameRenderer::getPositionShader);
			RenderSystem.setShaderColor(0.4f, 0.4f, 0.4f, 0.4f);
			if (parent.getTopWindow() == this)
				RenderSystem.setShaderColor(0.4f, 0.4f, 0.4f, 0.6f);
			RenderUtils.drawQuad(x, y, x + width, y + length, matrices);
			for (Component component : components)
			{
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				RenderSystem.lineWidth(1);
				component.render(matrices, mouseX, (int) (mouseY), delta);
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}
		if (draggable)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			RenderSystem.setShader(GameRenderer::getPositionShader);
			RenderSystem.setShaderColor(0.2f, 0.2f, 0.2f, 1.0f);
			if (parent.getTopWindow() == this)
				RenderSystem.setShaderColor(0.0f, 0.8f, 0.0f, 1.0f);
			RenderUtils.drawQuad(x, y, x + width, y + 10, matrices);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
		}
		if (closable)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			RenderSystem.setShader(GameRenderer::getPositionShader);
			RenderSystem.setShaderColor(1.0f, 0.2f, 0.2f, 1.0f);
			double x = getX() + width - 10;
			double y = getY();
			RenderUtils.drawQuad(x, y, x + 10, y + 10, matrices);
			TextRenderer textRenderer = MC.textRenderer;
			textRenderer.draw(matrices, "x", (float) (x + 2), (float) y, 0xFFFFFFFF);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
		}
		if (minimizable)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			RenderSystem.setShader(GameRenderer::getPositionShader);
			RenderSystem.setShaderColor(1.0f, 0.2f, 0.2f, 1.0f);
			double x = getX() + width - 25;
			double y = getY();
			RenderUtils.drawQuad(x, y, x + 10, y + 10, matrices);
			TextRenderer textRenderer = MC.textRenderer;
			textRenderer.draw(matrices, minimized ? "+" : "-", (float) (x + 2), (float) y, 0xFFFFFFFF);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	public void onMouseMoved(double mouseX, double mouseY)
	{
		for (Component component : components)
		{
			component.onMouseMoved(mouseX, mouseY);
		}
	}

	public void onMouseClicked(double mouseX, double mouseY, int button)
	{
		if (button == GLFW.GLFW_MOUSE_BUTTON_1)
		{
			if (closable)
			{
				double x = getX() + width - 10;
				double y = getY();
				if (RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x + 10, y + 10))
					parent.close(this);
			}
			if (minimizable)
			{
				double x = getX() + width - 25;
				double y = getY();
				if (RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x + 10, y + 10))
					minimized = !minimized;
			}
		}
		if (!minimized && !canDrag(mouseX, mouseY))
		{
			for (Component component : components)
			{
				component.onMouseClicked(mouseX, mouseY, button);
			}
		}
	}

	public void onMouseScrolled(double mouseX, double mouseY, double amount)
	{
		if (minimized)
			return;
		if (!RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x + width, y + length))
			return;
		for (Component component: components)
		{
			if (component.onMouseScrolled(mouseX, mouseY, amount))
				return;
		}

		scrollAmount += amount * 2;
		if (scrollAmount > 0)
			scrollAmount = 0;
		else
		{
			for (Component component : components)
			{
				component.setY(component.getY() + amount * 2);
			}
		}
	}

	public boolean onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
	{
		for (Component component : components)
		{
			if (component.onMouseDragged(mouseX, mouseY, button, deltaX, deltaY))
				return true;
		}
		return false;
	}

	public void onClose()
	{

	}

	public boolean canDrag(double mouseX, double mouseY)
	{
		if (!draggable)
			return false;
		return RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x + width, y + 10);
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getLength()
	{
		return length;
	}

	public void setLength(double length)
	{
		this.length = length;
	}

	public boolean isDraggable()
	{
		return isDraggable;
	}

	public void setIsDraggable(boolean isDraggable)
	{
		this.isDraggable = isDraggable;
	}

	public boolean isHoveringOver(double mouseX, double mouseY)
	{
		return RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x + width, y + length);
	}
}
