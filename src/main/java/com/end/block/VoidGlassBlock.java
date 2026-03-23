package com.end.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class VoidGlassBlock extends GlassBlock {
    public VoidGlassBlock(Settings settings) {
        super(settings.nonOpaque().strength(50.0f, 1200.0f));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Random.create().nextFloat() > 0.5f ? VoxelShapes.empty() : super.getCollisionShape(state, world, pos, context);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (Random.create().nextFloat() > 0.5f) {
            entity.handleFallDamage(fallDistance, 100.0f, world.getDamageSources().outOfWorld());
        } else {
            entity.handleFallDamage(fallDistance, 0.0f, world.getDamageSources().fall());
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity instanceof LivingEntity living && pos.getSquaredDistance(0, pos.getY(), 0) <= 100) {
            if (world.getTime() % 200 == 0) living.damage(world.getDamageSources().magic(), 2.0f);
        }
    }

    @Override
    public int getLuminance(BlockState state, BlockView world, BlockPos pos) {
        return (world instanceof World w && w.getLightLevel(pos) == 0) ? 15 : 0;
    }
}
