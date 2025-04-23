package fuzs.leavemybarsalone.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.GuiLayersContext;
import fuzs.puzzleslib.api.client.gui.v2.GuiHeightHelper;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;

public class LeaveMyBarsAloneClient implements ClientModConstructor {

    @Override
    public void onRegisterGuiLayers(GuiLayersContext context) {
        if (LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).experienceBar) {
            context.replaceGuiLayer(GuiLayersContext.JUMP_METER, (LayeredDraw.Layer layer) -> {
                return (GuiGraphics guiGraphics, DeltaTracker deltaTracker) -> {
                    Gui gui = Minecraft.getInstance().gui;
                    PlayerRideableJumping playerRideableJumping = gui.minecraft.player.jumpableVehicle();
                    if (playerRideableJumping != null) {
                        if (this.isExperienceBarVisible(gui)) {
                            int posX = guiGraphics.guiWidth() / 2 - 91;
                            gui.renderExperienceBar(guiGraphics, posX);
                        } else {
                            layer.render(guiGraphics, deltaTracker);
                        }
                    }
                };
            });
            context.replaceGuiLayer(GuiLayersContext.EXPERIENCE_LEVEL, (LayeredDraw.Layer layer) -> {
                return (GuiGraphics guiGraphics, DeltaTracker deltaTracker) -> {
                    Gui gui = Minecraft.getInstance().gui;
                    PlayerRideableJumping playerRideableJumping = gui.minecraft.player.jumpableVehicle();
                    if (playerRideableJumping != null && this.isExperienceBarVisible(gui)) {
                        // the vanilla method already includes the experience visibility check
                        this.renderExperienceLevel(gui, guiGraphics, deltaTracker);
                    } else {
                        layer.render(guiGraphics, deltaTracker);
                    }
                };
            });
        }
        if (LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) {
            // Fabric requires special handling, as gui layers do not respect the actual gui height, it is only updated afterward
            context.replaceGuiLayer(GuiLayersContext.VEHICLE_HEALTH, (LayeredDraw.Layer layer) -> {
                return (GuiGraphics guiGraphics, DeltaTracker deltaTracker) -> {
                    Gui gui = Minecraft.getInstance().gui;
                    int vehicleMaxHearts = gui.getVehicleMaxHearts(gui.getPlayerVehicleWithHealth());
                    if (gui.minecraft.gameMode.canHurtPlayer() && vehicleMaxHearts > 0) {
                        Player player = gui.getCameraPlayer();
                        int posX = guiGraphics.guiWidth() / 2 + 91;
                        gui.renderFood(guiGraphics,
                                player,
                                guiGraphics.guiHeight() - GuiHeightHelper.getRightHeight(gui),
                                posX);
                        GuiHeightHelper.addRightHeight(gui, 10);
                        if (ModLoaderEnvironment.INSTANCE.getModLoader().isFabricLike()) {
                            this.renderLayerWithTranslation(layer, guiGraphics, deltaTracker);
                            return;
                        }
                    }
                    layer.render(guiGraphics, deltaTracker);
                };
            });
            if (ModLoaderEnvironment.INSTANCE.getModLoader().isFabricLike()) {
                context.replaceGuiLayer(GuiLayersContext.AIR_LEVEL, (LayeredDraw.Layer layer) -> {
                    return (GuiGraphics guiGraphics, DeltaTracker deltaTracker) -> {
                        Gui gui = Minecraft.getInstance().gui;
                        int vehicleMaxHearts = gui.getVehicleMaxHearts(gui.getPlayerVehicleWithHealth());
                        if (gui.minecraft.gameMode.canHurtPlayer() && vehicleMaxHearts > 0) {
                            this.renderLayerWithTranslation(layer, guiGraphics, deltaTracker);
                        } else {
                            layer.render(guiGraphics, deltaTracker);
                        }
                    };
                });
            }
        }
    }

    private void renderLayerWithTranslation(LayeredDraw.Layer layer, GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -10.0F, 0.0F);
        layer.render(guiGraphics, deltaTracker);
        guiGraphics.pose().popPose();
    }

    /**
     * @see Gui#isExperienceBarVisible()
     */
    private boolean isExperienceBarVisible(Gui gui) {
        return gui.minecraft.gameMode.hasExperience() &&
                (gui.minecraft.player.jumpableVehicle() == null || gui.minecraft.player.getJumpRidingScale() == 0.0F);
    }

    /**
     * @see Gui#renderExperienceLevel(GuiGraphics, DeltaTracker)
     */
    private void renderExperienceLevel(Gui gui, GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        int i = gui.minecraft.player.experienceLevel;
        if (i > 0) {
            Profiler.get().push("expLevel");
            String string = i + "";
            int j = (guiGraphics.guiWidth() - gui.getFont().width(string)) / 2;
            int k = guiGraphics.guiHeight() - 31 - 4;
            guiGraphics.drawString(gui.getFont(), string, j + 1, k, 0, false);
            guiGraphics.drawString(gui.getFont(), string, j - 1, k, 0, false);
            guiGraphics.drawString(gui.getFont(), string, j, k + 1, 0, false);
            guiGraphics.drawString(gui.getFont(), string, j, k - 1, 0, false);
            guiGraphics.drawString(gui.getFont(), string, j, k, 0X80FF20, false);
            Profiler.get().pop();
        }
    }
}
