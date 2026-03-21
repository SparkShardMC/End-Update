package com.end;

import com.end.block.VoidGlassBlock;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EndUpdate implements ModInitializer {
    public static final String MOD_ID = "endupdate";
    public static final VoidGlassBlock VOID_GLASS = new VoidGlassBlock(AbstractBlock.Settings.copy(Blocks.GLASS));

    @Override
    public void onInitialize() {
        Identifier id = Identifier.of(MOD_ID, "void_glass");
        Registry.register(Registries.BLOCK, id, VOID_GLASS);
        Registry.register(Registries.ITEM, id, new BlockItem(VOID_GLASS, new Item.Settings()));
    }
}
