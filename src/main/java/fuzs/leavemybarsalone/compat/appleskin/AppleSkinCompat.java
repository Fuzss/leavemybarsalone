package fuzs.leavemybarsalone.compat.appleskin;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import squeek.appleskin.client.HUDOverlayHandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class AppleSkinCompat {

    public static void init() {
        MethodHandle renderFoodOrHealthOverlayHandle;
        Object foodOverlayType;
        try {
            Class<?> renderOverlayTypeClazz = Class.forName("squeek.appleskin.client.HUDOverlayHandler$RenderOverlayType");
            MethodType renderFoodOrHealthOverlayType = MethodType.methodType(void.class, ForgeIngameGui.class, PoseStack.class, float.class, int.class, int.class, renderOverlayTypeClazz);
            renderFoodOrHealthOverlayHandle = MethodHandles.publicLookup().findStatic(HUDOverlayHandler.class, "renderFoodOrHealthOverlay", renderFoodOrHealthOverlayType);
            Field foodField = renderOverlayTypeClazz.getField("FOOD");
            foodField.setAccessible(true);
            MethodHandle foodGetter = MethodHandles.publicLookup().unreflectGetter(foodField);
            foodOverlayType = foodGetter.invoke();
        } catch (Throwable e) {
            LeaveMyBarsAlone.LOGGER.warn("Failed to initialize Apple Skin integration", e);
            return;
        }
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.FOOD_LEVEL_ELEMENT, new ResourceLocation(LeaveMyBarsAlone.MOD_ID, "appleskin_exhaustion_mounted").toString(), (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (!LeaveMyBarsAlone.CONFIG.client().foodBar) return;
            final Minecraft minecraft = Minecraft.getInstance();
            boolean isMounted = minecraft.player.getVehicle() instanceof LivingEntity;
            if (isMounted && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                try {
                    HUDOverlayHandler.renderExhaustion(gui, mStack, partialTicks, screenWidth, screenHeight);
                } catch (Throwable e) {
                    LeaveMyBarsAlone.LOGGER.warn("Failed to render Apple Skin exhaustion overlay", e);
                }
            }
        });
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.FOOD_LEVEL_ELEMENT, new ResourceLocation(LeaveMyBarsAlone.MOD_ID, "appleskin_food_overlay_mounted").toString(), (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (!LeaveMyBarsAlone.CONFIG.client().foodBar) return;
            final Minecraft minecraft = Minecraft.getInstance();
            boolean isMounted = minecraft.player.getVehicle() instanceof LivingEntity;
            if (isMounted && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                try {
                    renderFoodOrHealthOverlayHandle.invoke(gui, mStack, partialTicks, screenWidth, screenHeight, foodOverlayType);
                } catch (Throwable e) {
                    LeaveMyBarsAlone.LOGGER.warn("Failed to render Apple Skin food overlay", e);
                }
            }
        });
    }
}
