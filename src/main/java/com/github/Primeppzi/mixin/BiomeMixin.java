package com.github.Primeppzi.mixin;

import com.github.Primeppzi.DRCAdditionSettings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BiomeMixin {
    @Shadow public abstract boolean canSetIce(WorldView world, BlockPos pos, boolean doWaterCheck);

    @Inject(method = "canSetIce(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z",at = @At("RETURN"),cancellable = true)
    private void injectedice(WorldView world, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir){
        if(DRCAdditionSettings.disableiceforming){
            cir.setReturnValue(false);
        }
        else {
            cir.setReturnValue(this.canSetIce(world, blockPos, true));
        }
    }

    @Inject(method = "canSetSnow",at = @At("RETURN"),cancellable = true)
    private void injectedsnow(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        if (DRCAdditionSettings.disablesnowforming){
            cir.setReturnValue(false);
        }
    }
}
