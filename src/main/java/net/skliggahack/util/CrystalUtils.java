package net.skliggahack.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.skliggahack.SkliggaHack;

import java.util.List;

import static net.skliggahack.SkliggaHack.MC;

public enum CrystalUtils
{
	;
	public static boolean canPlaceCrystalServer(BlockPos block)
	{
		BlockState blockState = MC.world.getBlockState(block);
		if (!blockState.isOf(Blocks.OBSIDIAN) && !blockState.isOf(Blocks.BEDROCK))
			return false;
		BlockPos blockPos2 = block.up();
		if (!MC.world.isAir(blockPos2))
			return false;
		double d = blockPos2.getX();
		double e = blockPos2.getY();
		double f = blockPos2.getZ();
		List<Entity> list = MC.world.getOtherEntities((Entity)null, new Box(d, e, f, d + 1.0D, e + 2.0D, f + 1.0D));
		//list.removeIf(entity -> entity instanceof ItemEntity); // items will be picked up by the nearby player, crystals can be placed down a lot faster in citying
		list.removeIf(entity ->
		{
			if (!(entity instanceof EndCrystalEntity))
				return false;
			return SkliggaHack.INSTANCE.getCrystalDataTracker().isCrystalAttacked(entity);
		}); // crystal placement will be faster since on the server side the crystal have already been removed (probably)
		return list.isEmpty();
	}
	public static boolean canPlaceCrystalClient(BlockPos block)
	{
		BlockState blockState = MC.world.getBlockState(block);
		if (!blockState.isOf(Blocks.OBSIDIAN) && !blockState.isOf(Blocks.BEDROCK))
			return false;
		return canPlaceCrystalClientAssumeObsidian(block);
	}
	public static boolean canPlaceCrystalClientAssumeObsidian(BlockPos block)
	{
		BlockPos blockPos2 = block.up();
		if (!MC.world.isAir(blockPos2))
			return false;
		double d = blockPos2.getX();
		double e = blockPos2.getY();
		double f = blockPos2.getZ();
		List<Entity> list = MC.world.getOtherEntities((Entity)null, new Box(d, e, f, d + 1.0D, e + 2.0D, f + 1.0D));
		return list.isEmpty();
	}
	public static boolean canPlaceCrystalClientAssumeObsidian(BlockPos block, Box bb)
	{
		BlockPos blockPos2 = block.up();
		if (!MC.world.isAir(blockPos2))
			return false;
		double d = blockPos2.getX();
		double e = blockPos2.getY();
		double f = blockPos2.getZ();
		Box crystalBox = new Box(d, e, f, d + 1.0D, e + 2.0D, f + 1.0D);
		if (crystalBox.intersects(bb))
			return false;
		List<Entity> list = MC.world.getOtherEntities((Entity)null, crystalBox);
		return list.isEmpty();
	}
}
