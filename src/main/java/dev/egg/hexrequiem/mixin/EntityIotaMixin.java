package dev.egg.hexrequiem.mixin;

import at.petrak.hexcasting.api.casting.iota.EntityIota;
import com.mojang.authlib.GameProfile;
import dev.egg.hexrequiem.utils.ReqiuemHelper;
import ladysnake.requiem.common.entity.PlayerShellEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityIota.class)
public class EntityIotaMixin {
    //forces any entity iota to fetch the body
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lat/petrak/hexcasting/api/casting/iota/Iota;<init>(Lat/petrak/hexcasting/api/casting/iota/IotaType;Ljava/lang/Object;)V"), index = 1)
    private static @NotNull Object hexrequiem$convertEntityToBody(@NotNull Object payload) {
        return ReqiuemHelper.getBody((Entity) payload);
    }

    //fixes the displayed face in the entity iota
    @Redirect(method = "getEntityNameWithInline", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getGameProfile()Lcom/mojang/authlib/GameProfile;"))
    private static GameProfile getGameProfile(PlayerEntity playerEntity) {
        if (playerEntity instanceof PlayerShellEntity shell)
            return shell.getDisplayProfile();
        return playerEntity.getGameProfile();
    }

    //fixes display of entity iotas on servers (for some reason). This is probably a bug with inline
    //tricks the game into trying to render a horse, failing, then making the spacer character less invisible
    //this attempt probably caches the entity renderers somehow, but im not 100% sure why this works tbh
    //if someone else can find the error in inline please tell me!!!
    @ModifyConstant(method = "getEntityNameWithInline", constant = @Constant(stringValue = ": "))
    private static @NotNull String hexrequiem$getEntityNameWithInline(@NotNull String string) {
        return ":<shadow a=0>§8[entity:horse]</shadow>";
    }
}
