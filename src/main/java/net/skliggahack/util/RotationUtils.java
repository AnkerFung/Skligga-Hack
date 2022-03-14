package net.skliggahack.util;

// copied from wurst

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import static net.skliggahack.SkliggaHack.MC;

public enum RotationUtils
{
	;

	public static Vec3d getEyesPos()
	{
		return getEyesPos(MC.player);
	}

	public static Vec3d getEyesPos(PlayerEntity player)
	{
		return new Vec3d(player.getX(),
				player.getY() + player.getEyeHeight(player.getPose()),
				player.getZ());
	}

	public static BlockPos getEyesBlockPos()
	{
		return new BlockPos(getEyesPos());
	}

	public static Vec3d getPlayerLookVec(PlayerEntity player)
	{
		float f = 0.017453292F;
		float pi = (float)Math.PI;

		float f1 = MathHelper.cos(-player.getYaw() * f - pi);
		float f2 = MathHelper.sin(-player.getYaw() * f - pi);
		float f3 = -MathHelper.cos(-player.getPitch() * f);
		float f4 = MathHelper.sin(-player.getPitch() * f);

		return new Vec3d(f2 * f3, f4, f1 * f3).normalize();
	}

	public static Vec3d getClientLookVec()
	{
		return getPlayerLookVec(MC.player);
	}

	public static Rotation getNeededRotations(Vec3d from, Vec3d vec)
	{
		Vec3d eyesPos = from;

		double diffX = vec.x - eyesPos.x;
		double diffY = vec.y - eyesPos.y;
		double diffZ = vec.z - eyesPos.z;

		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

		float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

		return new Rotation(yaw, pitch);
	}

	public static Rotation getNeededRotations(Vec3d vec)
	{
		Vec3d eyesPos = getEyesPos();

		double diffX = vec.x - eyesPos.x;
		double diffY = vec.y - eyesPos.y;
		double diffZ = vec.z - eyesPos.z;

		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

		float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

		return new Rotation(yaw, pitch);
	}

	public static double getAngleToLookVec(Vec3d vec)
	{
		return getAngleToLookVec(MC.player, vec);
	}

	public static double getAngleToLookVec(PlayerEntity player, Vec3d vec)
	{
		Rotation needed = getNeededRotations(vec);

		float currentYaw = MathHelper.wrapDegrees(player.getYaw());
		float currentPitch = MathHelper.wrapDegrees(player.getPitch());

		float diffYaw = currentYaw - needed.yaw;
		float diffPitch = currentPitch - needed.pitch;

		return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
	}

	public static final class Rotation
	{
		private final float yaw;
		private final float pitch;

		public Rotation(float yaw, float pitch)
		{
			this.yaw = MathHelper.wrapDegrees(yaw);
			this.pitch = MathHelper.wrapDegrees(pitch);
		}

		public float getYaw()
		{
			return yaw;
		}

		public float getPitch()
		{
			return pitch;
		}
	}
}