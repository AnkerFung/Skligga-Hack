package net.skliggahack.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.skliggahack.event.EventManager;
import net.skliggahack.event.events.PlayerTickListener;
import net.skliggahack.event.events.SendChatMessageListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.skliggahack.SkliggaHack.MC;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin
{
	@Inject(at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
			ordinal = 0), method = "tick()V")
	private void onTick(CallbackInfo ci)
	{
		EventManager.fire(new PlayerTickListener.PlayerTickEvent());
	}

	@Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
	public void sendChatMessage(String message, CallbackInfo ci)
	{
		SendChatMessageListener.SendChatMessageEvent event = new SendChatMessageListener.SendChatMessageEvent(message);
		EventManager.fire(event);
		if (event.isCancelled())
		{
			ci.cancel();
			return;
		}
		if (event.isModified())
		{
			MC.getNetworkHandler().sendPacket(new ChatMessageC2SPacket(event.getMessage()));
			ci.cancel();
		}
	}
}
