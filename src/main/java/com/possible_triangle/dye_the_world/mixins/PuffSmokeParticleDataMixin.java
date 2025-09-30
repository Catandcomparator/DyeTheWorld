package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.Dyes;
import com.railwayteam.railways.content.smokestack.particles.puffs.PuffSmokeParticleData;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PuffSmokeParticleData.class, remap = false)
public class PuffSmokeParticleDataMixin {

    @Inject(
            method = "create(ZZLnet/minecraft/world/item/DyeColor;)Lcom/railwayteam/railways/content/smokestack/particles/puffs/PuffSmokeParticleData;",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 0
    )
    private static void overwritePuffColor(boolean small, boolean stationary, DyeColor dye, CallbackInfoReturnable<PuffSmokeParticleData<?>> cir) {
        if (Dyes.isVanilla(dye)) return;
        var color = dye.getTextureDiffuseColors();
        cir.setReturnValue(PuffSmokeParticleData.create(small, stationary, color[0], color[1], color[2]));
    }

}
