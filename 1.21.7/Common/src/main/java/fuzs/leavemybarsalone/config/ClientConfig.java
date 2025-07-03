package fuzs.leavemybarsalone.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

public class ClientConfig implements ConfigCore {
    @Config(description = "Render food bar when riding a mount.", gameRestart = true)
    public boolean foodBar = true;
    @Config(description = "Render experience bar when not jumping with a mount.", gameRestart = true)
    public boolean experienceBar = true;
}
