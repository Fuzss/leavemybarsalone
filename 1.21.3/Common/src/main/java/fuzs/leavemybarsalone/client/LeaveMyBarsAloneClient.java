package fuzs.leavemybarsalone.client;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import fuzs.puzzleslib.api.client.core.v1.ClientAbstractions;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.event.v1.gui.RenderGuiLayerEvents;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.api.event.v1.core.EventPhase;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public class LeaveMyBarsAloneClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        // this is not necessary on NeoForge as we call the vanilla methods that update this
        if (ModLoaderEnvironment.INSTANCE.getModLoader().isFabricLike()) {
            RenderGuiLayerEvents.after(RenderGuiLayerEvents.FOOD_LEVEL)
                    .register(EventPhase.FIRST, (Gui gui, GuiGraphics guiGraphics, DeltaTracker deltaTracker) -> {
                        if (gui.minecraft.getCameraEntity() instanceof Player &&
                                LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar) {
                            int maxHearts = gui.getVehicleMaxHearts(gui.getPlayerVehicleWithHealth());
                            if (maxHearts != 0) {
                                ClientAbstractions.INSTANCE.addGuiRightHeight(gui, 10);
                            }
                        }
                    });
        }
    }
}
