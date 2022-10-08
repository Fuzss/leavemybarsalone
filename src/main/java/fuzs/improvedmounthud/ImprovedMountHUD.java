package fuzs.improvedmounthud;

import fuzs.improvedmounthud.config.ClientConfig;
import fuzs.puzzleslib.config.v2.AbstractConfig;
import fuzs.puzzleslib.config.v2.ConfigHolder;
import fuzs.puzzleslib.config.v2.ConfigHolderImpl;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ImprovedMountHUD.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ImprovedMountHUD {
    public static final String MOD_ID = "improvedmounthud";
    public static final String MOD_NAME = "Improved Mount HUD";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder<ClientConfig, AbstractConfig> CONFIG = ConfigHolder.client(() -> new ClientConfig());

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ((ConfigHolderImpl<?, ?>) CONFIG).addConfigs(MOD_ID);
    }
}
