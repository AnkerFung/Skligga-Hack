package net.skliggahack.keybind;

import net.skliggahack.SkliggaHack;
import net.skliggahack.event.events.KeyPressListener;
import net.skliggahack.gui.GuiScreen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static net.skliggahack.SkliggaHack.MC;

public class KeybindManager implements KeyPressListener
{

	private final ArrayList<Keybind> keybinds = new ArrayList<>();

	public KeybindManager()
	{
		SkliggaHack.INSTANCE.getEventManager().add(KeyPressListener.class, this);
		addDefaultKeybinds();
	}

	public ArrayList<Keybind> getAllKeybinds()
	{
		return (ArrayList<Keybind>) keybinds.clone();
	}

	public void removeAll()
	{
		keybinds.clear();
		addDefaultKeybinds();
	}

	public void addKeybind(Keybind keybind)
	{
		keybinds.add(keybind);
	}

	public void removeKeybind(Keybind keybind)
	{
		keybinds.remove(keybind);
	}

	public void removeKeybind(String name)
	{
		keybinds.removeIf(e -> e.getName().equals(name));
	}

	@Override
	public void onKeyPress(KeyPressListener.KeyPressEvent event)
	{
		for (Keybind keybind : keybinds)
		{
			if (event.getKeyCode() == keybind.getKey())
			{
				if (event.getAction() == GLFW.GLFW_PRESS)
					keybind.press();
				if (event.getAction() == GLFW.GLFW_RELEASE)
					keybind.release();
			}
		}
		//event.cancel();
	}

	private void addDefaultKeybinds()
	{
		addKeybind(new Keybind("ctrl-gui", GLFW.GLFW_KEY_RIGHT_CONTROL, true, false, () ->
		{
			if (MC.currentScreen != null)
				return;
			MC.setScreen(new GuiScreen());
		}));
	}
}
