package com.skd.cauldroncollection.data;

import com.skd.cauldroncollection.init.CauldronMaterial;
import com.skd.cauldroncollection.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLoot extends BlockLootSubProvider
{
    protected ModBlockLoot(HolderLookup.Provider registries)
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate()
    {
        for (CauldronMaterial material : CauldronMaterial.values())
        {
            Block emptyCauldron = ModBlocks.emptyCauldron(material).get();
            Block waterCauldron = ModBlocks.waterCauldron(material).get();

            if (material == CauldronMaterial.GLASS)
            {
                dropWhenSilkTouch(emptyCauldron);
                otherWhenSilkTouch(waterCauldron, emptyCauldron);
            }
            else
            {
                dropSelf(emptyCauldron);
                dropOther(waterCauldron, emptyCauldron.asItem());
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        List<Block> blocks = new ArrayList<>(CauldronMaterial.values().length * 2);

        for (CauldronMaterial material : CauldronMaterial.values())
        {
            blocks.add(ModBlocks.emptyCauldron(material).get());
            blocks.add(ModBlocks.waterCauldron(material).get());
        }

        return blocks;
    }
}
