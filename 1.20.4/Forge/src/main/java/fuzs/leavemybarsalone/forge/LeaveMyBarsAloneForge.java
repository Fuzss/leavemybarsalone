package fuzs.leavemybarsalone.forge;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(LeaveMyBarsAlone.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LeaveMyBarsAloneForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(LeaveMyBarsAlone.MOD_ID, LeaveMyBarsAlone::new);
    }
}
