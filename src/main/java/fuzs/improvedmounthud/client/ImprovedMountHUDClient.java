package fuzs.improvedmounthud.client;

import fuzs.improvedmounthud.ImprovedMountHUD;
import fuzs.improvedmounthud.client.handler.MountHUDHandler;
import fuzs.puzzleslib.PuzzlesLib;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ImprovedMountHUD.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ImprovedMountHUDClient {
    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        PuzzlesLib.setSideOnly();
        final MountHUDHandler handler = new MountHUDHandler();
        MinecraftForge.EVENT_BUS.addListener(handler::onRenderGameOverlay);
    }
}
