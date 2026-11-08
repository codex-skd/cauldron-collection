package com.skd.cauldroncollection.blocks;

import com.mojang.serialization.MapCodec;
import com.skd.cauldroncollection.init.CauldronInteractions;
import com.skd.cauldroncollection.init.CauldronMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ModLayeredCauldronBlock extends LayeredCauldronBlock implements MaterialCauldron
{
    public static final MapCodec<LayeredCauldronBlock> CODEC = simpleCodec(properties -> new ModLayeredCauldronBlock(properties));

    private final CauldronMaterial material;
    private final Supplier<Block> emptyCauldron;

    public ModLayeredCauldronBlock(BlockBehaviour.Properties properties)
    {
        this(Biome.Precipitation.RAIN, properties, null, CauldronInteractions.WATER, () -> null);
    }

    public ModLayeredCauldronBlock(Biome.Precipitation precipitation, BlockBehaviour.Properties properties, CauldronMaterial material, CauldronInteraction.InteractionMap interactions, Supplier<Block> emptyCauldron)
    {
        super(precipitation, interactions, properties);
        this.material = material;
        this.emptyCauldron = emptyCauldron;
    }

    @Override
    public MapCodec<LayeredCauldronBlock> codec()
    {
        return CODEC;
    }

    @Override
    public Block getEmptyCauldron()
    {
        return this.emptyCauldron.get();
    }

    @Override
    public Block getWaterCauldron()
    {
        return this;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state)
    {
        Block emptyCauldron = this.getEmptyCauldron();
        return emptyCauldron == null ? ItemStack.EMPTY : new ItemStack(emptyCauldron);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (!level.isClientSide && entity.isOnFire() && this.isEntityInsideContent(state, pos, entity))
        {
            entity.clearFire();

            if (entity.mayInteract(level, pos))
            {
                ModCauldronHelper.lowerFillLevel(state, level, pos);
            }
        }
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return this.material != null && this.material.isFlammable() ? 10 : 0;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return this.material != null && this.material.isFlammable() ? 5 : 0;
    }
}
