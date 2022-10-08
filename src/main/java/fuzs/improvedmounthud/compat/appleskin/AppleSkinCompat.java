package fuzs.improvedmounthud.compat.appleskin;

import fuzs.improvedmounthud.ImprovedMountHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;

public class AppleSkinCompat {
    public static void init() {
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.FOOD_LEVEL_ELEMENT, new ResourceLocation(ImprovedMountHUD.MOD_ID, "appleskin_exhaustion_mounted").toString(), (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (!ImprovedMountHUD.CONFIG.client().foodBar) return;
            final Minecraft minecraft = Minecraft.getInstance();
            boolean isMounted = minecraft.player.getVehicle() instanceof LivingEntity;
            if (isMounted && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                HUDOverlayHandler.renderExhaustion(gui, mStack, partialTicks, screenWidth, screenHeight);
            }
        });
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.FOOD_LEVEL_ELEMENT, new ResourceLocation(ImprovedMountHUD.MOD_ID, "appleskin_food_overlay_mounted").toString(), (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (!ImprovedMountHUD.CONFIG.client().foodBar) return;
            final Minecraft minecraft = Minecraft.getInstance();
            boolean isMounted = minecraft.player.getVehicle() instanceof LivingEntity;
            if (isMounted && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                HUDOverlayHandler.renderFoodOrHealthOverlay(gui, mStack, partialTicks, screenWidth, screenHeight, HUDOverlayHandler.RenderOverlayType.FOOD);
            }
        });
    }
}
