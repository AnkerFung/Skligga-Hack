package net.skliggahack.module.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.skliggahack.SkliggaHack;
import net.skliggahack.event.EventManager;
import net.skliggahack.event.events.PlayerTickListener;
import net.skliggahack.module.Category;
import net.skliggahack.module.Module;
import net.skliggahack.module.setting.BooleanSetting;
import net.skliggahack.module.setting.DecimalSetting;

import static net.skliggahack.SkliggaHack.MC;

public class TriggerBot extends Module implements PlayerTickListener
{

	private final DecimalSetting cooldown = DecimalSetting.Builder.newInstance()
			.setName("cooldown")
			.setDescription("the cooldown threshold")
			.setModule(this)
			.setValue(1)
			.setMin(0)
			.setMax(1)
			.setStep(0.1)
			.setAvailability(() -> true)
			.build();

	private final BooleanSetting attackInAir = BooleanSetting.Builder.newInstance()
			.setName("attackInAir")
			.setDescription("whether or not to attack in mid air")
			.setModule(this)
			.setValue(true)
			.setAvailability(() -> true)
			.build();

	private final BooleanSetting attackOnJump = BooleanSetting.Builder.newInstance()
			.setName("attackOnJump")
			.setDescription("whether or not to attack when going upwards")
			.setModule(this)
			.setValue(true)
			.setAvailability(attackInAir::get)
			.build();

	public TriggerBot()
	{
		super("TriggerBot", "automatically attacks players for you", false, Category.COMBAT);
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
		if (MC.player.isUsingItem())
			return;
		if (!(MC.player.getMainHandStack().getItem() instanceof SwordItem))
			return;
		HitResult hit = MC.crosshairTarget;
		if (hit.getType() != HitResult.Type.ENTITY)
			return;
		if (MC.player.getAttackCooldownProgress(0) < cooldown.get())
			return;
		Entity target = ((EntityHitResult) hit).getEntity();
		if (!(target instanceof PlayerEntity))
			return;
		if (!target.isOnGround() && !attackInAir.get())
			return;
		if (MC.player.getY() > MC.player.prevY && !attackOnJump.get())
			return;
		MC.interactionManager.attackEntity(MC.player, target);
		MC.player.swingHand(Hand.MAIN_HAND);
	}
}
