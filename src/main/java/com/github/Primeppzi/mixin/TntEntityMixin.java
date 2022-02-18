package com.github.Primeppzi.mixin;

import com.github.Primeppzi.DRCAdditionSettings;
import net.minecraft.entity.TntEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TntEntity.class)
public class TntEntityMixin {
    @ModifyConstant(method = "explode",constant = @Constant(floatValue = 4.0F))
    private float injectedpower(float constant){
        return (float) DRCAdditionSettings.TntExplosionPower;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/LivingEntity;)V",constant = @Constant(intValue = 80))
    private int injectedfusetime(int constant){
        return (int) DRCAdditionSettings.TntFuseTime;
    }
}
