package dev.egg.hexrequiem.casting.patterns.spells.greater

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapImmuneEntity
import at.petrak.hexcasting.api.misc.MediaConstants
import dev.egg.hexrequiem.utils.ReqiuemHelper
import net.minecraft.entity.LivingEntity

object OpRendMind : SpellAction
{
    override val argc = 1
    val cost = MediaConstants.SHARD_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val entity = args.getEntity(0, argc)

        env.assertEntityInRange(entity)

        if (!ReqiuemHelper.canRemoveSoul(entity))
            throw MishapImmuneEntity(entity)

        return SpellAction.Result(
            Spell(entity as LivingEntity),
            cost * entity.health.toLong(), // scales with the entity's health, for some semblance of balance
            listOf(ParticleSpray.burst(entity.pos, 1.0))
        )
    }

    private data class Spell(val entity: LivingEntity?) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            ReqiuemHelper.removeSoul(entity, true)
        }
    }
}