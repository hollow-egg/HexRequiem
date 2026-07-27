package dev.egg.hexrequiem;

import dev.egg.hexrequiem.registry.HexRequiemIotaTypes;
import dev.egg.hexrequiem.registry.HexRequiemPatternRegistry;
import net.fabricmc.api.ModInitializer;

public class HexRequiem implements ModInitializer {
    @Override
    public void onInitialize() {
        HexRequiemIotaTypes.INSTANCE.init();
        HexRequiemPatternRegistry.init();
        HexRequiemAbstractionsImpl.registerHexcastingEntries();
    }
}
