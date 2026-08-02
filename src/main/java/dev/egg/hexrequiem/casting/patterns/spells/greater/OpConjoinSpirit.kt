package dev.egg.hexrequiem.casting.patterns.spells.greater

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapImmuneEntity
import at.petrak.hexcasting.api.misc.MediaConstants
import dev.egg.hexrequiem.casting.getSoul
import dev.egg.hexrequiem.utils.ReqiuemHelper
import net.minecraft.entity.LivingEntity
import net.minecraft.server.network.ServerPlayerEntity


object OpConjoinSpirit : SpellAction
{
    override val argc = 2
    val cost = MediaConstants.SHARD_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val soul = args.getSoul(0, argc)
        val entity = args.getEntity(1, argc)

        //very intentionally not checking for soul's position. Allows for a sort of "teleportation" if you leave a body to possess somewhere
        env.assertEntityInRange(entity)

        // only supporting transferring player souls atm, planning on transferring mob souls later
        if (soul !is ServerPlayerEntity)
            throw MishapImmuneEntity(soul)

        if (!ReqiuemHelper.canPossess(entity))
            throw MishapImmuneEntity(entity)

        return SpellAction.Result(
            Spell(soul, entity as LivingEntity),
            cost,
            listOf(ParticleSpray.burst(entity.pos, 1.0))
        )
    }

    private data class Spell(val soul: LivingEntity?, val body: LivingEntity?) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            ReqiuemHelper.placeSoulInBody(soul, body)
        }
    }
}