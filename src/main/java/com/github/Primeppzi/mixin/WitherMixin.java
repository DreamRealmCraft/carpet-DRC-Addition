package com.github.Primeppzi.mixin;

import net.minecraft.entity.boss.WitherEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import static com.github.Primeppzi.DRCAdditionSettings.witherskullchance;

@Mixin(WitherEntity.class)
public class WitherMixin {

    @ModifyConstant(method = "shootSkullAt(ILnet/minecraft/entity/LivingEntity;)V",constant = @Constant(floatValue = 0.001F))
    private float injected(float constant){
        return (float) witherskullchance;
    }

}
