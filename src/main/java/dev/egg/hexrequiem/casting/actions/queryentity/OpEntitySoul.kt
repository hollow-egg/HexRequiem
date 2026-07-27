package dev.egg.hexrequiem.casting.actions.queryentity

import dev.egg.hexrequiem.api.asActionResult
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import dev.egg.hexrequiem.api.BodyInterface
import dev.egg.hexrequiem.utils.ReqiuemHelper
import net.minecraft.entity.LivingEntity

object OpEntitySoul : ConstMediaAction {

    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val body = args[0] as EntityIota
        val e = body.entity

        val entity = ReqiuemHelper.getSoul(e) ?: return null.asActionResult

        env.assertEntityInRange(entity)
        return BodyInterface(entity as LivingEntity).asActionResult
    }
}