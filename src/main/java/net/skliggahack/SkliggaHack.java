package net.skliggahack;

import net.minecraft.client.MinecraftClient;
import net.skliggahack.command.CommandParser;
import net.skliggahack.core.CrystalDataTracker;
import net.skliggahack.core.PlayerActionScheduler;
import net.skliggahack.core.Rotator;
import net.skliggahack.event.EventManager;
import net.skliggahack.gui.ClickGui;
import net.skliggahack.keybind.KeybindManager;
import net.skliggahack.module.ModuleManager;

public enum SkliggaHack
{

	INSTANCE;

	public static MinecraftClient MC;

	private EventManager eventManager;
	private ModuleManager moduleManager;
	private CommandParser commandParser;
	private KeybindManager keybindManager;
	private ClickGui gui;
	private boolean guiInitialized = false;
	private CrystalDataTracker crystalDataTracker;
	private PlayerActionScheduler playerActionScheduler;
	private Rotator rotator;

	public void init()
	{
		MC = MinecraftClient.getInstance();
		eventManager = new EventManager();
		moduleManager = new ModuleManager();
		commandParser = new CommandParser();
		keybindManager = new KeybindManager();
		gui = new ClickGui();
		crystalDataTracker = new CrystalDataTracker();
		playerActionScheduler = new PlayerActionScheduler();
		rotator = new Rotator();
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

	public PlayerActionScheduler getPlayerActionScheduler()
	{
		return playerActionScheduler;
	}

	public Rotator getRotator()
	{
		return rotator;
	}
}
