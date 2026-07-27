package dev.egg.hexrequiem.registry;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;

import dev.egg.hexrequiem.casting.actions.queryentity.OpEntitySoul;
import dev.egg.hexrequiem.casting.actions.queryentity.OpSoulEntity;
import dev.egg.hexrequiem.casting.patterns.spells.greater.OpConjoinSpirit;
import dev.egg.hexrequiem.casting.patterns.spells.greater.OpRendMind;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static dev.egg.hexrequiem.api.HexRequiemAPI.modLoc;

public class HexRequiemPatternRegistry {
    public record PatternRegistration(HexPattern pattern, Identifier id, Action action) {}

    public static List<PatternRegistration> PATTERNS = new ArrayList<>();
    public static List<PatternRegistration> PER_WORLD_PATTERNS = new ArrayList<>();

    // Spirit's Purification
    public static HexPattern BODY_SOUL = register(HexPattern.fromAngles("qaqdqaqdqaq", HexDir.NORTH_WEST), "body_soul", OpEntitySoul.INSTANCE);
    public static HexPattern SOUL_BODY = register(HexPattern.fromAngles("edeaedeaede", HexDir.NORTH_EAST), "soul_body", OpSoulEntity.INSTANCE);

    public static HexPattern REND_MIND = registerPerWorld(HexPattern.fromAngles("qqqaqqqwqaqqqqqeqqqaqqqwq", HexDir.WEST), "rend_mind", OpRendMind.INSTANCE);
    public static HexPattern CONJOIN_SPIRIT = registerPerWorld(HexPattern.fromAngles("qwqeqqqeqwqwqadadaqadad", HexDir.SOUTH_WEST), "conjoin_spirit", OpConjoinSpirit.INSTANCE);

    public static void init() {
    }

    private static HexPattern register(HexPattern pattern, String name, Action action) {
        PATTERNS.add(new PatternRegistration(pattern, modLoc(name), action));
        return pattern;
    }

    private static HexPattern registerPerWorld(HexPattern pattern, String name, Action action) {
        PER_WORLD_PATTERNS.add(new PatternRegistration(pattern, modLoc(name), action));
        return pattern;
    }
}
