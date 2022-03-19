package net.skliggahack;

import net.minecraft.client.MinecraftClient;
import net.skliggahack.command.CommandParser;
import net.skliggahack.event.EventManager;
import net.skliggahack.gui.ClickGui;
import net.skliggahack.keybind.KeybindManager;
import net.skliggahack.module.ModuleManager;
import net.skliggahack.util.CrystalDataTracker;

public enum SkliggaHack
{

	INSTANCE;

	public static final MinecraftClient MC = MinecraftClient.getInstance();

	private EventManager eventManager;
	private ModuleManager moduleManager;
	private CommandParser commandParser;
	private KeybindManager keybindManager;
	private ClickGui gui;
	private boolean guiInitialized = false;
	private CrystalDataTracker crystalDataTracker;

	public void init()
	{
		eventManager = new EventManager();
		moduleManager = new ModuleManager();
		commandParser = new CommandParser();
		keybindManager = new KeybindManager();
		gui = new ClickGui();
		crystalDataTracker = new CrystalDataTracker();
	}

	public EventManager getEventManager()
	{
		return eventManager;
	}

	public ModuleManager getModuleManager()
	{
		return moduleManager;
	}

	public CommandParser getCommandParser()
	{
		return commandParser;
	}

	public KeybindManager getKeybindManager()
	{
		return keybindManager;
	}

	public ClickGui getClickGui()
	{
		if (!guiInitialized)
		{
			gui.init();
			guiInitialized = true;
		}
		return gui;
	}

	public CrystalDataTracker getCrystalDataTracker()
	{
		return crystalDataTracker;
	}
}
