package com.end.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class VoidGlassBlock extends GlassBlock {
    public static final BlockSoundGroup VOID_SOUNDS = new BlockSoundGroup(
            0.0f, 1.0f,
            BlockSoundGroup.GLASS.getBreakSound(),
            BlockSoundGroup.GLASS.getStepSound(),
            BlockSoundGroup.GLASS.getPlaceSound(),
            BlockSoundGroup.GLASS.getHitSound(),
            BlockSoundGroup.GLASS.getFallSound()
    );

    public VoidGlassBlock(AbstractBlock.Settings settings) {
        super(settings
                .nonOpaque()
                .strength(50.0f, 1200.0f)
                .sounds(VOID_SOUNDS)
                .ticksRandomly()
        );
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // 50% chance to have no collision (clip through)
        if (Random.create().nextFloat() > 0.5f) {
            return VoxelShapes.empty();
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity instanceof LivingEntity livingEntity) {
            // Check proximity to 0,0
            double distanceToCenter = Math.sqrt(pos.getSquaredDistance(0, pos.getY(), 0));
            
            if (distanceToCenter <= 10) {
                // Damage every 10 seconds (200 ticks) logic handled via world time
                if (world.getTime() % 200 == 0) {
                    livingEntity.damage(world.getDamageSources().magic(), 2.0f);
                }
            }
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        // If they DIDN'T clip through (hit the block), 50% chance to die instantly
        if (Random.create().nextFloat() > 0.5f) {
            entity.handleFallDamage(fallDistance, 100.0f, world.getDamageSources().outOfWorld());
        } else {
            entity.handleFallDamage(fallDistance, 0.0f, world.getDamageSources().fall());
        }
    }

    @Override
    public int getLuminance(BlockState state, BlockView world, BlockPos pos) {
        // Glow only in total darkness (Light level 0)
        if (world instanceof World realWorld && realWorld.getLightLevel(pos) == 0) {
            return 15;
        }
        return 0;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }
}
