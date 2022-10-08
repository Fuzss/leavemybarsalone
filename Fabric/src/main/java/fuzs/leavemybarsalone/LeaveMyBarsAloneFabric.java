package fuzs.leavemybarsalone;

import fuzs.puzzleslib.core.CoreServices;
import net.fabricmc.api.ModInitializer;

public class LeaveMyBarsAloneFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CoreServices.FACTORIES.modConstructor(LeaveMyBarsAlone.MOD_ID).accept(new LeaveMyBarsAlone());
    }
}
