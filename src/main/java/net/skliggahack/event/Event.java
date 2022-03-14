package net.skliggahack.event;

import java.util.ArrayList;

public abstract class Event<T extends Listener>
{
	public abstract void fire(ArrayList<T> listeners);

	public abstract Class<T> getListenerType();
}
