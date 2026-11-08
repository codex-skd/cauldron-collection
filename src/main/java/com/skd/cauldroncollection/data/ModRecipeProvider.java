package com.skd.cauldroncollection.data;

import com.skd.cauldroncollection.CauldronCollection;
import com.skd.cauldroncollection.init.CauldronMaterial;
import com.skd.cauldroncollection.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider
{
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput)
    {
        for (CauldronMaterial material : CauldronMaterial.values())
        {
            DatagenHelper.RecipeIngredient ingredient = DatagenHelper.recipeIngredient(material);

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.cauldron(material).get())
                    .pattern("X X")
                    .pattern("X X")
                    .pattern("XXX")
                    .define('X', ingredient.ingredient())
                    .unlockedBy("has_ingredient", ingredient.criterion())
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CauldronCollection.MOD_ID, material.getName() + "_cauldron"));
        }
    }
}
