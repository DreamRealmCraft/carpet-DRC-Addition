package com.github.Primeppzi.mixin;


import com.github.Primeppzi.DRCAdditionSettings;
import net.minecraft.entity.ExperienceOrbEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {

    @Shadow private int pickingCount;

    @Shadow private int amount;

    @Inject(method = "isMergeable(Lnet/minecraft/entity/ExperienceOrbEntity;II)Z",at = @At("RETURN"),cancellable = true)
    private static void injected(ExperienceOrbEntity orb, int seed, int amount, CallbackInfoReturnable<Boolean> cir) {
        if(DRCAdditionSettings.forceExpMerge){
            cir.setReturnValue(!orb.isRemoved());
        }
        else{
            cir.setReturnValue(!orb.isRemoved() && (orb.getId() - seed) % 40 == 0);
        }

    }

    /**
     * @Prime_ppzi
     */
    @Overwrite
    private void merge(ExperienceOrbEntity other) {
        if(DRCAdditionSettings.forceExpMerge){
            this.amount+=other.getExperienceAmount();
        }
        else{
            this.pickingCount+= ((pickingCount));
        }
        other.discard();
    }
    /*
    @Inject(method = "onPlayerCollision",at = @At("TAIL"),cancellable = true)
    private void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        this.pickingCount=1;
    }
     */



}
