package fuzs.leavemybarsalone.fabric.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Gui.class)
abstract class GuiFabricMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyVariable(method = "renderHotbarAndDecorations", at = @At("STORE"))
    private PlayerRideableJumping renderHotbarAndDecorations(@Nullable PlayerRideableJumping playerRideableJumping) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) {
            return playerRideableJumping;
        } else if (playerRideableJumping != null && this.minecraft.player.getJumpRidingScale() == 0.0F &&
                this.minecraft.gameMode.hasExperience()) {
            return null;
        } else {
            return playerRideableJumping;
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

    @ModifyExpressionValue(
            method = "renderPlayerHealth", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;getVehicleMaxHearts(Lnet/minecraft/world/entity/LivingEntity;)I"
    )
    )
    private int renderPlayerHealth(int maxHearts) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) {
            return maxHearts;
        } else {
            return 0;
        }
    }

    @ModifyArg(
            method = "getAirBubbleYLine",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I")
    )
    private int getAirBubbleYLine(int vehicleHealth) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) {
            return vehicleHealth;
        } else {
            vehicleHealth = this.getVehicleMaxHearts(this.getPlayerVehicleWithHealth());
            return vehicleHealth != 0 ? vehicleHealth + 1 : vehicleHealth;
        }
    }

    @ModifyExpressionValue(
            method = "renderVehicleHealth",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;guiHeight()I")
    )
    private int renderVehicleHealth(int guiHeight) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar || !this.minecraft.gameMode.canHurtPlayer()) {
            return guiHeight;
        } else {
            return guiHeight - 10;
        }
    }

    @Shadow
    private LivingEntity getPlayerVehicleWithHealth() {
        throw new RuntimeException();
    }

    @Shadow
    private int getVehicleMaxHearts(LivingEntity mountEntity) {
        throw new RuntimeException();
    }
}
