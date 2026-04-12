package com.choonster.forgedynamicfluidcontainermodeltest2;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(ForgeDynamicFluidContainerModelTest2.MODID)
@Mod.EventBusSubscriber(modid = ForgeDynamicFluidContainerModelTest2.MODID)
public final class ForgeDynamicFluidContainerModelTest2 {
    public static final String MODID = "forge_dynamic_fluid_container_model_test_2";

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final String KEY = "bucket";

    public static final RegistryObject<Item> BUCKET = ITEMS.register(KEY,
            () -> new BucketItem(
                    () -> Fluids.LAVA,
                    new Item.Properties().setId(ITEMS.key(KEY))
            )
    );

    public ForgeDynamicFluidContainerModelTest2(FMLJavaModLoadingContext context) {
        var busGroup = context.getModBusGroup();

        ITEMS.register(busGroup);
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BUCKET.get());
        }
    }
}
