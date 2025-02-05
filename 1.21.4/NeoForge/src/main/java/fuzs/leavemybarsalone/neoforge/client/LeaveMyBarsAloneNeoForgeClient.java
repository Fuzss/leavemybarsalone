package fuzs.leavemybarsalone.neoforge.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.client.LeaveMyBarsAloneClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = LeaveMyBarsAlone.MOD_ID, dist = Dist.CLIENT)
public class LeaveMyBarsAloneNeoForgeClient {

    public LeaveMyBarsAloneNeoForgeClient() {
        ClientModConstructor.construct(LeaveMyBarsAlone.MOD_ID, LeaveMyBarsAloneClient::new);
    }
}
