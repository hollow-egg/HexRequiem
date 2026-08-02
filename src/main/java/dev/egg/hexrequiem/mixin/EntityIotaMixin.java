package dev.egg.hexrequiem.mixin;

import at.petrak.hexcasting.api.casting.iota.EntityIota;
import com.mojang.authlib.GameProfile;
import dev.egg.hexrequiem.utils.ReqiuemHelper;
import ladysnake.requiem.common.entity.PlayerShellEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

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

    //this fixes entity rendering in iotas when playing on a server
    @Redirect(method = "display", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;copy()Lnet/minecraft/text/MutableText;"))
    private static MutableText hexrequiem$copy(Text instance){
        return instance.copyContentOnly();
    }
}
