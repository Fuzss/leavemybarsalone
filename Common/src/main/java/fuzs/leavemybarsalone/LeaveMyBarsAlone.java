package fuzs.leavemybarsalone;

import fuzs.leavemybarsalone.config.ClientConfig;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.ModConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaveMyBarsAlone implements ModConstructor {
    public static final String MOD_ID = "leavemybarsalone";
    public static final String MOD_NAME = "Leave My Bars Alone";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder CONFIG = CoreServices.FACTORIES.clientConfig(ClientConfig.class, () -> new ClientConfig());

    @Override
    public void onConstructMod() {
        CONFIG.bakeConfigs(MOD_ID);
    }
}
