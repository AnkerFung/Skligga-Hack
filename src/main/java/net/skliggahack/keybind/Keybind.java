package net.skliggahack.keybind;

public record Keybind(String name, int key, boolean activateOnPress, boolean activateOnRelease,
                      Runnable action)
{

	public String getName()
	{
		return name;
	}

	public int getKey()
	{
		return key;
	}

	public void execute()
	{
		action.run();
	}

	public boolean shouldActivateOnPress()
	{
		return activateOnPress;
	}

	public boolean shouldActivateOnRelease()
	{
		return activateOnRelease;
	}
}