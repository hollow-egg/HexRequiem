package dev.egg.hexrequiem;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import dev.egg.hexrequiem.registry.HexRequiemPatternRegistry;
import net.minecraft.registry.Registry;

public class HexRequiemAbstractionsImpl {
    public static void registerHexcastingEntries() {
        for (var entry : HexRequiemPatternRegistry.PATTERNS) {
            Registry.register(HexActions.REGISTRY, entry.id(), new ActionRegistryEntry(entry.pattern(), entry.action()));
        }

        for (var entry : HexRequiemPatternRegistry.PER_WORLD_PATTERNS) {
            Registry.register(HexActions.REGISTRY, entry.id(), new ActionRegistryEntry(entry.pattern(), entry.action()));
        }
    }
}
