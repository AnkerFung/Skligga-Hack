package net.skliggahack.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.skliggahack.gui.window.Window;
import net.skliggahack.util.RenderUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CheckboxComponent extends Component
{

	private final static double checkboxSize = 10;

	private boolean value;
	private final Consumer<Boolean> action;
	private final Supplier<Boolean> availability;

	public CheckboxComponent(Window parent, double x, double y, boolean value, Consumer<Boolean> action, Supplier<Boolean> availability, String name)
	{
		super(parent, x, y, 10, name);
		this.value = value;
		this.action = action;
		this.availability = availability;
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
		double x2 = x + checkboxSize;
		double y2 = Math.min(getY() + parentY + checkboxSize, parentY2);
		if (getY() + parentY - parentY <= 0)
			return;
		if (parentY2 - (getY() + parentY) <= 0)
			return;
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0.6f, 0.6f, 0.6f, 1.0f);
		RenderUtils.drawOutlinedQuad(x, y, x2, y2, matrices);
		if (!availability.get() && !value)
		{
			RenderSystem.setShaderColor(0.4f, 0.4f, 0.4f, 1.0f);
			RenderUtils.drawQuad(x, y, x2, y2, matrices);
		}
		if (value)
		{
			x += 1;
			y += 1;
			x2 -= 1;
			y2 -= 1;
			RenderSystem.setShaderColor(0.6f, 0.6f, 0.6f, 1.0f);
			if (availability.get())
				RenderSystem.setShaderColor(0, 0.8f, 0, 1.0f);
			RenderUtils.drawQuad(x, y, x2, y2, matrices);
		}
	}

	@Override
	public void onMouseClicked(double mouseX, double mouseY, int button)
	{
		if (!availability.get())
			return;
		double parentX = parent.getX();
		double parentY = parent.getY();
		double parentWidth = parent.getWidth();
		double parentLength = parent.getLength();
		double parentX2 = parent.getX() + parentWidth;
		double parentY2 = parent.getY() + parentLength;
		double x = getX() + parentX;
		double y = Math.max(getY() + parentY, parentY);
		double x2 = x + checkboxSize;
		double y2 = Math.min(getY() + parentY + checkboxSize, parentY2);
		if (RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x2, y2))
		{
			value = !value;
			action.accept(value);
		}
	}
}
