package com.windanesz.necromancersdelight.registry;

import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.entity.projectile.EntityFrostBomb;
import com.windanesz.necromancersdelight.entity.projectile.EntityStinkBomb;
import com.windanesz.necromancersdelight.item.ItemFrostBomb;
import com.windanesz.necromancersdelight.item.ItemLeechCrystalAmulet;
import com.windanesz.necromancersdelight.item.ItemMaliceWard;
import com.windanesz.necromancersdelight.item.ItemPestCrown;
import com.windanesz.necromancersdelight.item.ItemStinkBomb;
import com.windanesz.wizardryutils.item.ItemNewArtefact;
import com.windanesz.wizardryutils.registry.ItemRegistry;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
	public static final Item amulet_necromantic_ward = placeholder();
	public static final Item amulet_leechlink = placeholder();
	public static final Item amulet_leech_crystal = placeholder();

	public static final Item ring_legion = placeholder();
	public static final Item ring_nameless = placeholder();
	public static final Item ring_frozen_dagger = placeholder();
	public static final Item ring_forsaken = placeholder();

	public static final Item charm_bone_bag = placeholder();
	public static final Item charm_mummy_minion = placeholder();
	public static final Item charm_scarab = placeholder();
	public static final Item charm_mushroom_minion = placeholder();
	public static final Item head_pest_crown = placeholder();

	//	public static final Item head_ = placeholder();

	public static final Item stink_bomb = placeholder();
	public static final Item frost_bomb = placeholder();


	private NDItems() {} // No instances!

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		ItemRegistry.registerItemArtefact(registry, "amulet_necromantic_ward", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.AMULET));
		ItemRegistry.registerItemArtefact(registry, "amulet_slowness_immunity", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.AMULET));
		ItemRegistry.registerItemArtefact(registry, "amulet_weakness_immunity", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.AMULET));
		ItemRegistry.registerItemArtefact(registry, "amulet_malice_ward", NecromancersDelight.MODID, new ItemMaliceWard(EnumRarity.RARE, ItemArtefact.Type.AMULET));
		ItemRegistry.registerItemArtefact(registry, "amulet_leechlink", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.AMULET));

		ItemRegistry.registerItemArtefact(registry, "ring_legion", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING));
		ItemRegistry.registerItemArtefact(registry, "ring_nameless", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING));
		ItemRegistry.registerItemArtefact(registry, "ring_frozen_dagger", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.RING));
		ItemRegistry.registerItemArtefact(registry, "ring_forsaken", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.EPIC, ItemArtefact.Type.RING));

		ItemRegistry.registerItemArtefact(registry, "charm_bone_bag", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.CHARM));
		ItemRegistry.registerItemArtefact(registry, "charm_mummy_minion", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.RARE, ItemArtefact.Type.CHARM));
		ItemRegistry.registerItemArtefact(registry, "charm_scarab", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.EPIC, ItemArtefact.Type.CHARM));
		ItemRegistry.registerItemArtefact(registry, "charm_mushroom_minion", NecromancersDelight.MODID, new ItemArtefact(EnumRarity.EPIC, ItemArtefact.Type.CHARM));


		ItemRegistry.registerItemArtefact(registry, "head_pest_crown", NecromancersDelight.MODID, new ItemPestCrown(EnumRarity.EPIC, ItemNewArtefact.Type.HEAD));


		ItemRegistry.registerItem(registry, "stink_bomb", NecromancersDelight.MODID, new ItemStinkBomb());
		ItemRegistry.registerItem(registry, "frost_bomb", NecromancersDelight.MODID, new ItemFrostBomb());
		ItemRegistry.registerItem(registry, "amulet_leech_crystal", NecromancersDelight.MODID, new ItemLeechCrystalAmulet(EnumRarity.RARE, ItemArtefact.Type.AMULET));
	}

	/** Called from init() in the main mod class to register wizardry's dispenser behaviours. */
	public static void registerDispenseBehaviours(){

		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(stink_bomb, new BehaviorProjectileDispense(){

			@Override
			protected IProjectile getProjectileEntity(World world, IPosition position, ItemStack stack){
				EntityStinkBomb entity = new EntityStinkBomb(world);
				entity.setPosition(position.getX(), position.getY(), position.getZ());
				return entity;
			}

		});

		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(frost_bomb, new BehaviorProjectileDispense(){

			@Override
			protected IProjectile getProjectileEntity(World world, IPosition position, ItemStack stack){
				EntityFrostBomb entity = new EntityFrostBomb(world);
				entity.setPosition(position.getX(), position.getY(), position.getZ());
				return entity;
			}

		});
	}
}