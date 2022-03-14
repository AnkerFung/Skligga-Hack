package net.skliggahack.gui.component;

import net.minecraft.client.util.math.MatrixStack;
import net.skliggahack.gui.window.Window;

public abstract class Component
{

	public final Window parent;
	private double x, y;
	private final double length;
	private final String name;

	public Component(Window parent, double x, double y, double length, String name)
	{
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.length = length;
		this.name = name;
	}

	public abstract void render(MatrixStack matrices, int mouseX, int mouseY, float delta);

	public void onMouseMoved(double mouseX, double mouseY)
	{

	}

	public void onMouseClicked(double mouseX, double mouseY, int button)
	{

	}

	public boolean onMouseScrolled(double mouseX, double mouseY, double amount)
	{
		return false;
	}

	public boolean onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
	{
		return false;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getLength()
	{
		return length;
	}

	public String getName()
	{
		return name;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}
}
