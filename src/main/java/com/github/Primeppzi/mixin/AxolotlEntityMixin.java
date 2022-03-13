package com.github.Primeppzi.mixin;

import com.github.Primeppzi.DRCAdditionSettings;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AxolotlEntity.class)
public abstract class AxolotlEntityMixin {
    @Shadow protected abstract void eat(PlayerEntity player, Hand hand, ItemStack stack);

    @Inject(method = "isBreedingItem",at = @At("RETURN"),cancellable = true)
    private void injected(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if(DRCAdditionSettings.easierAxolotlFeed){
            cir.setReturnValue(ItemTags.AXOLOTL_TEMPT_ITEMS.contains(stack.getItem()) || stack.isOf(Items.TROPICAL_FISH));
        }
        else{
            cir.setReturnValue(ItemTags.AXOLOTL_TEMPT_ITEMS.contains(stack.getItem()));
        }
    }
    /**
     * @author
     */
    @Overwrite
    private static boolean shouldBabyBeDifferent(Random random) {
        return random.nextFloat() < DRCAdditionSettings.blueAxolotlChance;
    }

}
