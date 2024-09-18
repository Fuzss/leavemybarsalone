package fuzs.leavemybarsalone.fabric.mixin.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import fuzs.leavemybarsalone.fabric.api.v1.client.SharedGuiHeights;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Gui.class)
abstract class GuiFabricMixin {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private boolean leavemybarsalone$returnZeroVehicleMaxHearts;

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"))
    private void renderPlayerHealth$0(GuiGraphics guiGraphics, CallbackInfo callback) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            MutableInt value = new MutableInt(39 + this.overflowingBars$getAdditionalRightHeight(player));
            FabricLoader.getInstance().getObjectShare().put(SharedGuiHeights.OBJECT_SHARE_RIGHT_HEIGHT_KEY, value);
        }
        this.leavemybarsalone$returnZeroVehicleMaxHearts = true;
    }

    @Shadow
    private Player getCameraPlayer() {
        throw new IllegalStateException();
    }

    @Unique
    private int overflowingBars$getAdditionalRightHeight(Player player) {
        LivingEntity vehicle = this.getPlayerVehicleWithHealth();
        int vehicleMaxHearts = this.getVehicleMaxHearts(vehicle);
        int vehicleHeartRows = this.getVisibleVehicleHeartRows(vehicleMaxHearts);
        if (!this.minecraft.gameMode.canHurtPlayer()) return vehicleHeartRows * 10;
        if (LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) vehicleHeartRows++;
        int rightHeight = Math.max(1, vehicleHeartRows) * 10;
        int maxAirSupply = player.getMaxAirSupply();
        int currentAirSupply = Math.min(player.getAirSupply(), maxAirSupply);
        if (player.isEyeInFluid(FluidTags.WATER) || currentAirSupply < maxAirSupply) {
            rightHeight += 10;
        }
        return rightHeight;
    }

    @Shadow
    private LivingEntity getPlayerVehicleWithHealth() {
        throw new RuntimeException();
    }

    @Shadow
    private int getVisibleVehicleHeartRows(int mountHealth) {
        throw new RuntimeException();
    }

    @Inject(method = "getVehicleMaxHearts", at = @At("HEAD"), cancellable = true)
    private void getVehicleMaxHearts(@Nullable LivingEntity vehicle, CallbackInfoReturnable<Integer> callback) {
        if (this.leavemybarsalone$returnZeroVehicleMaxHearts && LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) {
            callback.setReturnValue(0);
        }
    }

    @ModifyVariable(method = "getVisibleVehicleHeartRows", at = @At("HEAD"), argsOnly = true)
    private int getVisibleVehicleHeartRows$0(int vehicleHealth) {
        return vehicleHealth == 0 ? this.getVehicleMaxHearts(this.getPlayerVehicleWithHealth()) : vehicleHealth;
    }

    @Shadow
    private int getVehicleMaxHearts(LivingEntity mountEntity) {
        throw new RuntimeException();
    }

    @Inject(method = "renderVehicleHealth", at = @At("HEAD"))
    private void renderVehicleHealth(GuiGraphics guiGraphics, CallbackInfo callback) {
        this.leavemybarsalone$returnZeroVehicleMaxHearts = false;
    }

    @ModifyVariable(method = "renderVehicleHealth", at = @At("STORE"), ordinal = 2)
    private int renderVehicleHealth(int leftHeight) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) return leftHeight;
        if (this.minecraft.gameMode.canHurtPlayer()) {
            return leftHeight - 10;
        } else {
            return leftHeight;
        }
    }

    @Inject(method = "renderJumpMeter", at = @At("HEAD"), cancellable = true)
    public void renderJumpMeter(PlayerRideableJumping rideable, GuiGraphics guiGraphics, int x, CallbackInfo callback) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
        if (this.minecraft.gameMode.hasExperience() && this.minecraft.player.getJumpRidingScale() == 0.0F && rideable.getJumpCooldown() == 0.0F) {
            this.renderExperienceBar(guiGraphics, x);
            callback.cancel();
        }
    }

    @Shadow
    public abstract void renderExperienceBar(GuiGraphics guiGraphics, int xPos);
}
