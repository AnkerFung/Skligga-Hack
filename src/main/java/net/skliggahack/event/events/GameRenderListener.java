package net.skliggahack.event.events;

import net.minecraft.client.util.math.MatrixStack;
import net.skliggahack.event.Event;
import net.skliggahack.event.Listener;

import java.util.ArrayList;

public interface GameRenderListener extends Listener
{
	void onGameRender(MatrixStack matrixStack, float tickDelta);

	class GameRenderEvent extends Event<GameRenderListener>
	{

		private MatrixStack matrixStack;
		private float tickDelta;

		public GameRenderEvent(MatrixStack matrixStack, float tickDelta)
		{
			this.matrixStack = matrixStack;
			this.tickDelta = tickDelta;
		}

		@Override
		public void fire(ArrayList<GameRenderListener> listeners)
		{
			listeners.forEach(e -> e.onGameRender(matrixStack, tickDelta));
		}

		@Override
		public Class<GameRenderListener> getListenerType()
		{
			return GameRenderListener.class;
		}
	}
}
