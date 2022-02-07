package com.github.Primeppzi.mixin;
import carpet.CarpetSettings;
import com.github.Primeppzi.DRCAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(GlowSquidEntity.class)
public class GlowSquidEntityMixin {
    @Inject(method = "canSpawn",at = @At("RETURN"),cancellable = true)
    private static void injected(EntityType<? extends LivingEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if(DRCAdditionSettings.glowSquidRestriction) {
            boolean i = hasNoLight(world, pos) && hasStoneFloor(pos, world) && world.getBlockState(pos).isOf(Blocks.WATER) && pos.getY() <= world.getSeaLevel() - 33;
            cir.setReturnValue(i);
        }


    }

    private static boolean hasStoneFloor(BlockPos pos, ServerWorldAccess world) {
        BlockPos.Mutable lv = pos.mutableCopy();

        for(int i = 0; i < 5; ++i) {
            lv.move(Direction.DOWN);
            BlockState lv2 = world.getBlockState(lv);
            if (lv2.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                return true;
            }

            if (!lv2.isOf(Blocks.WATER)) {
                return false;
            }
        }

        return false;
    }

    private static boolean hasNoLight(ServerWorldAccess world, BlockPos pos) {
        int i = world.toServerWorld().isThundering() ? world.getLightLevel(pos, 10) : world.getLightLevel(pos);
        return i == 0;
    }
}