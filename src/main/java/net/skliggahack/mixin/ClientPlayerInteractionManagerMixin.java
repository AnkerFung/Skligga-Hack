package net.skliggahack.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.skliggahack.event.events.AttackEntityListener.AttackEntityEvent;
import net.skliggahack.util.MixinUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin
{
	@Inject(at = @At("HEAD"), method = "attackEntity", cancellable = true)
	private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci)
	{
		MixinUtils.fireCancellable(new AttackEntityEvent(player, target), ci);
	}
}
