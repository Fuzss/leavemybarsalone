package fuzs.leavemybarsalone.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.client.handler.RidingBarsHandler;
import fuzs.leavemybarsalone.integration.appleskin.AppleSkinIntegration;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = LeaveMyBarsAlone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LeaveMyBarsAloneForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
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
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.FOOD_LEVEL_ELEMENT, LeaveMyBarsAlone.id("food_level_mounted").toString(), RidingBarsHandler.FOOD_LEVEL_MOUNTED_GUI_OVERLAY);
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, LeaveMyBarsAlone.id("experience_bar_mounted").toString(), RidingBarsHandler.EXPERIENCE_BAR_MOUNTED_GUI_OVERLAY);
    }
}
