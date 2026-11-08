package com.skd.cauldroncollection.blocks;

import com.mojang.serialization.MapCodec;
import com.skd.cauldroncollection.init.CauldronInteractions;
import com.skd.cauldroncollection.init.CauldronMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Supplier;

public class ModCauldronBlock extends AbstractCauldronBlock implements MaterialCauldron
{
    public static final MapCodec<ModCauldronBlock> CODEC = simpleCodec(ModCauldronBlock::new);

    private final CauldronMaterial material;
    private final Supplier<Block> waterCauldron;

    public ModCauldronBlock(BlockBehaviour.Properties properties)
    {
        this(properties, null, CauldronInteractions.EMPTY, () -> null);
    }

    public ModCauldronBlock(BlockBehaviour.Properties properties, CauldronMaterial material, CauldronInteraction.InteractionMap interactions, Supplier<Block> waterCauldron)
    {
        super(properties, interactions);
        this.material = material;
        this.waterCauldron = waterCauldron;
    }

    @Override
    protected MapCodec<ModCauldronBlock> codec()
    {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState state)
    {
        return false;
    }

    @Override
    public Block getEmptyCauldron()
    {
        return this;
    }

    @Override
    public Block getWaterCauldron()
    {
        return this.waterCauldron.get();
    }

    @Override
    public void handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation)
    {
        if (precipitation != Biome.Precipitation.RAIN || level.getRandom().nextFloat() >= 0.05F)
        {
            return;
        }

        Block waterCauldron = this.getWaterCauldron();

        if (waterCauldron == null)
        {
            return;
        }

        level.setBlockAndUpdate(pos, waterCauldron.defaultBlockState());
        level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    protected boolean canReceiveStalactiteDrip(Fluid fluid)
    {
        return fluid == Fluids.WATER;
    }

    @Override
    protected void receiveStalactiteDrip(BlockState state, Level level, BlockPos pos, Fluid fluid)
    {
        if (fluid != Fluids.WATER)
        {
            return;
        }

        Block waterCauldron = this.getWaterCauldron();

        if (waterCauldron == null)
        {
            return;
        }

        BlockState newState = waterCauldron.defaultBlockState();
        level.setBlockAndUpdate(pos, newState);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        level.levelEvent(1047, pos, 0);
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
