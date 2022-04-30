package com.windanesz.necromancersdelight.registry;

import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.block.BlockTemporaryGlowShroom;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@GameRegistry.ObjectHolder(NecromancersDelight.MODID)
@Mod.EventBusSubscriber
public class NDBlocks {

	private NDBlocks() {} // no instances

	public static final Block temporary_glowshroom = placeholder();


	@Nonnull
	@SuppressWarnings("ConstantConditions")
	private static <T> T placeholder() { return null; }

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		registerBlock(registry, "temporary_glowshroom", new BlockTemporaryGlowShroom());
	}


	public static void registerBlock(IForgeRegistry<Block> registry, String name, Block block) {
		block.setRegistryName(NecromancersDelight.MODID, name);
		block.setTranslationKey(block.getRegistryName().toString());
		registry.register(block);
	}

	/**
	 * Called from the preInit method in the main mod class to register all the tile entities.
	 */
	public static void registerTileEntities() { }

}
