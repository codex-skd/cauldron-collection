package com.skd.cauldroncollection.data;

import com.skd.cauldroncollection.CauldronCollection;
import com.skd.cauldroncollection.init.CauldronMaterial;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider
{
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, CauldronCollection.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        for (CauldronMaterial material : CauldronMaterial.values())
        {
            String name = material.getName();
            // Flat icon (inventory, ground, item frame): the HD texture as a plain sprite.
            ItemModelBuilder icon = withExistingParent(name + "_cauldron_icon", mcLoc("item/generated"))
                    .texture("layer0", modLoc("item/" + name + "_cauldron"));

            // Held model: the real 3D block, with the usual block display transforms.
            ItemModelBuilder held = getBuilder(name + "_cauldron_held")
                    .parent(new ModelFile.UncheckedModelFile(modLoc("block/" + name + "_cauldron")));
            held.transforms()
                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0.0F, 45.0F, 0.0F).scale(0.4F).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0.0F, 225.0F, 0.0F).scale(0.4F).end()
                    .end();

            getBuilder(name + "_cauldron")
                    .customLoader(SeparateTransformsModelBuilder::begin)
                    .base(held)
                    .perspective(ItemDisplayContext.GUI, icon)
                    .perspective(ItemDisplayContext.GROUND, icon)
                    .perspective(ItemDisplayContext.FIXED, icon)
                    .end();
        }
    }
}
