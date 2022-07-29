package net.skliggahack.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.skliggahack.gui.window.Window;
import net.skliggahack.util.RenderUtils;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

import static net.skliggahack.SkliggaHack.MC;

public class ButtonComponent extends Component
{

	private final Runnable action;
	private final Supplier<String> display;

	public ButtonComponent(Window parent, double x, double y, double length, String name, Runnable action, Supplier<String> display)
	{
		super(parent, x, y, length, name);
		this.action = action;
		this.display = display;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.render(matrices, mouseX, mouseY, delta);
		double parentX = parent.getX();
		double parentY = parent.getY();
		double parentWidth = parent.getWidth();
		double parentLength = parent.getLength();
		double parentX2 = parent.getX() + parentWidth;
		double parentY2 = parent.getY() + parentLength;
		double x = getX() + parentX;
		double y = Math.max(getY() + parentY, parentY);
		double x2 = Math.min(x + getLength(), parentX2);
		double y2 = Math.min(y + 10, parentY2);
		if (getY() + 10 <= 0)
			return;
		if (parentY2 - (getY() + parentY) <= 0)
			return;
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0.4f, 0.4f, 0.4f, 0.4f);
		if (RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x2, y2))
			RenderSystem.setShaderColor(0.6f, 0.6f, 0.6f, 1.0f);
		RenderUtils.drawQuad(x, y, x2, y2, matrices);
		MC.textRenderer.draw(matrices, display.get(), (float)x, (float)y, 0xffffff);
	}

	@Override
	public void onMouseClicked(double mouseX, double mouseY, int button)
	{
		double parentX = parent.getX();
		double parentY = parent.getY();
		double parentWidth = parent.getWidth();
		double parentLength = parent.getLength();
		double parentX2 = parent.getX() + parentWidth;
		double parentY2 = parent.getY() + parentLength;
		double x = getX() + parentX;
		double y = Math.max(getY() + parentY, parentY);
		double x2 = Math.min(x + getLength(), parentX2);
		double y2 = Math.min(y + 10, parentY2);
		if (getY() + 10 <= 0)
			return;
		if (parentY2 - (getY() + parentY) <= 0)
			return;
		if (RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x2, y2))
		{
			if (button == GLFW.GLFW_MOUSE_BUTTON_1)
			{
				action.run();
			}
		}
	}
}
