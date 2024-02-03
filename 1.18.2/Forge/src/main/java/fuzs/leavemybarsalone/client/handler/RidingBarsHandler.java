package fuzs.leavemybarsalone.client.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class RidingBarsHandler {
    public static final IIngameOverlay FOOD_LEVEL_MOUNTED_GUI_OVERLAY = (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) return;
        final Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player.getVehicle() instanceof LivingEntity && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);
            gui.renderFood(screenWidth, screenHeight, poseStack);
        }
    };
    public static final IIngameOverlay EXPERIENCE_BAR_MOUNTED_GUI_OVERLAY = (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
        final Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player.getJumpRidingScale() == 0.0F && minecraft.player.isRidingJumpable() && !minecraft.options.hideGui) {
            gui.setupOverlayRenderState(true, false);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            if (minecraft.gameMode.hasExperience()) {
                gui.renderExperienceBar(poseStack, screenWidth / 2 - 91);
            }
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    };

    public static void onRenderGuiOverlayEvent$Pre(final RenderGameOverlayEvent.PreLayer evt) {
        // not sure if transparency could be an issue here (possibly with resource packs if vanilla doesn't have it?),
        // so cancel this to be safe
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
        if (evt.getOverlay() == ForgeIngameGui.JUMP_BAR_ELEMENT) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player.getJumpRidingScale() == 0.0F) {
                evt.setCanceled(true);
            }
        }
    }
}
