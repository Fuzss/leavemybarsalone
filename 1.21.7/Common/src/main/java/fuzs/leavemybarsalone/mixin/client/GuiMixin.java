package fuzs.leavemybarsalone.mixin.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
abstract class GuiMixin {

    @ModifyVariable(method = "nextContextualInfoState", at = @At("STORE"), ordinal = 1)
    private boolean nextContextualInfoState(boolean pickJumpInfo) {
        if (LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) {
            return pickJumpInfo && this.willPrioritizeJumpInfo();
        } else {
            return pickJumpInfo;
        }
    }

    @Shadow
    private boolean willPrioritizeJumpInfo() {
        throw new RuntimeException();
    }
}
