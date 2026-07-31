package dev.egg.hexrequiem.mixin;

import at.petrak.hexcasting.api.pigment.ColorProvider;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ColorProvider.class)
public interface ColorProviderAccessor {
    @Invoker("getRawColor")
    int hexrequiem$getRawColor(float time, Vec3d position);
}
