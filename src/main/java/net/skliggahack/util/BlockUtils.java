package net.skliggahack.util;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.stream.Stream;

import static net.skliggahack.SkliggaHack.MC;

public enum BlockUtils
{
	;

	public static boolean canPlace(BlockState state, BlockPos pos)
	{
		return MC.world.canPlace(state, pos, null);
	}

	public static boolean hasBlock(BlockPos pos)
	{
		return !MC.world.getBlockState(pos).isAir();
	}

	public static boolean isBlock(Block block, BlockPos pos)
	{
		return getBlockState(pos).getBlock() == block;
	}

	public static Block getBlock(BlockPos pos)
	{
		return MC.world.getBlockState(pos).getBlock();
	}

	public static BlockState getBlockState(BlockPos pos)
	{
		return MC.world.getBlockState(pos);
	}

	public static BlockState getDefaultBlockState()
	{
		return Blocks.STONE.getDefaultState();
	}

	public static boolean isBlockReplaceable(BlockPos pos)
	{
		return getBlockState(pos).getMaterial().isReplaceable();
	}

	public static Stream<BlockPos> getAllInBoxStream(BlockPos from, BlockPos to)
	{
		BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()),
				Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
		BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()),
				Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));

		Stream<BlockPos> stream = Stream.iterate(min, pos -> {

			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			x++;

			if(x > max.getX())
			{
				x = min.getX();
				y++;
			}

			if(y > max.getY())
			{
				y = min.getY();
				z++;
			}

			if(z > max.getZ())
				throw new IllegalStateException("Stream limit didn't work.");

			return new BlockPos(x, y, z);
		});

		int limit = (max.getX() - min.getX() + 1)
				* (max.getY() - min.getY() + 1) * (max.getZ() - min.getZ() + 1);

		return stream.limit(limit);
	}

}
