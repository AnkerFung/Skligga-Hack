package net.skliggahack.core;

import net.minecraft.util.math.Vec3d;
import net.skliggahack.SkliggaHack;
import net.skliggahack.event.events.PlayerTickListener;
import net.skliggahack.util.RotationUtils;

import java.util.ArrayList;

import static net.skliggahack.SkliggaHack.MC;

public class Rotator implements PlayerTickListener
{

	public Rotator()
	{
		SkliggaHack.INSTANCE.getEventManager().add(PlayerTickListener.class, this);
	}

	private final ArrayList<Rotation> rotations = new ArrayList<>();
	private Runnable callback;

	@Override
	public void onPlayerTick()
	{
		if (rotations.size() != 0)
		{
			RotationUtils.setRotation(rotations.get(rotations.size() - 1));
			rotations.remove(rotations.size() - 1);
			if (rotations.size() == 0)
				callback.run();
		}
	}

	public void stepToward(Vec3d pos, int steps, Runnable callback)
	{
		stepToward(RotationUtils.getNeededRotations(pos), steps, callback);
	}

	public void stepToward(Rotation rotation, int steps, Runnable callback)
	{
		rotations.clear();
		float yaw = rotation.getYaw();
		float pitch = rotation.getPitch();
		float stepYaw = (yaw - MC.player.getYaw()) / (float) steps;
		float stepPitch = (pitch - MC.player.getPitch()) / (float) steps;
		for (int i = 0; i < steps; i++)
		{
			rotations.add(new Rotation(yaw, rotation.isIgnoreYaw(), pitch, rotation.isIgnorePitch()));
			yaw -= stepYaw;
			pitch -= stepPitch;
		}
		this.callback = callback;
	}
}
