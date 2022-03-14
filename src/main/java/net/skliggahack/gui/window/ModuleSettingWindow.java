package net.skliggahack.gui.window;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.skliggahack.gui.ClickGui;
import net.skliggahack.gui.component.Component;
import net.skliggahack.gui.component.ModuleButtonComponent;
import net.skliggahack.mixinterface.ITextRenderer;
import net.skliggahack.module.Module;
import net.skliggahack.module.setting.Setting;

import static net.skliggahack.SkliggaHack.MC;

public class ModuleSettingWindow extends Window
{

	private final Module module;
	private final ModuleButtonComponent moduleButton;

	public ModuleSettingWindow(ClickGui parent, double x, double y, Module module, ModuleButtonComponent moduleButton)
	{
		super(parent, x, y, 150, 200);
		this.module = module;
		this.moduleButton = moduleButton;
		y = 40;
		for (Setting<?> setting : module.getSettings())
		{
			Component component = setting.makeComponent(this);
			component.setX(20);
			component.setY(y);
			addComponent(component);
			y += component.getLength() + 20.0;
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.render(matrices, mouseX, mouseY, delta);
		if (minimized)
			return;
		for (Component component : components)
		{
			double parentX = getX();
			double parentY = getY();
			double parentWidth = getWidth();
			double parentLength = getLength();
			double parentX2 = getX() + parentWidth;
			double parentY2 = getY() + parentLength;
			double x = component.getX() + parentX;
			double y = Math.max(component.getY() + parentY - 10, parentY);
			((ITextRenderer) MC.textRenderer).drawTrimmed(new LiteralText(component.getName()), (float) x, (float) y, (int) (parentX2 - x), 0xFFFFFF, matrices.peek().getPositionMatrix());
		}
	}

	@Override
	public void onClose()
	{
		moduleButton.settingWindowClosed();
	}

}
