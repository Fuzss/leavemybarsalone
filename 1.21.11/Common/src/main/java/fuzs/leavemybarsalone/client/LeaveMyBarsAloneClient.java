package fuzs.leavemybarsalone.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.GuiLayersContext;
import fuzs.puzzleslib.api.client.gui.v2.ScreenHelper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public class LeaveMyBarsAloneClient implements ClientModConstructor {
    public static final Identifier FOOD_LEVEL_GUI_LAYER = LeaveMyBarsAlone.id("food_level");

    @Override
    public void onRegisterGuiLayers(GuiLayersContext context) {
        context.registerGuiLayer(FOOD_LEVEL_GUI_LAYER,
                GuiLayersContext.VEHICLE_HEALTH,
                (GuiGraphics guiGraphics, DeltaTracker deltaTracker) -> {
                    if (LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodLevel) {
                        Gui gui = Minecraft.getInstance().gui;
                        int vehicleMaxHearts = gui.getVehicleMaxHearts(gui.getPlayerVehicleWithHealth());
                        if (gui.minecraft.gameMode.canHurtPlayer() && vehicleMaxHearts > 0) {
                            Player player = gui.getCameraPlayer();
                            int posX = guiGraphics.guiWidth() / 2 + 91;
                            gui.renderFood(guiGraphics,
                                    player,
                                    guiGraphics.guiHeight()
                                            - ScreenHelper.getRightStatusBarHeight(FOOD_LEVEL_GUI_LAYER),
                                    posX);
                        }
                    }
                });
        context.addRightStatusBarHeightProvider(FOOD_LEVEL_GUI_LAYER, (Player player) -> {
            if (LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodLevel) {
                Gui gui = Minecraft.getInstance().gui;
                int vehicleMaxHearts = gui.getVehicleMaxHearts(gui.getPlayerVehicleWithHealth());
                return gui.minecraft.gameMode.canHurtPlayer() && vehicleMaxHearts > 0 ? 10 : 0;
            } else {
                return 0;
            }
        });
    }
}
