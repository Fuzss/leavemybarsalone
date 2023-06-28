package fuzs.leavemybarsalone.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.client.handler.RidingBarsHandler;
import fuzs.leavemybarsalone.integration.appleskin.AppleSkinIntegration;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LeaveMyBarsAlone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LeaveMyBarsAloneForgeClient {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        registerHandlers();
        registerIntegration();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener(RidingBarsHandler::onRenderGuiOverlayEvent$Pre);
    }

    private static void registerIntegration() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("appleskin")) {
            AppleSkinIntegration.init();
        }
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(final RegisterGuiOverlaysEvent evt) {
        evt.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), RidingBarsHandler.FOOD_LEVEL_MOUNTED_KEY.getPath(), RidingBarsHandler.FOOD_LEVEL_MOUNTED);
        evt.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), RidingBarsHandler.EXPERIENCE_BAR_MOUNTED_KEY.getPath(), RidingBarsHandler.EXPERIENCE_BAR_MOUNTED);
    }
}
