package fuzs.improvedmounthud.config;

import fuzs.puzzleslib.config.v2.AbstractConfig;
import fuzs.puzzleslib.config.v2.annotation.Config;

public class ClientConfig extends AbstractConfig {
    @Config(description = "Render food bar when riding a mount.")
    public boolean foodBar = true;
    @Config(description = "Render experience bar when not jumping with a mount.")
    public boolean experienceBar = true;

    public ClientConfig() {
        super("");
    }
}
