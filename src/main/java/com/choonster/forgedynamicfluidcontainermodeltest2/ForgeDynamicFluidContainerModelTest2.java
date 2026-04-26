package com.choonster.forgedynamicfluidcontainermodeltest2;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(ForgeDynamicFluidContainerModelTest2.MODID)
@Mod.EventBusSubscriber(modid = ForgeDynamicFluidContainerModelTest2.MODID)
public final class ForgeDynamicFluidContainerModelTest2 {
	public static final String MODID = "forge_dynamic_fluid_container_model_test_2";

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);
	private static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.FLUID_TYPES, MODID);

	public static final String KEY = "bucket";

	public static final RegistryObject<Item> BUCKET = ITEMS.register(KEY,
			() -> new BucketItem(
					() -> Fluids.LAVA,
					new Item.Properties().setId(ITEMS.key(KEY))
			)
	);

	private static ForgeFlowingFluid.Properties FLUID_PROPERTIES;

	public static final RegistryObject<ForgeFlowingFluid.Source> STILL = FLUIDS.register("gas",
			() -> new ForgeFlowingFluid.Source(FLUID_PROPERTIES)
	);

	public static final RegistryObject<ForgeFlowingFluid.Flowing> FLOWING = FLUIDS.register("gas_flowing",
			() -> new ForgeFlowingFluid.Flowing(FLUID_PROPERTIES)
	);

	public static final RegistryObject<BasicFluidType> FLUID_TYPE = FLUID_TYPES.register("gas",
			() -> new BasicFluidType(
					Identifier.fromNamespaceAndPath(MODID, "block/fluid_gas_still"),
					Identifier.fromNamespaceAndPath(MODID, "block/fluid_gas_flow"),
					FluidType.Properties.create()
							.lightLevel(10)
							.density(-1600)
							.viscosity(100)
							.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
							.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
							.sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
			)
	);

	public static final RegistryObject<LiquidBlock> BLOCK = BLOCKS.register("gas",
			() -> new LiquidBlock(
					STILL,
					Block.Properties.of()
							.replaceable()
							.noCollision()
							.strength(100)
							.pushReaction(PushReaction.DESTROY)
							.noLootTable()
							.liquid()
							.sound(SoundType.EMPTY)
							.setId(BLOCKS.key("gas"))
			)
	);

	public static final RegistryObject<BucketItem> GAS_BUCKET = ITEMS.register("gas_bucket", () -> new BucketItem(
			STILL,
			new Item.Properties().setId(ITEMS.key("gas_bucket"))
	));

	static {
		FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(FLUID_TYPE, STILL, FLOWING)
				.block(BLOCK)
				.bucket(GAS_BUCKET);
	}

	public ForgeDynamicFluidContainerModelTest2(FMLJavaModLoadingContext context) {
		var busGroup = context.getModBusGroup();

		ITEMS.register(busGroup);
		BLOCKS.register(busGroup);
		FLUIDS.register(busGroup);
		FLUID_TYPES.register(busGroup);
	}

	@SubscribeEvent
	public static void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(BUCKET.get());
		}
	}
}
