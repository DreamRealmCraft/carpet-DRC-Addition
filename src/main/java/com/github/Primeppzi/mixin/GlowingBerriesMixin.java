package com.github.Primeppzi.mixin;

import com.github.Primeppzi.DRCAdditionSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class GlowingBerriesMixin {
    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void glowBerryGlowEffect(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if(!world.isClient) {
            if (stack.getItem() == Items.GLOW_BERRIES && DRCAdditionSettings.glowGlowBerries) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200, 0, true, true));
            }
        }
    }

}
