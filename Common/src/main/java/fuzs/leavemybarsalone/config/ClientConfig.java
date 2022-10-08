package fuzs.leavemybarsalone.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;

public class ClientConfig implements ConfigCore {
    @Config(description = "Render food bar when riding a mount.")
    public boolean foodBar = true;
    @Config(description = "Render experience bar when not jumping with a mount.")
    public boolean experienceBar = true;
    @Config(category = "integration", description = {"Enable compat with AppleSkin mod when it is installed.", "Should only really be disabled when breaking changes have been made to Apple Skin."})
    public boolean appleSkin = true;
}
