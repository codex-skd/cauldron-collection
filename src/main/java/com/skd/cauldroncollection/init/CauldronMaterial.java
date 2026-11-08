package com.skd.cauldroncollection.init;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public enum CauldronMaterial
{
    ACACIA("acacia", MapColor.COLOR_ORANGE, SoundType.WOOD, 2.0F, 3.0F, false, true),
    DARK_OAK("dark_oak", MapColor.COLOR_BROWN, SoundType.WOOD, 2.0F, 3.0F, false, true),
    BIRCH("birch", MapColor.SAND, SoundType.WOOD, 2.0F, 3.0F, false, true),
    JUNGLE("jungle", MapColor.DIRT, SoundType.WOOD, 2.0F, 3.0F, false, true),
    OAK("oak", MapColor.WOOD, SoundType.WOOD, 2.0F, 3.0F, false, true),
    SPRUCE("spruce", MapColor.PODZOL, SoundType.WOOD, 2.0F, 3.0F, false, true),
    GOLD("gold", MapColor.GOLD, SoundType.METAL, 3.0F, 10.0F, true, false),
    DIAMOND("diamond", MapColor.DIAMOND, SoundType.METAL, 5.0F, 10.0F, true, false),
    COBBLE("cobble", MapColor.STONE, SoundType.STONE, 2.0F, 2.0F, true, false),
    GLASS("glass", MapColor.NONE, SoundType.GLASS, 0.3F, 0.3F, false, false),
    OBSIDIAN("obsidian", MapColor.COLOR_BLACK, SoundType.STONE, 50.0F, 1200.0F, true, false),
    BRICK("brick", MapColor.COLOR_RED, SoundType.STONE, 2.0F, 10.0F, true, false),
    STONE("stone", MapColor.STONE, SoundType.STONE, 2.0F, 2.0F, true, false),
    GRANITE("granite", MapColor.DIRT, SoundType.STONE, 2.0F, 2.0F, true, false),
    POLISHED_GRANITE("polished_granite", MapColor.DIRT, SoundType.STONE, 2.0F, 2.0F, true, false),
    DIORITE("diorite", MapColor.QUARTZ, SoundType.STONE, 2.0F, 2.0F, true, false),
    POLISHED_DIORITE("polished_diorite", MapColor.QUARTZ, SoundType.STONE, 2.0F, 2.0F, true, false),
    ANDESITE("andesite", MapColor.STONE, SoundType.STONE, 2.0F, 2.0F, true, false),
    POLISHED_ANDESITE("polished_andesite", MapColor.STONE, SoundType.STONE, 2.0F, 2.0F, true, false);

    private final String name;
    private final MapColor mapColor;
    private final SoundType soundType;
    private final float destroyTime;
    private final float explosionResistance;
    private final boolean requiresCorrectToolForDrops;
    private final boolean flammable;

    CauldronMaterial(String name, MapColor mapColor, SoundType soundType, float destroyTime, float explosionResistance, boolean requiresCorrectToolForDrops, boolean flammable)
    {
        this.name = name;
        this.mapColor = mapColor;
        this.soundType = soundType;
        this.destroyTime = destroyTime;
        this.explosionResistance = explosionResistance;
        this.requiresCorrectToolForDrops = requiresCorrectToolForDrops;
        this.flammable = flammable;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isFlammable()
    {
        return this.flammable;
    }

    public BlockBehaviour.Properties createProperties()
    {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(this.mapColor)
                .sound(this.soundType)
                .strength(this.destroyTime, this.explosionResistance)
                .noOcclusion();

        if (this.requiresCorrectToolForDrops)
        {
            properties.requiresCorrectToolForDrops();
        }

        return properties;
    }
}
