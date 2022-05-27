package net.skliggahack.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.skliggahack.event.EventManager;
import net.skliggahack.event.events.GameRenderListener.GameRenderEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
	@Inject(
			at = {@At(value = "FIELD",
					target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
					opcode = Opcodes.GETFIELD,
					ordinal = 0)},
			method = {
					"renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V"},
			locals = LocalCapture.CAPTURE_FAILSOFT)
	private void onRenderWorld(float tickDelta, long finishTimeNano, MatrixStack matrices, CallbackInfo ci, boolean bl, Camera camera, MatrixStack matrixStack, double d, float f, Matrix4f matrix4f, Matrix3f matrix3f)
	{
		GameRenderEvent event = new GameRenderEvent(matrices, tickDelta);
		EventManager.fire(event);
	}
}
