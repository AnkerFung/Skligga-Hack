package net.skliggahack.event.events;

import net.skliggahack.event.Event;
import net.skliggahack.event.Listener;

import java.util.ArrayList;

public interface PlayerTickListener extends Listener
{
	void onPlayerTick();

	class PlayerTickEvent extends Event<PlayerTickListener>
	{

		@Override
		public void fire(ArrayList<PlayerTickListener> listeners)
		{
			listeners.forEach(PlayerTickListener::onPlayerTick);
		}

		@Override
		public Class<PlayerTickListener> getListenerType()
		{
			return PlayerTickListener.class;
		}
	}
}
