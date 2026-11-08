package com.skd.cauldroncollection.init;

import com.skd.cauldroncollection.CauldronCollection;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CauldronCollection.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CAULDRONS = CREATIVE_MODE_TABS.register("cauldrons", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.cauldron_collection.cauldron"))
            .icon(() -> new ItemStack(ModItems.cauldron(CauldronMaterial.OAK).get()))
            .displayItems((parameters, output) ->
            {
                for (CauldronMaterial material : CauldronMaterial.values())
                {
                    output.accept(ModItems.cauldron(material).get());
                }
            })
            .build());

    private ModCreativeTabs()
    {
    }
}
