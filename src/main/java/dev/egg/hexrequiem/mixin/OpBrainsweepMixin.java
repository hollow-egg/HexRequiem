package dev.egg.hexrequiem.mixin;

import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadBrainsweep;
import at.petrak.hexcasting.common.casting.actions.spells.great.OpBrainsweep;
import com.llamalad7.mixinextras.sugar.Local;
import dev.egg.hexrequiem.utils.ReqiuemHelper;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(OpBrainsweep.class)
public class OpBrainsweepMixin {
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lat/petrak/hexcasting/xplat/IXplatAbstractions;isBrainswept(Lnet/minecraft/entity/mob/MobEntity;)Z"))
    private void hexrequiem$checkForSoul(List<? extends Iota> args, CastingEnvironment env, CallbackInfoReturnable<SpellAction.Result> cir, @Local(name = "sacrifice") MobEntity sacrifice, @Local(name = "pos") BlockPos pos) {
        if (ReqiuemHelper.getSoul(sacrifice) == null)
            throw new MishapBadBrainsweep(sacrifice, pos);
    }
}
