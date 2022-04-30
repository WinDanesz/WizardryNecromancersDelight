package com.windanesz.necromancersdelight.registry;

import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.item.ItemMaliceWard;
import com.windanesz.wizardryutils.registry.ItemRegistry;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@ObjectHolder(NecromancersDelight.MODID)
@Mod.EventBusSubscriber
public final class NDItems {

	@Nonnull
	@SuppressWarnings("ConstantConditions")
	private static <T> T placeholder(){ return null; }

	public static final Item amulet_slowness_immunity = placeholder();
	public static final Item amulet_weakness_immunity = placeholder();
	public static final Item amulet_malice_ward = placeholder();
	public static final Item amulet_necromatic_ward = placeholder();

	public static final Item ring_frozen_dagger = placeholder();
	public static final Item ring_legion = placeholder();
	public static final Item ring_nameless = placeholder();

	public static final Item charm_scarab = placeholder();
	public static final Item charm_bone_bag = placeholder();
	public static final Item charm_mushroom_minion = placeholder();

	//	public static final Item head_ = placeholder();

	//	public static final Item belt_ = placeholder();


	private NDItems() {} // No instances!

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		ItemRegistry.registerItemArtefact(registry, "amulet_slowness_immunity", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.AMULET));
		ItemRegistry.registerItemArtefact(registry, "amulet_weakness_immunity", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.AMULET));
		ItemRegistry.registerItemArtefact(registry, "amulet_malice_ward", NecromancersDelight.MODID, new ItemMaliceWard(EnumRarity.RARE, ItemArtefact.Type.AMULET));
		ItemRegistry.registerItemArtefact(registry, "amulet_necromatic_ward", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.AMULET));

		ItemRegistry.registerItemArtefact(registry, "ring_frozen_dagger", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.RING));
		ItemRegistry.registerItemArtefact(registry, "ring_legion", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING));
		ItemRegistry.registerItemArtefact(registry, "ring_nameless", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING));

		ItemRegistry.registerItemArtefact(registry, "charm_bone_bag", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.CHARM));
		ItemRegistry.registerItemArtefact(registry, "charm_scarab", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.EPIC, ItemArtefact.Type.CHARM));
		ItemRegistry.registerItemArtefact(registry, "charm_mushroom_minion", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.EPIC, ItemArtefact.Type.CHARM));


	}

}