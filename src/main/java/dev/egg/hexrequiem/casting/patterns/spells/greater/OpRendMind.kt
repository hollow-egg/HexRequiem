package dev.egg.hexrequiem.casting.patterns.spells.greater

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import dev.egg.hexrequiem.utils.ReqiuemHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.registry.tag.ItemTags

object OpRendMind : SpellAction
{
    override val argc = 1
    val cost = MediaConstants.SHARD_UNIT

    private fun canRemoveSoul(entity: Entity): Boolean {
        return entity is LivingEntity && !entity.getEquippedStack(EquipmentSlot.HEAD).isIn(ItemTags.TRIMMABLE_ARMOR)
    }

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val targetArg = args[0] as? EntityIota?: return SpellAction.Result(Spell(null), 0L, emptyList())

        val entity = targetArg.entity

        env.assertEntityInRange(entity)

        if (!canRemoveSoul(entity))
            return SpellAction.Result(Spell(null), 0L, emptyList())

        return SpellAction.Result(
            Spell(entity as LivingEntity),
            cost * entity.health.toLong(), // scales with the entity's health, for some semblance of balance
            listOf(ParticleSpray.burst(entity.pos, 1.0))
        )
    }

    private data class Spell(val entity: LivingEntity?) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            entity ?: return
            ReqiuemHelper.removeSoul(entity, true)
        }
    }
}