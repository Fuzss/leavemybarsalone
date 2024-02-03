package fuzs.leavemybarsalone.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.api.client.SharedGuiHeights;
import fuzs.leavemybarsalone.config.ClientConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
abstract class GuiFabricMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"))
    private void renderPlayerHealth$0(PoseStack poseStack, CallbackInfo callback) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            FabricLoader.getInstance().getObjectShare().put(SharedGuiHeights.OBJECT_SHARE_RIGHT_HEIGHT_KEY, new MutableInt(39 + this.overflowingBars$getAdditionalRightHeight(player)));
        }
    }

    @Shadow
    private Player getCameraPlayer() {
        throw new IllegalStateException();
    }

    @Unique
    private int overflowingBars$getAdditionalRightHeight(Player player) {
        LivingEntity livingEntity = this.getPlayerVehicleWithHealth();
        int x = this.getVehicleMaxHearts(livingEntity);
        int aa = this.getVisibleVehicleHeartRows(x);
        if (!this.minecraft.gameMode.canHurtPlayer()) return aa * 10;
        if (LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) aa++;
        int rightHeight = Math.max(1, aa) * 10;
        int y = player.getMaxAirSupply();
        int z = Math.min(player.getAirSupply(), y);
        if (player.isEyeInFluid(FluidTags.WATER) || z < y) {
            rightHeight += 10;
        }
        return rightHeight;
    }

    @Shadow
    private LivingEntity getPlayerVehicleWithHealth() {
        throw new IllegalStateException();
    }

    @ModifyVariable(method = "renderPlayerHealth", at = @At("STORE"), ordinal = 13, slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=health"), to = @At(value = "CONSTANT", args = "stringValue=food")))
    private int renderPlayerHealth$1(int vehicleMaxHearts) {
        return !LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar ? vehicleMaxHearts : 0;
    }

    @ModifyVariable(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getMaxAirSupply()I"), ordinal = 13)
    private int renderPlayerHealth$2(int vehicleMaxHearts) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) return vehicleMaxHearts;
        LivingEntity livingentity = this.getPlayerVehicleWithHealth();
        return this.getVehicleMaxHearts(livingentity);
    }

    @Shadow
    private int getVehicleMaxHearts(LivingEntity mountEntity) {
        throw new IllegalStateException();
    }

    @Shadow
    private int getVisibleVehicleHeartRows(int mountHealth) {
        throw new IllegalStateException();
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
    public void renderJumpMeter(PoseStack poseStack, int x, CallbackInfo callback) {
        if (!LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) return;
        if (this.minecraft.gameMode.hasExperience() && this.minecraft.player.getJumpRidingScale() == 0.0F) {
            this.renderExperienceBar(poseStack, x);
            callback.cancel();
        }
    }

    @Shadow
    public abstract void renderExperienceBar(PoseStack guiGraphics, int xPos);
}
