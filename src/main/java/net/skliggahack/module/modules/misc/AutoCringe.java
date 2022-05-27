package net.skliggahack.module.modules.misc;

import net.skliggahack.event.events.SendChatMessageListener;
import net.skliggahack.module.Category;
import net.skliggahack.module.Module;

public class AutoCringe extends Module implements SendChatMessageListener
{
	public AutoCringe()
	{
		super("AutoCringe", "cringe", false, Category.MISC);
	}

	@Override
	public void onEnable()
	{
		getEventManager().add(SendChatMessageListener.class, this);
	}

	@Override
	public void onDisable()
	{
		getEventManager().remove(SendChatMessageListener.class, this);
	}

	@Override
	public void sendChatMessage(SendChatMessageEvent event)
	{
		char[] chars = event.getMessage().toCharArray();
		boolean bl = false;
		for (int i = 0; i < chars.length; i++)
		{
			if (bl)
				chars[i] = Character.toUpperCase(chars[i]);
			else
				chars[i] = Character.toLowerCase(chars[i]);
			bl = !bl;
		}
		event.setMessage(new String(chars));
	}
}
