package dev.egg.hexrequiem.utils;

import baritone.api.fakeplayer.FakeServerPlayerEntity;
import ladysnake.requiem.api.v1.internal.ProtoPossessable;
import ladysnake.requiem.api.v1.possession.PossessionComponent;
import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import ladysnake.requiem.core.entity.SoulHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.mojang.text2speech.Narrator.LOGGER;
import static ladysnake.requiem.api.v1.possession.PossessionComponent.getHost;

public class ReqiuemHelper {

    public static Entity getSoul(Entity entity) {
        ProtoPossessable possessable = (ProtoPossessable) entity;

        if (entity instanceof FakeServerPlayerEntity)
            return null;

        if (possessable.isBeingPossessed())
            return possessable.getPossessor();
        else if (entity instanceof ServerPlayerEntity)
            return entity;
        else if (!SoulHolderComponent.isSoulless((LivingEntity) entity))
            return entity;

        return null;
    }

    public static Entity getBody(Entity entity) {
        var host = getHost(entity);
        return host != null ? host : entity;
    }

    public static void removeSoul(LivingEntity e){
        if (e instanceof ServerPlayerEntity player){
            PossessionComponent possessionComponent = PossessionComponent.get(player);
            if (possessionComponent.isPossessionOngoing()) {
                possessionComponent.stopPossessing(false);
                LOGGER.info("Stopping Possession");
            }
            else{
                RemnantComponent remnantComponent = RemnantComponent.get(player);
                remnantComponent.splitPlayer(true);
                LOGGER.info("Splitting Player");
            }
        }
        else if (!SoulHolderComponent.isSoulless(e)){
            SoulHolderComponent.get(e).removeSoul();
            LOGGER.info("Removed Soul");
        }
    }
}
