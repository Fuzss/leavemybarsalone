package fuzs.leavemybarsalone.neoforge;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(LeaveMyBarsAlone.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LeaveMyBarsAloneNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(LeaveMyBarsAlone.MOD_ID, LeaveMyBarsAlone::new);
    }
}
