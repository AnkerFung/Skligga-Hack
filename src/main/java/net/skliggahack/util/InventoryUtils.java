package net.skliggahack.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

import static net.skliggahack.SkliggaHack.MC;

public enum InventoryUtils
{
	;

	public static boolean selectItemFromHotbar(Predicate<Item> item)
	{
		PlayerInventory inv = MC.player.getInventory();

		for (int i = 0; i < 9; i++)
		{
			ItemStack itemStack = inv.getStack(i);
			if (!item.test(itemStack.getItem()))
				continue;
			inv.selectedSlot = i;
			return true;
		}

		return false;
	}

	public static boolean selectItemFromHotbar(Item item)
	{
		return selectItemFromHotbar(i -> i == item);
	}

	public static boolean hasItemInHotbar(Predicate<Item> item)
	{
		PlayerInventory inv = MC.player.getInventory();

		for (int i = 0; i < 9; i++)
		{
			ItemStack itemStack = inv.getStack(i);
			if (item.test(itemStack.getItem()))
				return true;
		}
		return false;
	}

	public static int countItem(Predicate<Item> item)
	{
		PlayerInventory inv = MC.player.getInventory();

		int count = 0;

		for (int i = 0; i < 36; i++)
		{
			if (item.test(inv.getStack(i).getItem()))
				count++;
		}

		return count;
	}

	public static int countItem(Item item)
	{
		return countItem(i -> i == item);
	}
}
