package net.skliggahack.util;

public enum MathUtils
{
	;
	public static double roundToStep(double value, double step)
	{
		return step * Math.round(value / step);
	}
}
