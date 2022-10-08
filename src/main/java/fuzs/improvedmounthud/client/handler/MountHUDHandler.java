package fuzs.improvedmounthud.client.handler;

import fuzs.improvedmounthud.ImprovedMountHUD;
import fuzs.improvedmounthud.mixin.client.accessor.ForgeIngameGuiAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;

public class MountHUDHandler {
    public static void init() {
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.FOOD_LEVEL_ELEMENT,  new ResourceLocation(ImprovedMountHUD.MOD_ID, "food_level_mounted").toString(), (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (!ImprovedMountHUD.CONFIG.client().foodBar) return;
            final Minecraft minecraft = Minecraft.getInstance();
            boolean isMounted = minecraft.player.getVehicle() instanceof LivingEntity;
            if (isMounted && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                gui.renderFood(screenWidth, screenHeight, mStack);
            }
        });
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, new ResourceLocation(ImprovedMountHUD.MOD_ID, "experience_bar_mounted").toString(), (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (!ImprovedMountHUD.CONFIG.client().experienceBar) return;
            final Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player.getJumpRidingScale() == 0.0F && minecraft.player.isRidingJumpable() && !minecraft.options.hideGui) {
                gui.setupOverlayRenderState(true, false);
                ((ForgeIngameGuiAccessor) gui).callRenderExperience(screenWidth / 2 - 91, mStack);
            }
        });
    }
}
