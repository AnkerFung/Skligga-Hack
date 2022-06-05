package net.skliggahack.core;

import net.skliggahack.SkliggaHack;
import net.skliggahack.event.events.PlayerTickListener;

import java.util.LinkedList;

public class PlayerActionScheduler implements PlayerTickListener
{
	public PlayerActionScheduler()
	{
		SkliggaHack.INSTANCE.getEventManager().add(PlayerTickListener.class, this);
	}

	private final LinkedList<ScheduledEvent> scheduledEvents = new LinkedList<>();

	@Override
	public void onPlayerTick()
	{
		for (ScheduledEvent event : scheduledEvents)
		{
			if (event.afterTicks == 0)
				event.callback.run();
			else
				event.afterTicks--;
		}
		scheduledEvents.removeIf(e -> e.afterTicks == 0);
	}

	public void schedule(int afterTicks, Runnable callback)
	{
		scheduledEvents.addLast(new ScheduledEvent(afterTicks, callback));
	}

	private static class ScheduledEvent
	{
		public int afterTicks;
		public final Runnable callback;

		public ScheduledEvent(int afterTicks, Runnable callback)
		{
			this.afterTicks = afterTicks;
			this.callback = callback;
		}
	}
}
