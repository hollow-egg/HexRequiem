package dev.egg.hexrequiem.casting.iotas;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.api.utils.HexUtils;
import com.samsthenerd.inline.api.InlineAPI;
import dev.egg.hexrequiem.mixin.ColorProviderAccessor;
import dev.egg.hexrequiem.registry.HexRequiemIotaTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.samsthenerd.inline.api.data.ItemInlineData;

import java.awt.*;
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
        if (!(this.getEntity() instanceof ServerPlayerEntity))
            out.putString("name", this.getEntity().getType().getName().getString());
        else {
            out.putString("name", this.getEntity().getEntityName());
            //save pigment
            var provider = HexAPI.instance().getColorizer((ServerPlayerEntity) this.getEntity());
            out.put("pigment", provider.serializeToNBT());
        }

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
            if (!(entity instanceof LivingEntity)) {
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

            if (!ctag.contains("pigment", NbtElement.COMPOUND_TYPE)) {
                return Text.literal(name + " Soul").getWithStyle(Style.EMPTY.withColor(Formatting.BLUE)).get(0);
            }

            // fetch pigment
            var pigment = FrozenPigment.fromNBT(ctag.getCompound("pigment"));
            var colorProvider = (ColorProviderAccessor)pigment.getColorProvider();

            //make item inline
            Text inlineItem = new ItemInlineData(pigment.item()).asText(false);
            inlineItem = inlineItem.copyContentOnly().setStyle(InlineAPI.INSTANCE.withSizeModifier(inlineItem.getStyle(), 1.5));

            //create gradient
            String text = name + "'s Soul: ";
            StringBuilder textFinal = new StringBuilder("<neon>");

            int length = text.length() - 1;
            for (int i = 0; i < length; ++i) {
                String hexColor1 = String.format("#%06X", (0xFFFFFF & colorProvider.hexrequiem$getRawColor(600.0f/length * i, Vec3d.ZERO)));
                String hexColor2 = String.format("#%06X", (0xFFFFFF & colorProvider.hexrequiem$getRawColor(600.0f/length * (i + 1), Vec3d.ZERO)));
                textFinal.append("<grad from=").append(hexColor1).append(" to=").append(hexColor2).append(">").append(text.charAt(i)).append("</grad>");
            }

            return Text.literal(textFinal.toString()).append(inlineItem);
        }

        @Override
        public int color() {
            return 0xff_55ffff;
        }
    };

}
