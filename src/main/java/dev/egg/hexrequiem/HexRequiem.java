package dev.egg.hexrequiem;

import at.petrak.hexcasting.api.HexAPI;
import dev.egg.hexrequiem.registry.HexRequiemIotaTypes;
import dev.egg.hexrequiem.registry.HexRequiemPatternRegistry;
import dev.egg.hexrequiem.utils.ReqiuemHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.MemoryModuleType;

public class HexRequiem implements ModInitializer {
    @Override
    public void onInitialize() {
        HexRequiemIotaTypes.INSTANCE.init();
        HexRequiemPatternRegistry.init();
        HexRequiemAbstractionsImpl.registerHexcastingEntries();

        HexAPI.instance().registerCustomBrainsweepingBehavior(EntityType.ALLAY, ReqiuemHelper::destroySoul);
        HexAPI.instance().registerCustomBrainsweepingBehavior(EntityType.VILLAGER, ReqiuemHelper::destroySoul);
    }
}
