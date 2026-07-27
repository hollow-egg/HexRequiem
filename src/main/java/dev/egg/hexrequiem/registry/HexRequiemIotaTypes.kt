package dev.egg.hexrequiem.registry

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.xplat.IXplatAbstractions
import dev.egg.hexrequiem.api.HexRequiemAPI.modLoc
import dev.egg.hexrequiem.casting.iotas.SoulIota
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.jetbrains.annotations.ApiStatus
import java.util.function.BiConsumer

object HexRequiemIotaTypes {
    @JvmStatic
    @ApiStatus.Internal
    fun registerTypes(r: BiConsumer<IotaType<*>, Identifier>) {
        for ((key, value) in TYPES) {
            r.accept(value, key)
        }
    }

    private val TYPES: MutableMap<Identifier, IotaType<*>> = LinkedHashMap()

    @JvmField
    val SOUL: IotaType<SoulIota> = type("soul", SoulIota.TYPE)

    private fun <U : Iota, T : IotaType<U>> type(name: String, type: T): T {
        val old = TYPES.put(modLoc(name), type)
        require(old == null) { "Typo? Duplicate id $name" }
        return type
    }

    fun init() {
        registerTypes(bind(IXplatAbstractions.INSTANCE.iotaTypeRegistry))
    }

    private fun <T> bind(registry: Registry<in T>): BiConsumer<T, Identifier> =
        BiConsumer<T, Identifier> { t, id -> Registry.register(registry, id, t) }
}