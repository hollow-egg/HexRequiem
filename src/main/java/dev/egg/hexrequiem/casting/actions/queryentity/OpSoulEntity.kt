package dev.egg.hexrequiem.casting.actions.queryentity

import dev.egg.hexrequiem.api.asActionResult
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import dev.egg.hexrequiem.api.SoulInterface
import dev.egg.hexrequiem.casting.iotas.SoulIota
import dev.egg.hexrequiem.utils.ReqiuemHelper

object OpSoulEntity : ConstMediaAction {

    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val soul = args[0] as SoulIota
        val e = soul.entity

        val entity = ReqiuemHelper.getBody(e) ?: return null.asActionResult

        env.assertEntityInRange(entity)
        return SoulInterface(entity).asActionResult
    }
}