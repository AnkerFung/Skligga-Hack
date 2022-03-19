package net.skliggahack.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.skliggahack.SkliggaHack;
import net.skliggahack.gui.component.ModuleButtonComponent;
import net.skliggahack.gui.window.Window;
import net.skliggahack.module.Category;
import net.skliggahack.module.Module;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.TreeMap;

import static net.skliggahack.SkliggaHack.MC;

public class ClickGui
{

	private final ArrayList<Window> windows = new ArrayList<>();
	private Window draggingWindow = null;
	private double globalShiftX = 0;
	private double globalShiftY = 0;

	private static final Identifier chubLogo = new Identifier("chub", "logo.png");

	public void init()
	{
		TreeMap<Category, Window> categorizedWindows = new TreeMap<>();
		TreeMap<Category, Double> heights = new TreeMap<>();
		double x = 25;
		for (Category category : Category.values())
		{
			Window window = new Window(this, x, 25, 125, 400);
			window.setTitle(category.toString());
			categorizedWindows.put(category, window);
			heights.put(category, 20.0);
			windows.add(window);
			x += 150;
		}
		for (Module module : SkliggaHack.INSTANCE.getModuleManager().getModules())
		{
			Category category = module.getCategory();
			Window window = categorizedWindows.get(category);
			double y = heights.get(category);
			window.addComponent(new ModuleButtonComponent(window, module, 10, y));
			heights.put(category, y + 20);
		}
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		//renderLogo(matrices);
		matrices.push();
		matrices.translate(globalShiftX, globalShiftY, 0);
		for (Window window : windows)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderSystem.lineWidth(1);
			window.render(matrices, (int) (mouseX - globalShiftX), (int) (mouseY - globalShiftY), delta);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
		}
		matrices.pop();
	}

	public void handleMouseMoved(double mouseX, double mouseY)
	{
		for (Window window : windows)
		{
			window.onMouseMoved(mouseX - globalShiftX, mouseY - globalShiftY);
		}
	}

	public void handleMouseClicked(double mouseX, double mouseY, int button)
	{
		int clickedWindowIndex = -1;
		for (int i = windows.size() - 1; i >= 0; i--)
		{
			if (windows.get(i).isHoveringOver(mouseX - globalShiftX, mouseY - globalShiftY))
			{
				clickedWindowIndex = i;
				break;
			}
		}

		if (clickedWindowIndex == -1)
			return;

		Window clickedWindow = windows.get(clickedWindowIndex);

		if (clickedWindow == getTopWindow())
		{
			clickedWindow.onMouseClicked(mouseX - globalShiftX, mouseY - globalShiftY, button);
		}

		if (button != GLFW.GLFW_MOUSE_BUTTON_1)
			return;
		if (!windows.contains(clickedWindow))
			return;

		if (clickedWindow.canDrag(mouseX - globalShiftX, mouseY - globalShiftY))
			draggingWindow = clickedWindow;

		windows.remove(clickedWindowIndex);
		windows.add(clickedWindow);
	}

	public void handleMouseReleased(double mouseX, double mouseY, int button)
	{
		if (button == GLFW.GLFW_MOUSE_BUTTON_1)
			draggingWindow = null;
	}

	public void handleMouseScrolled(double mouseX, double mouseY, double amount)
	{
		Window top = getTopWindow();
		if (top == null)
			return;
		top.onMouseScrolled(mouseX - globalShiftX, mouseY - globalShiftY, amount);
	}

	public void handleMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
	{
		for (Window window : windows)
		{
			if (window.onMouseDragged(mouseX - globalShiftX, mouseY - globalShiftY, button, deltaX, deltaY))
				return;
		}
		if (button != GLFW.GLFW_MOUSE_BUTTON_1)
			return;
		if (GLFW.glfwGetKey(MC.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS)
		{
			globalShiftX += deltaX;
			globalShiftY += deltaY;
			return;
		}
		if (draggingWindow != null)
		{
			draggingWindow.setX(draggingWindow.getX() + deltaX);
			draggingWindow.setY(draggingWindow.getY() + deltaY);
		}
	}

	public Window getTopWindow()
	{
		int size = windows.size();
		if (size == 0)
			return null;
		return windows.get(size - 1);
	}

	public void moveToTop(Window window)
	{
		windows.remove(window);
		windows.add(window);
	}

	public void add(Window window)
	{
		windows.add(window);
	}

	public void close(Window window)
	{
		window.onClose();
		windows.remove(window);
	}

	public void renderLogo(MatrixStack matrices)
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, chubLogo);
		DrawableHelper.drawTexture(matrices, 0, 3, 0, 0, 80, 60, 80, 60);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
	}
}