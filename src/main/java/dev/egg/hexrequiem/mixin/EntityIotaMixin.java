package dev.egg.hexrequiem.mixin;

import at.petrak.hexcasting.api.casting.iota.EntityIota;
import dev.egg.hexrequiem.utils.ReqiuemHelper;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityIota.class)
public class EntityIotaMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lat/petrak/hexcasting/api/casting/iota/Iota;<init>(Lat/petrak/hexcasting/api/casting/iota/IotaType;Ljava/lang/Object;)V"), index = 1)
    private static @NotNull Object hexrequiem$convertEntityToBody(@NotNull Object payload) {
        return ReqiuemHelper.getBody((Entity) payload);
    }
}
