package fuzs.leavemybarsalone.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import fuzs.puzzleslib.core.CoreServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import fuzs.leavemybarsalone.integration.AppleSkinIntegration;

@Mod.EventBusSubscriber(modid = LeaveMyBarsAlone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LeaveMyBarsAloneForgeClient {
    public static final ResourceLocation FOOD_LEVEL_MOUNTED_KEY = new ResourceLocation(LeaveMyBarsAlone.MOD_ID, "food_level_mounted");
    public static final ResourceLocation EXPERIENCE_BAR_MOUNTED_KEY = new ResourceLocation(LeaveMyBarsAlone.MOD_ID, "experience_bar_mounted");
    private static final IGuiOverlay FOOD_LEVEL_MOUNTED = new IGuiOverlay() {

        @Override
        public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
            if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) return;
            final Minecraft minecraft = Minecraft.getInstance();
            boolean isMounted = minecraft.player.getVehicle() instanceof LivingEntity;
            if (isMounted && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                gui.renderFood(screenWidth, screenHeight, poseStack);
            }
        }
    };
    private static final IGuiOverlay EXPERIENCE_BAR_MOUNTED = new IGuiOverlay() {

        @Override
        public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
            if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
            final Minecraft minecraft = gui.getMinecraft();
            if (minecraft.player.getJumpRidingScale() == 0.0F && minecraft.player.isRidingJumpable() && !minecraft.options.hideGui) {
                gui.setupOverlayRenderState(true, false);
                RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
                if (minecraft.gameMode.hasExperience()) {
                    gui.renderExperienceBar(poseStack, screenWidth / 2 - 91);
                }
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    };

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final RenderGuiOverlayEvent.Pre evt) -> {
            // not sure if transparency could be an issue here (possibly with resource packs if vanilla doesn't have it?),
            // so cancel this to be safe
            if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
            if (evt.getOverlay() == VanillaGuiOverlay.JUMP_BAR.type()) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player.getJumpRidingScale() == 0.0F) {
                    evt.setCanceled(true);
                }
            }
        });
        if (LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).appleSkin && CoreServices.ENVIRONMENT.isModLoaded("appleskin")) {
            MinecraftForge.EVENT_BUS.addListener(AppleSkinIntegration::onRenderGuiOverlay$Pre);
            MinecraftForge.EVENT_BUS.addListener(AppleSkinIntegration::onRenderGuiOverlay$Post);
        }
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(final RegisterGuiOverlaysEvent evt) {
        evt.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), FOOD_LEVEL_MOUNTED_KEY.getPath(), FOOD_LEVEL_MOUNTED);
        evt.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), EXPERIENCE_BAR_MOUNTED_KEY.getPath(), EXPERIENCE_BAR_MOUNTED);
    }
}
