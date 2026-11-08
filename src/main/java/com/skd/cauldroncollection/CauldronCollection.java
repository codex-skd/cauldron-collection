package com.skd.cauldroncollection;

import com.mojang.logging.LogUtils;
import com.skd.cauldroncollection.init.CauldronInteractions;
import com.skd.cauldroncollection.init.ModBlocks;
import com.skd.cauldroncollection.init.ModCreativeTabs;
import com.skd.cauldroncollection.init.ModItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(CauldronCollection.MOD_ID)
public class CauldronCollection
{
    public static final String MOD_ID = "cauldron_collection";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CauldronCollection(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        LOGGER.info("Cauldron Collection loaded");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            CauldronInteraction.bootStrap();
            CauldronInteractions.bootstrap();

            long blocks = ForgeRegistries.BLOCKS.getKeys().stream().filter(key -> MOD_ID.equals(key.getNamespace())).count();
            long items = ForgeRegistries.ITEMS.getKeys().stream().filter(key -> MOD_ID.equals(key.getNamespace())).count();
            LOGGER.info("Cauldron Collection registered {} blocks and {} items in namespace {}", blocks, items, MOD_ID);
        });
    }
}
