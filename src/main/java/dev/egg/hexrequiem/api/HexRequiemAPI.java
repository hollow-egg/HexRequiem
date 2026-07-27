package dev.egg.hexrequiem.api;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface HexRequiemAPI {

    String MOD_ID = "hexrequiem";
    Logger LOGGER = LogManager.getLogger(MOD_ID);

    Supplier<HexRequiemAPI> INSTANCE = Suppliers.memoize(() -> {
        return new HexRequiemAPI() {};
    });

    static HexRequiemAPI instance() {
        return INSTANCE.get();
    }

    static Identifier modLoc(String s) {
        return new Identifier(MOD_ID, s);
    }
}
