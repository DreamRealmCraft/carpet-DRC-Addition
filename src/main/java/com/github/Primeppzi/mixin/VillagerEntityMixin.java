package com.github.Primeppzi.mixin;


import com.github.Primeppzi.DRCAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {
    @Inject(method = "tick",at = @At("HEAD"))
    private void injected(CallbackInfo ci){
        this.villagerdetach();
    }
    public void villagerdetach(){
        VillagerEntity villager = (VillagerEntity) (Object)this;
        if(villager.hasVehicle() &&villager.getVehicle().getType() == EntityType.CHICKEN){
            villager.detach();
        }
    }
}
