package fuzs.leavemybarsalone.mixin.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ForgeIngameGui.class)
public interface ForgeIngameGuiAccessor {

    @Invoker(remap = false)
    void callRenderExperience(int x, PoseStack poseStack);
}
