package net.skliggahack.gui.window;

import net.skliggahack.gui.ClickGui;
import net.skliggahack.gui.component.Component;
import net.skliggahack.gui.component.ModuleButtonComponent;
import net.skliggahack.module.Module;
import net.skliggahack.module.setting.Setting;

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
	public void onClose()
	{
		moduleButton.settingWindowClosed();
	}

}
