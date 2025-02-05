package fuzs.leavemybarsalone.neoforge;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.neoforged.fml.common.Mod;

@Mod(LeaveMyBarsAlone.MOD_ID)
public class LeaveMyBarsAloneNeoForge {

    public LeaveMyBarsAloneNeoForge() {
        ModConstructor.construct(LeaveMyBarsAlone.MOD_ID, LeaveMyBarsAlone::new);
    }
}
