package fuzs.leavemybarsalone;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class LeaveMyBarsAloneFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(LeaveMyBarsAlone.MOD_ID, LeaveMyBarsAlone::new);
    }
}
