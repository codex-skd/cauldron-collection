package com.skd.cauldroncollection.data;

import com.skd.cauldroncollection.init.CauldronMaterial;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

public final class DatagenHelper
{
    private DatagenHelper()
    {
    }

    public static String texture(CauldronMaterial material)
    {
        return "minecraft:block/" + switch (material)
        {
            case ACACIA -> "acacia_planks";
            case DARK_OAK -> "dark_oak_planks";
            case BIRCH -> "birch_planks";
            case JUNGLE -> "jungle_planks";
            case OAK -> "oak_planks";
            case SPRUCE -> "spruce_planks";
            case GOLD -> "gold_block";
            case DIAMOND -> "diamond_block";
            case COBBLE -> "cobblestone";
            case GLASS -> "glass";
            case OBSIDIAN -> "obsidian";
            case BRICK -> "bricks";
            case STONE -> "stone";
            case GRANITE -> "granite";
            case POLISHED_GRANITE -> "polished_granite";
            case DIORITE -> "diorite";
            case POLISHED_DIORITE -> "polished_diorite";
            case ANDESITE -> "andesite";
            case POLISHED_ANDESITE -> "polished_andesite";
        };
    }

    public static String englishName(CauldronMaterial material)
    {
        return switch (material)
        {
            case ACACIA -> "Acacia Cauldron";
            case DARK_OAK -> "Dark Oak Cauldron";
            case BIRCH -> "Birch Cauldron";
            case JUNGLE -> "Jungle Cauldron";
            case OAK -> "Oak Cauldron";
            case SPRUCE -> "Spruce Cauldron";
            case GOLD -> "Gold Cauldron";
            case DIAMOND -> "Diamond Cauldron";
            case COBBLE -> "Cobblestone Cauldron";
            case GLASS -> "Glass Cauldron";
            case OBSIDIAN -> "Obsidian Cauldron";
            case BRICK -> "Brick Cauldron";
            case STONE -> "Stone Cauldron";
            case GRANITE -> "Granite Cauldron";
            case POLISHED_GRANITE -> "Polished Granite Cauldron";
            case DIORITE -> "Diorite Cauldron";
            case POLISHED_DIORITE -> "Polished Diorite Cauldron";
            case ANDESITE -> "Andesite Cauldron";
            case POLISHED_ANDESITE -> "Polished Andesite Cauldron";
        };
    }

    public static RecipeIngredient recipeIngredient(CauldronMaterial material)
    {
        TagKey<Item> tag = switch (material)
        {
            case GOLD -> Tags.Items.INGOTS_GOLD;
            case DIAMOND -> Tags.Items.GEMS_DIAMOND;
            case COBBLE -> Tags.Items.COBBLESTONES;
            case GLASS -> Tags.Items.GLASS_BLOCKS;
            case OBSIDIAN -> Tags.Items.OBSIDIANS;
            case BRICK -> TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "ingots/brick"));
            case STONE -> Tags.Items.STONES;
            default -> null;
        };

        if (tag != null)
        {
            Ingredient ingredient = Ingredient.of(tag);
            Criterion<?> criterion = InventoryChangeTrigger.TriggerInstance.hasItems(
                    ItemPredicate.Builder.item().of(tag).build());
            return new RecipeIngredient(ingredient, criterion);
        }

        ItemLike item = switch (material)
        {
            case ACACIA -> Items.ACACIA_PLANKS;
            case DARK_OAK -> Items.DARK_OAK_PLANKS;
            case BIRCH -> Items.BIRCH_PLANKS;
            case JUNGLE -> Items.JUNGLE_PLANKS;
            case OAK -> Items.OAK_PLANKS;
            case SPRUCE -> Items.SPRUCE_PLANKS;
            case GRANITE -> Items.GRANITE;
            case POLISHED_GRANITE -> Items.POLISHED_GRANITE;
            case DIORITE -> Items.DIORITE;
            case POLISHED_DIORITE -> Items.POLISHED_DIORITE;
            case ANDESITE -> Items.ANDESITE;
            case POLISHED_ANDESITE -> Items.POLISHED_ANDESITE;
            default -> throw new IllegalStateException("No item ingredient for " + material);
        };

        return new RecipeIngredient(Ingredient.of(item), InventoryChangeTrigger.TriggerInstance.hasItems(item));
    }

    public record RecipeIngredient(Ingredient ingredient, Criterion<?> criterion)
    {
    }
}
