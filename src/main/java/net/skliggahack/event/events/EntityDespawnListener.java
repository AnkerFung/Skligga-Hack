package net.skliggahack.event.events;

import net.minecraft.entity.Entity;
import net.skliggahack.event.Event;
import net.skliggahack.event.Listener;

import java.util.ArrayList;

public interface EntityDespawnListener extends Listener
{
	void onEntityDespawn(Entity entity);

	class EntityDespawnEvent extends Event<EntityDespawnListener>
	{

		private Entity entity;

		public EntityDespawnEvent(Entity entity)
		{
			this.entity = entity;
		}

		@Override
		public void fire(ArrayList<EntityDespawnListener> listeners)
		{
			listeners.forEach(e -> e.onEntityDespawn(entity));
		}

		@Override
		public Class<EntityDespawnListener> getListenerType()
		{
			return EntityDespawnListener.class;
		}
	}
}
