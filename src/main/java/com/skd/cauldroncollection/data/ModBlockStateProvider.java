package com.skd.cauldroncollection.data;

import com.skd.cauldroncollection.CauldronCollection;
import com.skd.cauldroncollection.init.CauldronMaterial;
import com.skd.cauldroncollection.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider
{
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, CauldronCollection.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        for (CauldronMaterial material : CauldronMaterial.values())
        {
            String name = material.getName();
            ResourceLocation texture = ResourceLocation.parse(DatagenHelper.texture(material));
            boolean glass = material == CauldronMaterial.GLASS;

            Block emptyCauldron = ModBlocks.emptyCauldron(material).get();
            BlockModelBuilder emptyModel = models().withExistingParent(name + "_cauldron", mcLoc("block/cauldron"))
                    .texture("particle", texture)
                    .texture("top", texture)
                    .texture("bottom", texture)
                    .texture("side", texture)
                    .texture("inside", texture);

            if (glass)
            {
                emptyModel.renderType("minecraft:cutout");
            }

            simpleBlock(emptyCauldron, emptyModel);

            Block waterCauldron = ModBlocks.waterCauldron(material).get();
            BlockModelBuilder level1 = waterModel(name + "_water_cauldron_level1", "block/template_cauldron_level1", texture, glass);
            BlockModelBuilder level2 = waterModel(name + "_water_cauldron_level2", "block/template_cauldron_level2", texture, glass);
            BlockModelBuilder full = waterModel(name + "_water_cauldron_full", "block/template_cauldron_full", texture, glass);

            getVariantBuilder(waterCauldron)
                    .partialState().with(LayeredCauldronBlock.LEVEL, 1).modelForState().modelFile(level1).addModel()
                    .partialState().with(LayeredCauldronBlock.LEVEL, 2).modelForState().modelFile(level2).addModel()
                    .partialState().with(LayeredCauldronBlock.LEVEL, 3).modelForState().modelFile(full).addModel();
        }
    }

    private BlockModelBuilder waterModel(String name, String parent, ResourceLocation texture, boolean glass)
    {
        BlockModelBuilder model = models().withExistingParent(name, mcLoc(parent))
                .texture("particle", texture)
                .texture("top", texture)
                .texture("bottom", texture)
                .texture("side", texture)
                .texture("inside", texture)
                .texture("content", mcLoc("block/water_still"));

        if (glass)
        {
            model.renderType("minecraft:cutout");
        }

        return model;
    }
}
