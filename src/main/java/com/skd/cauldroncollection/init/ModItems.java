package com.skd.cauldroncollection.init;

import com.skd.cauldroncollection.CauldronCollection;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Map;

public final class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CauldronCollection.MOD_ID);
    public static final Map<CauldronMaterial, RegistryObject<BlockItem>> CAULDRON_ITEMS = new EnumMap<>(CauldronMaterial.class);

    static
    {
        for (CauldronMaterial material : CauldronMaterial.values())
        {
            RegistryObject<BlockItem> item = ITEMS.register(material.getName() + "_cauldron",
                    () -> new BlockItem(ModBlocks.emptyCauldron(material).get(), new Item.Properties()));
            CAULDRON_ITEMS.put(material, item);
        }
    }

    private ModItems()
    {
    }

    public static RegistryObject<BlockItem> cauldron(CauldronMaterial material)
    {
        return CAULDRON_ITEMS.get(material);
    }
}
