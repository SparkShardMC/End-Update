package com.end.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

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
            .luminance(state -> 7)
        );
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }
}
