package fuzs.leavemybarsalone.integration.appleskin;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.client.handler.RidingBarsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class AppleSkinIntegration {

    public static void init() {
        MethodHandle renderExhaustionHandle;
        MethodHandle renderFoodOrHealthOverlayHandle;
        Object foodOverlayType;
        try {
            Class<?> renderOverlayTypeClazz = Class.forName("squeek.appleskin.client.HUDOverlayHandler$RenderOverlayType");
            MethodType renderExhaustionType = MethodType.methodType(void.class, ForgeIngameGui.class, PoseStack.class, float.class, int.class, int.class);
            MethodType renderFoodOrHealthOverlayType = MethodType.methodType(void.class, ForgeIngameGui.class, PoseStack.class, float.class, int.class, int.class, renderOverlayTypeClazz);
            Class<?> clazz = Class.forName("squeek.appleskin.client.HUDOverlayHandler");
            renderExhaustionHandle = MethodHandles.publicLookup().findStatic(clazz, "renderExhaustion", renderExhaustionType);
            renderFoodOrHealthOverlayHandle = MethodHandles.publicLookup().findStatic(clazz, "renderFoodOrHealthOverlay", renderFoodOrHealthOverlayType);
            Field foodField = renderOverlayTypeClazz.getField("FOOD");
            foodField.setAccessible(true);
            MethodHandle foodGetter = MethodHandles.publicLookup().unreflectGetter(foodField);
            foodOverlayType = foodGetter.invoke();
        } catch (Throwable e) {
            LeaveMyBarsAlone.LOGGER.warn("Failed to initialize Apple Skin integration", e);
            return;
        }
        MinecraftForge.EVENT_BUS.addListener((RenderGameOverlayEvent.PreLayer evt) -> onRenderGuiOverlay$Pre(evt, renderExhaustionHandle));
        MinecraftForge.EVENT_BUS.addListener((RenderGameOverlayEvent.PostLayer evt) -> onRenderGuiOverlay$Post(evt, renderFoodOrHealthOverlayHandle, foodOverlayType));
    }

    private static void onRenderGuiOverlay$Pre(final RenderGameOverlayEvent.PreLayer evt, MethodHandle methodHandle) {
        if (evt.getOverlay() == RidingBarsHandler.FOOD_LEVEL_MOUNTED_GUI_OVERLAY) {
            Minecraft minecraft = Minecraft.getInstance();
            ForgeIngameGui gui = (ForgeIngameGui) minecraft.gui;
            if (minecraft.player.getVehicle() instanceof LivingEntity && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                try {
                    methodHandle.invoke(gui, evt.getMatrixStack(), evt.getPartialTicks(), evt.getWindow().getScreenWidth(), evt.getWindow().getScreenHeight());
                } catch (Throwable e) {
                    LeaveMyBarsAlone.LOGGER.warn("Failed to render Apple Skin exhaustion overlay", e);
                }
            }
        }
    }

    private static void onRenderGuiOverlay$Post(final RenderGameOverlayEvent.PostLayer evt, MethodHandle methodHandle, Object foodOverlayType) {
        if (evt.getOverlay() == RidingBarsHandler.EXPERIENCE_BAR_MOUNTED_GUI_OVERLAY) {
            Minecraft minecraft = Minecraft.getInstance();
            ForgeIngameGui gui = (ForgeIngameGui) minecraft.gui;
            if (minecraft.player.getVehicle() instanceof LivingEntity && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                try {
                    methodHandle.invoke(gui, evt.getMatrixStack(), evt.getPartialTicks(), evt.getWindow().getScreenWidth(), evt.getWindow().getScreenHeight(), foodOverlayType);
                } catch (Throwable e) {
                    LeaveMyBarsAlone.LOGGER.warn("Failed to render Apple Skin food overlay", e);
                }
            }
        }
    }
}
