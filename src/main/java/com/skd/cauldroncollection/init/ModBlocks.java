package com.skd.cauldroncollection.init;

import com.skd.cauldroncollection.CauldronCollection;
import com.skd.cauldroncollection.blocks.ModCauldronBlock;
import com.skd.cauldroncollection.blocks.ModLayeredCauldronBlock;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Map;

public final class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CauldronCollection.MOD_ID);
    public static final Map<CauldronMaterial, RegistryObject<ModCauldronBlock>> EMPTY_CAULDRONS = new EnumMap<>(CauldronMaterial.class);
    public static final Map<CauldronMaterial, RegistryObject<ModLayeredCauldronBlock>> WATER_CAULDRONS = new EnumMap<>(CauldronMaterial.class);

    static
    {
        for (CauldronMaterial material : CauldronMaterial.values())
        {
            RegistryObject<ModCauldronBlock> emptyCauldron = BLOCKS.register(material.getName() + "_cauldron",
                    () -> new ModCauldronBlock(material.createProperties(), material, CauldronInteractions.EMPTY,
                            () -> WATER_CAULDRONS.get(material).get()));
            EMPTY_CAULDRONS.put(material, emptyCauldron);

            RegistryObject<ModLayeredCauldronBlock> waterCauldron = BLOCKS.register(material.getName() + "_water_cauldron",
                    () -> new ModLayeredCauldronBlock(Biome.Precipitation.RAIN, material.createProperties(), material, CauldronInteractions.WATER,
                            () -> EMPTY_CAULDRONS.get(material).get()));
            WATER_CAULDRONS.put(material, waterCauldron);
        }
    }

    private ModBlocks()
    {
    }

    public static RegistryObject<ModCauldronBlock> emptyCauldron(CauldronMaterial material)
    {
        return EMPTY_CAULDRONS.get(material);
    }

    public static RegistryObject<ModLayeredCauldronBlock> waterCauldron(CauldronMaterial material)
    {
        return WATER_CAULDRONS.get(material);
    }
}
