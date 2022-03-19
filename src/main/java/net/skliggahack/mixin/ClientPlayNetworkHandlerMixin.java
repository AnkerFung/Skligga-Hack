package net.skliggahack.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.skliggahack.SkliggaHack;
import net.skliggahack.module.modules.misc.NoLoadingScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin
{

	@Shadow
	@Final
	private MinecraftClient client;

	private boolean positionLookSetup = false;

	@Inject(at = @At("TAIL"), method = "onPlayerPositionLook")
	private void onPlayerPositionLook(PlayerPositionLookS2CPacket packet, CallbackInfo ci)
	{
		if (!positionLookSetup)
		{
			positionLookSetup = true;
			if (SkliggaHack.INSTANCE.getModuleManager().getModule(NoLoadingScreen.class).isEnabled())
				client.setScreen(null);
		}
	}

	@Inject(at = @At("HEAD"), method = "onPlayerRespawn")
	private void reset(PlayerRespawnS2CPacket packet, CallbackInfo ci)
	{
		positionLookSetup = false;
	}

}
