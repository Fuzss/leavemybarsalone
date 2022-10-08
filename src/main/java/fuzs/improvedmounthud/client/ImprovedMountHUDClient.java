package fuzs.improvedmounthud.client;

import fuzs.improvedmounthud.ImprovedMountHUD;
import fuzs.improvedmounthud.client.handler.MountHUDHandler;
import fuzs.improvedmounthud.compat.appleskin.AppleSkinCompat;
import fuzs.improvedmounthud.compat.appleskin.HUDOverlayHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(modid = ImprovedMountHUD.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ImprovedMountHUDClient {
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        if (ImprovedMountHUD.CONFIG.client().compat.appleSkin && ModList.get().isLoaded("appleskin")) {
            final HUDOverlayHandler handler = new HUDOverlayHandler();
            MinecraftForge.EVENT_BUS.addListener(handler::onClientTick);
        }
    }

    @SubscribeEvent
    public static void onLoadComplete(final FMLLoadCompleteEvent evt) {
        if (ImprovedMountHUD.CONFIG.client().compat.appleSkin && ModList.get().isLoaded("appleskin")) {
            AppleSkinCompat.init();
        }
        // call as late as possible so nothing slips in-between
        MountHUDHandler.init();
    }
}
