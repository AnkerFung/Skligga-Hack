package net.skliggahack.util;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.LogManager;

import static net.skliggahack.SkliggaHack.MC;

public enum ChatUtils
{
	;
	private static final String prefix = "§f[§2SkliggaHack§f] ";

	public static void log(String message)
	{
		LogManager.getLogger().info("[SkliggaHack] {}", message.replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
	}
	public static void info(String message)
	{
		String string = prefix + "Info: " + message;
		sendPlainMessage(string);
	}
	public static void error(String message)
	{
		String string = prefix + "§4Error: §f" + message;
		sendPlainMessage(string);
	}
	public static void sendPlainMessage(String message)
	{
		InGameHud hud = MC.inGameHud;
		if (hud != null)
			hud.getChatHud().addMessage(new LiteralText(message));
	}
}
