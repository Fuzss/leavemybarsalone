package fuzs.leavemybarsalone.fabric.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.client.LeaveMyBarsAloneClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class LeaveMyBarsAloneFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(LeaveMyBarsAlone.MOD_ID, LeaveMyBarsAloneClient::new);
    }
}
