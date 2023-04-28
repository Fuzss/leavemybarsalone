package fuzs.leavemybarsalone.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;

public class ClientConfig extends AbstractConfig {
    @Config(description = "Render food bar when riding a mount.")
    public boolean foodBar = true;
    @Config(description = "Render experience bar when not jumping with a mount.")
    public boolean experienceBar = true;
    @Config
    public CompatConfig compat = new CompatConfig();

    public ClientConfig() {
        super("");
    }

    public static class CompatConfig extends AbstractConfig {
        @Config(description = {"Enable compat with AppleSkin mod when it is installed.", "Should only really be disabled when breaking changes have been made to Apple Skin."})
        public boolean appleSkin = true;

        public CompatConfig() {
            super("compat");
        }
    }
}
