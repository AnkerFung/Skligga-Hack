package net.skliggahack.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.skliggahack.gui.ClickGui;
import net.skliggahack.gui.window.ModuleSettingWindow;
import net.skliggahack.gui.window.Window;
import net.skliggahack.mixinterface.ITextRenderer;
import net.skliggahack.module.Module;
import net.skliggahack.util.RenderUtils;
import org.lwjgl.glfw.GLFW;

import static net.skliggahack.SkliggaHack.MC;

public class ModuleButtonComponent extends Component
{

	private final Module module;
	private boolean settingWindowOpened = false;
	private ModuleSettingWindow moduleSettingWindow;

	public ModuleButtonComponent(Window parent, Module module, double x, double y)
	{
		super(parent, x, y, 10, module.getName());
		this.module = module;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		double parentX = parent.getX();
		double parentY = parent.getY();
		double parentWidth = parent.getWidth();
		double parentLength = parent.getLength();
		double parentX2 = parent.getX() + parentWidth;
		double parentY2 = parent.getY() + parentLength;
		double x = getX() + parentX;
		double y = Math.max(getY() + parentY, parentY);
		double x2 = parentX2 - getX();
		double y2 = Math.min(getY() + parentY + 10, parentY2);
		if (getY() + parentY - parentY <= 0)
			return;
		if (parentY2 - (getY() + parentY) <= 0)
			return;
		RenderSystem.setShader(GameRenderer::getPositionShader);
		if (parent == parent.parent.getTopWindow() && RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x2, y2))
		{
			if (module.isEnabled())
				RenderSystem.setShaderColor(0.4f, 0.8f, 0.4f, 1.0f);
			else
				RenderSystem.setShaderColor(0.6f, 0.6f, 0.6f, 1.0f);
		}
		else
		{
			if (module.isEnabled())
				RenderSystem.setShaderColor(0.0f, 0.8f, 0.0f, 1.0f);
			else
				RenderSystem.setShaderColor(0.4f, 0.4f, 0.4f, 0.4f);
		}
		RenderUtils.drawQuad(x, y, x2, y2, matrices);
		double textX = x + 2;
		double textY = y + 1;
		ITextRenderer textRenderer = (ITextRenderer) MC.textRenderer;
		textRenderer.drawTrimmed(new LiteralText(module.getName()), (float) textX, (float) textY, (int) (x2 - textX), 0x0, matrices.peek().getPositionMatrix());
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
		double y = getY() + parentY;
		double x2 = parentX2 - getX();
		double y2 = Math.min(y + 20, parentY2);
		if (RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x2, y2))
		{
			if (button == GLFW.GLFW_MOUSE_BUTTON_1)
			{
				module.toggle();
			}
			else
			{
				if (!settingWindowOpened)
				{
					ClickGui gui = parent.parent;
					moduleSettingWindow = new ModuleSettingWindow(gui, mouseX, mouseY, module, this);
					gui.add(moduleSettingWindow);
					settingWindowOpened = true;
				}
				else
				{
					parent.parent.moveToTop(moduleSettingWindow);
				}
			}
		}
	}

	public void settingWindowClosed()
	{
		settingWindowOpened = false;
		moduleSettingWindow = null;
	}
}
