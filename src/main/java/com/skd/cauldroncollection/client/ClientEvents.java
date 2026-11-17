package com.skd.cauldroncollection.client;

import com.skd.cauldroncollection.CauldronCollection;
import com.skd.cauldroncollection.init.ModBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = CauldronCollection.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents
{
    private ClientEvents()
    {
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event)
    {
        Block[] waterCauldrons = ModBlocks.WATER_CAULDRONS.values().stream()
                .map(RegistryObject::get)
                .toArray(Block[]::new);

        event.register((state, level, pos, tintIndex) -> level != null && pos != null
                ? BiomeColors.getAverageWaterColor(level, pos)
                : -1, waterCauldrons);
    }
}
