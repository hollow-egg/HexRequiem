package dev.egg.hexrequiem.casting.iotas;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import dev.egg.hexrequiem.registry.HexRequiemIotaTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulIota extends Iota {

    public SoulIota(@NotNull LivingEntity entity) {
        super(HexRequiemIotaTypes.SOUL, entity);
    }

    public LivingEntity getEntity() {
        return (LivingEntity) this.payload;
    }

    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    protected boolean toleratesOther(Iota that) {
        return typesMatch(this, that)
                && that instanceof SoulIota soul
                && this.getEntity().getUuid().equals(soul.getEntity().getUuid());
    }

    @Override
    public @NotNull NbtElement serialize() {
        var out = new NbtCompound();
        out.putUuid("uuid", this.getEntity().getUuid());
        if (!(this.getEntity() instanceof ServerPlayerEntity)) {
            out.putString("name", this.getEntity().getType().getName().getString());
        }
        else
            out.putString("name", this.getEntity().getEntityName());
        return out;
    }

    public static IotaType<SoulIota> TYPE = new IotaType<>() {
        @Nullable
        @Override
        public SoulIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException
        {
            var ctag = HexUtils.downcast(tag, NbtCompound.TYPE);
            UUID uuid = ctag.getUuid("uuid");
            if (uuid == null) {
                return null;
            }
            var entity = world.getEntity(uuid);
            if (entity == null || !(entity instanceof LivingEntity)) {
                return null;
            }
            return new SoulIota((LivingEntity) entity);
        }

        @Override
        public Text display(NbtElement tag)
        {
            if (!(tag instanceof NbtCompound ctag)) {
                return Text.translatable("hexcasting.spelldata.entity.whoknows");
            }
            if (!ctag.contains("name", NbtElement.STRING_TYPE)) {
                return Text.translatable("hexcasting.spelldata.entity.whoknows");
            }
            var name = ctag.getString("name");
            return Text.literal(name + "'s Soul").getWithStyle(Style.EMPTY.withColor(Formatting.BLUE)).get(0);
        }

        @Override
        public int color() {
            return 0xff_55ffff;
        }
    };

}
