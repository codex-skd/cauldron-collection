package com.skd.cauldroncollection.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public final class ModCauldronHelper
{
    private ModCauldronHelper()
    {
    }

    /**
     * Sets the fill level of a material cauldron. A level of 0 or less converts the block back into the
     * empty cauldron of the same material, levels 1..3 use the matching layered water cauldron.
     */
    public static void setFillLevel(BlockState state, Level level, BlockPos pos, int fillLevel)
    {
        if (!(state.getBlock() instanceof MaterialCauldron cauldron))
        {
            return;
        }

        BlockState newState;

        if (fillLevel <= 0)
        {
            newState = cauldron.getEmptyCauldron().defaultBlockState();
        }
        else
        {
            int clampedLevel = Math.min(fillLevel, LayeredCauldronBlock.MAX_FILL_LEVEL);
            newState = cauldron.getWaterCauldron().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, clampedLevel);
        }

        level.setBlockAndUpdate(pos, newState);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
    }

    /**
     * 1.21.1 equivalent of {@link LayeredCauldronBlock#lowerFillLevel(BlockState, Level, BlockPos)} that
     * never falls back to the vanilla {@link net.minecraft.world.level.block.Blocks#CAULDRON}.
     */
    public static void lowerFillLevel(BlockState state, Level level, BlockPos pos)
    {
        if (!(state.getBlock() instanceof MaterialCauldron))
        {
            return;
        }

        setFillLevel(state, level, pos, state.getValue(LayeredCauldronBlock.LEVEL) - 1);
    }
}
