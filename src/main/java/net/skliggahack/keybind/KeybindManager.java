package net.skliggahack.keybind;

import net.skliggahack.SkliggaHack;
import net.skliggahack.event.events.KeyPressListener;
import net.skliggahack.gui.GuiScreen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;

import static net.skliggahack.SkliggaHack.MC;

public class KeybindManager implements KeyPressListener
{

	private final HashMap<Integer, ArrayList<Keybind>> keybinds = new HashMap<>();

	public KeybindManager()
	{
		SkliggaHack.INSTANCE.getEventManager().add(KeyPressListener.class, this);
		addDefaultKeybinds();
	}

	public ArrayList<Keybind> getAllKeybinds()
	{
		ArrayList<Keybind> result = new ArrayList<>();
		keybinds.forEach((i, a) -> result.addAll(a));
		return result;
	}

	public void removeAll()
	{
		keybinds.clear();
		addDefaultKeybinds();
	}

	public void addKeybind(Keybind keybind)
	{
		int key = keybind.getKey();
		if (keybinds.containsKey(key))
			keybinds.get(key).add(keybind);
		else
		{
			keybinds.put(key, new ArrayList<>());
			addKeybind(keybind);
		}
	}

	public void removeKeybind(String name)
	{
		keybinds.forEach((key, value) ->
				value.removeIf(k ->
						k.getName().equals(name)
				)
		);
	}

	@Override
	public void onKeyPress(KeyPressListener.KeyPressEvent event)
	{
		if (MC.currentScreen != null)
			return;
		if (!keybinds.containsKey(event.getKeyCode()))
			return;

		for (Keybind keybind : keybinds.get(event.getKeyCode()))
		{
			if (keybind.shouldActivateOnPress() && event.getAction() == GLFW.GLFW_PRESS)
				keybind.execute();
			if (keybind.shouldActivateOnRelease() && event.getAction() == GLFW.GLFW_RELEASE)
				keybind.execute();
		}
		//event.cancel();
	}

	private void addDefaultKeybinds()
	{
		addKeybind(new Keybind("ctrl-gui", GLFW.GLFW_KEY_RIGHT_CONTROL, true, false, () -> MC.setScreen(new GuiScreen())));
	}
}
