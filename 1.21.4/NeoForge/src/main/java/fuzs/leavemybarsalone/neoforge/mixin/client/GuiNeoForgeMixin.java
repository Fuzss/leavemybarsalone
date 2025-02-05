package fuzs.leavemybarsalone.neoforge.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Gui.class)
abstract class GuiNeoForgeMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "maybeRenderJumpMeter", at = @At("HEAD"), cancellable = true, remap = false)
    private void maybeRenderJumpMeter(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo callback) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
        if (this.minecraft.player.getJumpRidingScale() == 0.0F && this.minecraft.gameMode.hasExperience()) {
            callback.cancel();
        }
    }

    @Inject(method = "maybeRenderExperienceBar", at = @At("TAIL"), remap = false)
    private void maybeRenderExperienceBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo callback, @Local int posX) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
        if (this.minecraft.player.jumpableVehicle() != null && this.minecraft.player.getJumpRidingScale() == 0.0F &&
                this.isExperienceBarVisible()) {
            this.renderExperienceBar(guiGraphics, posX);
        }
    }

    @Inject(method = "isExperienceBarVisible", at = @At("HEAD"), cancellable = true)
    private void isExperienceBarVisible(CallbackInfoReturnable<Boolean> callback) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
        if (this.minecraft.player.jumpableVehicle() != null && this.minecraft.player.getJumpRidingScale() == 0.0F &&
                this.minecraft.gameMode.hasExperience()) {
            callback.setReturnValue(true);
        }
    }

    @ModifyVariable(method = "renderFoodLevel", at = @At("STORE"), remap = false)
    private int renderFoodLevel(int maxHearts) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) {
            return maxHearts;
        } else {
            return 0;
        }
    }

    @Shadow
    private boolean isExperienceBarVisible() {
        throw new RuntimeException();
    }

    @Shadow
    private void renderExperienceBar(GuiGraphics guiGraphics, int x) {
        throw new RuntimeException();
    }
}
