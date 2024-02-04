package fuzs.leavemybarsalone.forge.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.client.LeaveMyBarsAloneClient;
import fuzs.leavemybarsalone.forge.client.handler.ForgeRidingBarsHandler;
import fuzs.leavemybarsalone.forge.integration.appleskin.AppleSkinIntegration;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = LeaveMyBarsAlone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LeaveMyBarsAloneForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(LeaveMyBarsAlone.MOD_ID, LeaveMyBarsAloneClient::new);
        registerHandlers();
        registerIntegration();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener(ForgeRidingBarsHandler::onRenderGuiOverlayEvent$Pre);
    }

    private static void registerIntegration() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("appleskin")) {
            AppleSkinIntegration.init();
        }
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(final RegisterGuiOverlaysEvent evt) {
        evt.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(),
                "food_level_mounted",
                ForgeRidingBarsHandler.FOOD_LEVEL_MOUNTED_GUI_OVERLAY
        );
        evt.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(),
                "experience_bar_mounted",
                ForgeRidingBarsHandler.EXPERIENCE_BAR_MOUNTED_GUI_OVERLAY
        );
    }
}
