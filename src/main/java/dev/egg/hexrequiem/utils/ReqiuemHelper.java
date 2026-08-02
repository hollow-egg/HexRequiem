package dev.egg.hexrequiem.utils;

import baritone.api.fakeplayer.FakeServerPlayerEntity;
import ladysnake.requiem.api.v1.internal.ProtoPossessable;
import ladysnake.requiem.api.v1.possession.PossessionComponent;
import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import ladysnake.requiem.common.entity.ReleasedSoulEntity;
import ladysnake.requiem.common.entity.RequiemEntities;
import ladysnake.requiem.common.tag.RequiemEntityTypeTags;
import ladysnake.requiem.core.entity.SoulHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import static com.mojang.text2speech.Narrator.LOGGER;
import static ladysnake.requiem.common.remnant.RemnantTypes.REMNANT;

public class ReqiuemHelper {

    public static boolean canRemoveSoul(Entity entity) {
        return entity instanceof LivingEntity e
                && !e.getEquippedStack(EquipmentSlot.HEAD).isIn(ItemTags.TRIMMABLE_ARMOR); // if wearing armor on the head (tinfoil hat haha)
    }

    public static boolean canPossess(Entity entity) {
        return entity instanceof LivingEntity
                && ReqiuemHelper.getSoul(entity) == null; // if body already has a soul
    }

    public static Entity getSoul(Entity entity) {

        if (entity instanceof FakeServerPlayerEntity
            || !(entity instanceof MobEntity || entity instanceof PlayerEntity))
            return null;

        ProtoPossessable possessable = (ProtoPossessable) entity;
        if (possessable.isBeingPossessed())
            return possessable.getPossessor();
        else if (entity instanceof ServerPlayerEntity)
            return entity;
        else if (!SoulHolderComponent.isSoulless((LivingEntity) entity))
            return entity;

        return null;
    }

    public static Entity getBody(Entity entity) {
        var host = PossessionComponent.getHost(entity);
        return host != null ? host : entity;
    }

    public static void destroySoul(LivingEntity entity) { removeSoul(entity, false); }

    public static void removeSoul(LivingEntity body, boolean spawnSoul){

        var possessor = ((ProtoPossessable) body).getPossessor();

        if (possessor != null){
            PossessionComponent possessionComponent = PossessionComponent.get(possessor);
            if (possessionComponent.isPossessionOngoing()) {
                possessionComponent.stopPossessing(false);
                LOGGER.info("Stopping Possession");
            }
        }
        else if (body instanceof ServerPlayerEntity player) {
            RemnantComponent remnantComponent = RemnantComponent.get(player);
            remnantComponent.become(REMNANT);
            remnantComponent.splitPlayer(true);
            LOGGER.info("Splitting Player");
        }
        else if (!SoulHolderComponent.isSoulless(body) && !body.getType().isIn(RequiemEntityTypeTags.SOUL_CAPTURE_BLACKLIST)){
            SoulHolderComponent.get(body).removeSoul();
            if (spawnSoul)
                spawnReleasedSoul(body);

            LOGGER.info("Removed Soul");
        }
    }

    public static void placeSoulInBody(LivingEntity soul, LivingEntity body) {

        //just accept players for now :(
        if (!(soul instanceof ServerPlayerEntity))
            return;

        boolean success = false;
        if (body instanceof MobEntity mob)
            success = PossessionComponent.get((PlayerEntity) soul).startPossessing(mob);
        else if (body instanceof FakeServerPlayerEntity fake)
            success = RemnantComponent.get((PlayerEntity) soul).merge(fake);

        if (!success)
            LOGGER.warn("Failed to start Possession: Soul:" + soul + " Body:" + body);
    }

    public static void spawnReleasedSoul(LivingEntity entity){
        ReleasedSoulEntity releasedSoul = new ReleasedSoulEntity(RequiemEntities.RELEASED_SOUL, entity.getWorld(), entity.getUuid());
        releasedSoul.setPosition(entity.getX(), entity.getBodyY(0.8D), entity.getZ());
        releasedSoul.setVelocity(new Vec3d(0, 0.15, 0));
        releasedSoul.setYaw(entity.getYaw());
        releasedSoul.setPitch(entity.getPitch());
        entity.getWorld().spawnEntity(releasedSoul);
    }
}
