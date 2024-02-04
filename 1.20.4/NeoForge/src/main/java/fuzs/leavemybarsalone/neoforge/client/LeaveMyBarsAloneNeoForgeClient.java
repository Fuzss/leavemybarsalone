package fuzs.leavemybarsalone.neoforge.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.client.LeaveMyBarsAloneClient;
import fuzs.leavemybarsalone.neoforge.client.handler.NeoForgeRidingBarsHandler;
import fuzs.leavemybarsalone.neoforge.integration.appleskin.AppleSkinIntegration;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;
import net.neoforged.neoforge.common.NeoForge;

@Mod.EventBusSubscriber(modid = LeaveMyBarsAlone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LeaveMyBarsAloneNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(LeaveMyBarsAlone.MOD_ID, LeaveMyBarsAloneClient::new);
        registerHandlers();
        registerIntegration();
    }

    private static void registerHandlers() {
        NeoForge.EVENT_BUS.addListener(NeoForgeRidingBarsHandler::onRenderGuiOverlayEvent$Pre);
    }

    private static void registerIntegration() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("appleskin")) {
            AppleSkinIntegration.init();
        }
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(final RegisterGuiOverlaysEvent evt) {
        evt.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(),
                LeaveMyBarsAlone.id("food_level_mounted"),
                NeoForgeRidingBarsHandler.FOOD_LEVEL_MOUNTED_GUI_OVERLAY
        );
        evt.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(),
                LeaveMyBarsAlone.id("experience_bar_mounted"),
                NeoForgeRidingBarsHandler.EXPERIENCE_BAR_MOUNTED_GUI_OVERLAY
        );
    }
}
