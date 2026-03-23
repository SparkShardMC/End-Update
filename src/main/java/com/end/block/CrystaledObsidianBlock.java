package com.end.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CrystaledObsidianBlock extends Block {
    public CrystaledObsidianBlock(Settings settings) {
        super(settings
            .strength(150.0f, 3600.0f) // 15s break time / TNT proof
            .luminance(state -> 15)    // Max light
            .requiresTool()
        );
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            // Damage player 1 per tick while breaking
            player.damage(world.getDamageSources().magic(), 1.0f);
        }
        super.onBlockBreakStart(state, world, pos, player);
    }
}
