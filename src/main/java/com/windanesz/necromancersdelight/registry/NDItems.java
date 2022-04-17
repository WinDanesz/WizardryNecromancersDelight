package com.windanesz.necromancersdelight.registry;

import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.wizardryutils.registry.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(NecromancersDelight.MODID)
@Mod.EventBusSubscriber
public final class NDItems {

	//	public static final Item ring_ = placeholder();

	//	public static final Item amulet_ = placeholder();

	//	public static final Item charm_ = placeholder();

	//	public static final Item head_ = placeholder();

	//	public static final Item belt_ = placeholder();


	private NDItems() {} // No instances!

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		ItemRegistry.registerItemArtefact(registry, "charm_", NecromancersDelight.MODID, new Item());


	}

}