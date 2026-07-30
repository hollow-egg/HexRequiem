package dev.egg.hexrequiem.casting.patterns.spells.greater

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import baritone.api.fakeplayer.FakeServerPlayerEntity
import dev.egg.hexrequiem.casting.iotas.SoulIota
import dev.egg.hexrequiem.utils.ReqiuemHelper
import ladysnake.requiem.api.v1.possession.PossessionComponent
import ladysnake.requiem.api.v1.remnant.RemnantComponent
import ladysnake.requiem.common.entity.PlayerShellEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.server.network.ServerPlayerEntity


object OpConjoinSpirit : SpellAction
{
    override val argc = 2
    val cost = MediaConstants.SHARD_UNIT

    private fun canPossess(entity: Entity): Boolean {
        if (entity !is LivingEntity // if body is not a valid target
            || ReqiuemHelper.getSoul(entity) != null) // if body already has a soul
            return false
        return true
    }

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val soulArg = args[0] as? SoulIota?: return SpellAction.Result(Spell(null, null), 0L, emptyList())
        val entityArg = args[1] as? EntityIota?: return SpellAction.Result(Spell(null, null), 0L, emptyList())

        env.assertEntityInRange(entityArg.entity)

        // only supporting transferring player souls atm, planning on transferring mob souls later
        if (soulArg.entity !is ServerPlayerEntity || !canPossess(entityArg.entity))
            return SpellAction.Result(Spell(
                null,
                null,
            ), 0L, emptyList())

        return SpellAction.Result(
            Spell(soulArg.entity as ServerPlayerEntity, entityArg.entity as LivingEntity),
            cost,
            listOf(ParticleSpray.burst(entityArg.entity.pos, 1.0))
        )
    }

    private data class Spell(val soul: ServerPlayerEntity?, val body: LivingEntity?) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            soul ?: return
            body ?: return
            val success: Boolean
            if (body is MobEntity)
                success = PossessionComponent.get(soul).startPossessing(body)
            else if (body is FakeServerPlayerEntity)
                success = RemnantComponent.get(soul).merge(body);
        }
    }
}