package fuzs.leavemybarsalone.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
abstract class GuiMixin extends GuiComponent {
    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyVariable(method = "renderPlayerHealth", at = @At("STORE"), ordinal = 13, slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=health"), to = @At(value = "CONSTANT", args = "stringValue=food")))
    private int leavemybarsalone$renderPlayerHealth$1(int vehicleMaxHearts) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) return vehicleMaxHearts;
        return 0;
    }

    @ModifyVariable(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getMaxAirSupply()I"), ordinal = 13)
    private int leavemybarsalone$renderPlayerHealth$2(int vehicleMaxHearts) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) return vehicleMaxHearts;
        LivingEntity livingentity = this.getPlayerVehicleWithHealth();
        return this.getVehicleMaxHearts(livingentity);
    }

    @Shadow
    private LivingEntity getPlayerVehicleWithHealth() {
        throw new IllegalStateException();
    }

    @Shadow
    private int getVehicleMaxHearts(LivingEntity mountEntity) {
        throw new IllegalStateException();
    }

    @ModifyVariable(method = "renderVehicleHealth", at = @At("STORE"), ordinal = 2)
    private int leavemybarsalone$renderVehicleHealth(int leftHeight) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) return leftHeight;
        return leftHeight - 10;
    }

    @Inject(method = "renderJumpMeter", at = @At("HEAD"), cancellable = true)
    public void leavemybarsalone$renderJumpMeter(PoseStack poseStack, int x, CallbackInfo callback) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
        if (this.minecraft.gameMode.hasExperience() && this.minecraft.player.getJumpRidingScale() == 0.0F) {
            this.renderExperienceBar(poseStack, x);
            callback.cancel();
        }
    }

    @Shadow
    public abstract void renderExperienceBar(PoseStack poseStack, int xPos);
}
