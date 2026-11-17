package com.skd.cauldroncollection.init;

import com.skd.cauldroncollection.CauldronCollection;
import com.skd.cauldroncollection.blocks.ModCauldronHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Map;

public final class CauldronInteractions
{
    public static final CauldronInteraction.InteractionMap EMPTY = CauldronInteraction.newInteractionMap(CauldronCollection.MOD_ID + ":empty");
    public static final CauldronInteraction.InteractionMap WATER = CauldronInteraction.newInteractionMap(CauldronCollection.MOD_ID + ":water");

    private CauldronInteractions()
    {
    }

    /**
     * Must run after {@link CauldronInteraction#bootStrap()} so that the vanilla maps (and therefore the
     * complete list of dyeable items, banners and shulker boxes) are already populated.
     */
    public static void bootstrap()
    {
        Map<Item, CauldronInteraction> empty = EMPTY.map();
        empty.put(Items.WATER_BUCKET, CauldronInteractions::fillEmptyWithWaterBucket);
        empty.put(Items.POTION, CauldronInteractions::fillEmptyWithWaterBottle);

        Map<Item, CauldronInteraction> water = WATER.map();
        water.put(Items.WATER_BUCKET, CauldronInteractions::fillWaterWithWaterBucket);
        water.put(Items.BUCKET, CauldronInteractions::emptyWaterBucket);
        water.put(Items.GLASS_BOTTLE, CauldronInteractions::fillWaterBottle);
        water.put(Items.POTION, CauldronInteractions::addWaterBottle);

        CauldronInteraction.WATER.map().forEach((item, interaction) ->
        {
            if (interaction == CauldronInteraction.DYED_ITEM)
            {
                water.put(item, CauldronInteractions::washDyedItem);
            }
            else if (interaction == CauldronInteraction.BANNER)
            {
                water.put(item, CauldronInteractions::washBanner);
            }
            else if (interaction == CauldronInteraction.SHULKER_BOX)
            {
                water.put(item, CauldronInteractions::washShulkerBox);
            }
        });
    }

    private static ItemInteractionResult fillEmptyWithWaterBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        if (!level.isClientSide)
        {
            Item usedItem = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(usedItem));
            ModCauldronHelper.setFillLevel(state, level, pos, LayeredCauldronBlock.MAX_FILL_LEVEL);
            level.playSound((Entity) null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private static ItemInteractionResult fillEmptyWithWaterBottle(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);

        if (contents == null || !contents.is(Potions.WATER))
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide)
        {
            Item usedItem = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(usedItem));
            ModCauldronHelper.setFillLevel(state, level, pos, LayeredCauldronBlock.MIN_FILL_LEVEL);
            level.playSound((Entity) null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private static ItemInteractionResult fillWaterWithWaterBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        if (!level.isClientSide)
        {
            Item usedItem = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(usedItem));
            ModCauldronHelper.setFillLevel(state, level, pos, LayeredCauldronBlock.MAX_FILL_LEVEL);
            level.playSound((Entity) null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private static ItemInteractionResult emptyWaterBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        if (state.getValue(LayeredCauldronBlock.LEVEL) != LayeredCauldronBlock.MAX_FILL_LEVEL)
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide)
        {
            Item usedItem = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.WATER_BUCKET)));
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(usedItem));
            ModCauldronHelper.setFillLevel(state, level, pos, 0);
            level.playSound((Entity) null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private static ItemInteractionResult fillWaterBottle(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        if (!level.isClientSide)
        {
            Item usedItem = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, PotionContents.createItemStack(Items.POTION, Potions.WATER)));
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(usedItem));
            ModCauldronHelper.lowerFillLevel(state, level, pos);
            level.playSound((Entity) null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private static ItemInteractionResult addWaterBottle(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        if (state.getValue(LayeredCauldronBlock.LEVEL) == LayeredCauldronBlock.MAX_FILL_LEVEL)
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);

        if (contents == null || !contents.is(Potions.WATER))
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide)
        {
            Item usedItem = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(usedItem));
            level.setBlockAndUpdate(pos, state.cycle(LayeredCauldronBlock.LEVEL));
            level.playSound((Entity) null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private static ItemInteractionResult washDyedItem(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        if (!stack.is(ItemTags.DYEABLE) || !stack.has(DataComponents.DYED_COLOR))
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide)
        {
            stack.remove(DataComponents.DYED_COLOR);
            player.awardStat(Stats.CLEAN_ARMOR);
            ModCauldronHelper.lowerFillLevel(state, level, pos);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private static ItemInteractionResult washBanner(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        BannerPatternLayers layers = stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);

        if (layers.layers().isEmpty())
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide)
        {
            ItemStack result = stack.copyWithCount(1);
            result.set(DataComponents.BANNER_PATTERNS, layers.removeLast());
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, result, false));
            player.awardStat(Stats.CLEAN_BANNER);
            ModCauldronHelper.lowerFillLevel(state, level, pos);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private static ItemInteractionResult washShulkerBox(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        Block block = Block.byItem(stack.getItem());

        if (!(block instanceof ShulkerBoxBlock))
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide)
        {
            ItemStack result = stack.transmuteCopy(Blocks.SHULKER_BOX, 1);
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, result, false));
            player.awardStat(Stats.CLEAN_SHULKER_BOX);
            ModCauldronHelper.lowerFillLevel(state, level, pos);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }
}
