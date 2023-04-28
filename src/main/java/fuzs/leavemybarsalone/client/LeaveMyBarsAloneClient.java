package fuzs.leavemybarsalone.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.client.handler.MountHUDHandler;
import fuzs.leavemybarsalone.compat.appleskin.AppleSkinCompat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(modid = LeaveMyBarsAlone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LeaveMyBarsAloneClient {

    @SubscribeEvent
    public static void onLoadComplete(final FMLLoadCompleteEvent evt) {
        if (LeaveMyBarsAlone.CONFIG.client().compat.appleSkin && ModList.get().isLoaded("appleskin")) {
            AppleSkinCompat.init();
        }
        // call as late as possible so nothing slips in-between
        MountHUDHandler.init();
    }
}
