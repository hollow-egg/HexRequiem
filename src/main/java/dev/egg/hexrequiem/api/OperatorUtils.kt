package dev.egg.hexrequiem.api

import at.petrak.hexcasting.api.casting.iota.EntityIota
import dev.egg.hexrequiem.casting.iotas.SoulIota
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity

//simply encodes the entities in a different structure so asActionResult can differentiate them
class BodyInterface(val entity: LivingEntity) {}
class SoulInterface(val entity: Entity) {}

inline val BodyInterface.asActionResult get() = listOf(SoulIota(this.entity))
inline val SoulInterface.asActionResult get() = listOf(EntityIota(this.entity))