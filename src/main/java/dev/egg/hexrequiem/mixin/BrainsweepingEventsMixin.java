package dev.egg.hexrequiem.mixin;

import at.petrak.hexcasting.common.misc.BrainsweepingEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrainsweepingEvents.class)
public class BrainsweepingEventsMixin {
    @Inject(method = "interactWithBrainswept", at = @At("HEAD"), cancellable = true)
    private static void hexrequiem$allowInteraction(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult, CallbackInfoReturnable<ActionResult> cir){
        cir.setReturnValue(ActionResult.PASS);
        cir.cancel();
    }
}
