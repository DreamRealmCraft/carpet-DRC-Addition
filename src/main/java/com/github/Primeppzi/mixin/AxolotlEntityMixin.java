package com.github.Primeppzi.mixin;

import com.github.Primeppzi.DRCAdditionSettings;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AxolotlEntity.class)
public abstract class AxolotlEntityMixin {

    @Inject(method = "isBreedingItem",at = @At("RETURN"),cancellable = true)
    private void injected(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if(DRCAdditionSettings.easierAxolotlFeed){
            cir.setReturnValue(stack.isIn(ItemTags.AXOLOTL_TEMPT_ITEMS) || stack.isOf(Items.TROPICAL_FISH));
        }
        else{
            cir.setReturnValue(stack.isIn(ItemTags.AXOLOTL_TEMPT_ITEMS));
        }
    }
    /**
     * @author Prime_ppzi
     */
    @Overwrite
    private static boolean shouldBabyBeDifferent(Random random) {
        return random.nextFloat() < DRCAdditionSettings.blueAxolotlChance;
    }

}
