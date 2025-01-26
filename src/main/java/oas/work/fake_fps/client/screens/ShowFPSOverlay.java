
package oas.work.fake_fps.client.screens;

import org.checkerframework.checker.units.qual.h;

import oas.work.fake_fps.procedures.ShowFPSActivatedProcedure;
import oas.work.fake_fps.procedures.ReturnFPSProcedure;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;

@EventBusSubscriber({Dist.CLIENT})
public class ShowFPSOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Level world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		Player entity = Minecraft.getInstance().player;
		if (entity != null) {
			world = entity.level();
			x = entity.getX();
			y = entity.getY();
			z = entity.getZ();
		}
		if (ShowFPSActivatedProcedure.execute()) {
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					ReturnFPSProcedure.execute(), 1, 1, -1, false);
		}
	}
}
