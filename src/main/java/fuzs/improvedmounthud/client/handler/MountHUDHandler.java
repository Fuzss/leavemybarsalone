package fuzs.improvedmounthud.client.handler;

import fuzs.improvedmounthud.ImprovedMountHUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MountHUDHandler {
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre evt) {
        if (evt.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        if (ImprovedMountHUD.CONFIG.client().foodBar) {
            ForgeIngameGui.renderFood = true;
        }
        if (ImprovedMountHUD.CONFIG.client().experienceBar) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player.getJumpRidingScale() == 0.0F) {
                ForgeIngameGui.renderJumpBar = false;
            }
        }
    }
}
