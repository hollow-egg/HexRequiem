package dev.egg.hexrequiem.casting

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import dev.egg.hexrequiem.casting.iotas.SoulIota
import net.minecraft.entity.Entity

fun List<Iota>.getSoul(idx: Int, argc: Int = 0): Entity {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is SoulIota) {
        return x.entity
    } else {
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "soul")
    }
}