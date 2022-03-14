package net.skliggahack.module.modules.combat;

import net.minecraft.item.Items;
import net.skliggahack.SkliggaHack;
import net.skliggahack.event.EventManager;
import net.skliggahack.event.events.PlayerTickListener;
import net.skliggahack.module.Category;
import net.skliggahack.module.Module;
import net.skliggahack.module.setting.IntegerSetting;
import net.skliggahack.util.InventoryUtils;

import static net.skliggahack.SkliggaHack.MC;

public class AutoRekit extends Module implements PlayerTickListener
{

	private final IntegerSetting kitNum = IntegerSetting.Builder.newInstance()
			.setName("kitNum")
			.setDescription("the kit number you want to use")
			.setModule(this)
			.setValue(1)
			.setMin(1)
			.setMax(9)
			.setAvailability(() -> true)
			.build();

	public AutoRekit()
	{
		super("AutoRekit", "automatically rekit when you run out of totems", false, Category.COMBAT);
	}

	@Override
	public void onEnable()
	{
		EventManager eventManager = SkliggaHack.INSTANCE.getEventManager();
		eventManager.add(PlayerTickListener.class, this);
	}

	@Override
	public void onDisable()
	{
		EventManager eventManager = SkliggaHack.INSTANCE.getEventManager();
		eventManager.remove(PlayerTickListener.class, this);
	}

	@Override
	public void onPlayerTick()
	{
		if (InventoryUtils.countItem(Items.TOTEM_OF_UNDYING) == 0)
			MC.player.sendChatMessage("/k" + kitNum.get());
	}
}
