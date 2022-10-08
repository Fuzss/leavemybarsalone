package fuzs.leavemybarsalone.integration;

import fuzs.leavemybarsalone.client.LeaveMyBarsAloneForgeClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;

public class AppleSkinIntegration {

    public static void onRenderGuiOverlay$Pre(RenderGuiOverlayEvent.Pre evt) {
        if (evt.getOverlay() == GuiOverlayManager.findOverlay(LeaveMyBarsAloneForgeClient.FOOD_LEVEL_MOUNTED_KEY)) {
            Minecraft mc = Minecraft.getInstance();
            ForgeGui gui = (ForgeGui) mc.gui;
            boolean isMounted = mc.player.getVehicle() instanceof LivingEntity;
            if (isMounted && !mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
                HUDOverlayHandler.renderExhaustion(gui, evt.getPoseStack(), evt.getPartialTick(), evt.getWindow().getScreenWidth(), evt.getWindow().getScreenHeight());
            }
        }
    }

    public static void onRenderGuiOverlay$Post(RenderGuiOverlayEvent.Post evt) {
        if (evt.getOverlay() == GuiOverlayManager.findOverlay(LeaveMyBarsAloneForgeClient.EXPERIENCE_BAR_MOUNTED_KEY)) {
            Minecraft mc = Minecraft.getInstance();
            ForgeGui gui = (ForgeGui) mc.gui;
            boolean isMounted = mc.player.getVehicle() instanceof LivingEntity;
            if (isMounted && !mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
                HUDOverlayHandler.renderFoodOrHealthOverlay(gui, evt.getPoseStack(), evt.getPartialTick(), evt.getWindow().getScreenWidth(), evt.getWindow().getScreenHeight(), HUDOverlayHandler.RenderOverlayType.FOOD);
            }
        }
    }
}
