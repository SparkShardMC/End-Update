package com.end;

import com.end.block.CrystaledObsidianBlock;
import com.end.block.VoidGlassBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EndUpdate implements ModInitializer {
    public static final String MOD_ID = "endupdate";

    public static final VoidGlassBlock VOID_GLASS = new VoidGlassBlock(AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final CrystaledObsidianBlock CRYSTALED_OBSIDIAN = new CrystaledObsidianBlock(AbstractBlock.Settings.copy(Blocks.OBSIDIAN));

    @Override
    public void onInitialize() {
        registerBlock("void_glass", VOID_GLASS);
        registerBlock("crystaled_obsidian", CRYSTALED_OBSIDIAN);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            content.add(VOID_GLASS);
            content.add(CRYSTALED_OBSIDIAN);
        });
    }

    private void registerBlock(String name, net.minecraft.block.Block block) {
        Identifier id = Identifier.of(MOD_ID, name);
        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
    }
}
