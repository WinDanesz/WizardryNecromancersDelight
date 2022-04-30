package com.windanesz.necromancersdelight.handler;

import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityUnburied;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.windanesz.necromancersdelight.registry.NDItems;
import com.windanesz.necromancersdelight.registry.NDPotions;
import com.windanesz.wizardryutils.capability.SummonedCreatureData;
import com.windanesz.wizardryutils.integration.baubles.BaublesIntegration;
import com.windanesz.wizardryutils.tools.WizardryUtilsTools;
import electroblob.wizardry.constants.SpellType;
import electroblob.wizardry.entity.living.EntityZombieMinion;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.util.BlockUtils;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/**
 * Most artefact effects of the mod are handled here.
 * Author @WinDanesz
 */
@Mod.EventBusSubscriber
public class NDEventHandler {

	@SubscribeEvent
	public static void onPotionApplicableEvent(PotionEvent.PotionApplicableEvent event) {

		if (event.getEntity() instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) event.getEntity();

			for (ItemArtefact artefact : ItemArtefact.getActiveArtefacts(player)) {

				if (artefact == NDItems.amulet_slowness_immunity) {

					if (event.getPotionEffect().getPotion() == MobEffects.SLOWNESS) { event.setResult(Event.Result.DENY); }

				} else if (artefact == NDItems.amulet_weakness_immunity) {

					if (event.getPotionEffect().getPotion() == MobEffects.WEAKNESS) { event.setResult(Event.Result.DENY); }
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeathEvent(LivingDeathEvent event) {

		if (event.getSource().getTrueSource() instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

			if (ItemArtefact.isArtefactActive(player, NDItems.charm_bone_bag)) {

				if (event.getEntity() instanceof AbstractSkeleton || event.getEntity() instanceof EntitySkeletonHorse) {
					List<ItemStack> stackList = BaublesIntegration.getEquippedArtefactStacks(player, ItemArtefact.Type.CHARM);
					if (!stackList.isEmpty()) {
						ItemStack bag = addCharmProgress(stackList.get(0), 25);
						BaublesIntegration.setArtefactToSlot(player, bag, ItemArtefact.Type.CHARM);
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onSpellCastPreEvent(SpellCastEvent.Post event) {

		if (!event.getWorld().isRemote && event.getCaster() instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) event.getCaster();
			SpellModifiers modifiers = event.getModifiers();

			if (event.getSpell().getType() == SpellType.MINION && ItemArtefact.isArtefactActive(player, NDItems.charm_bone_bag)) {
				ItemStack bag = BaublesIntegration.getEquippedArtefactStacks(player, ItemArtefact.Type.CHARM).get(0);
				if (bag.hasTagCompound() && bag.getTagCompound().hasKey("progress") && bag.getTagCompound().getInteger("progress") == 100) {
					// Try and find a nearby floor space
					BlockPos pos = BlockUtils.findNearbyFloorSpace(player, 5, 3);
					if (pos != null) {
						EntityBoneWorm osVermis = new EntityBoneWorm(player.world);
						SummonedCreatureData minionData = SummonedCreatureData.get(osVermis);
						osVermis.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
						minionData.setCaster(player);
						minionData.setLifetime((int) (800 * modifiers.get(WizardryItems.duration_upgrade)));
						player.world.spawnEntity(osVermis);
						BaublesIntegration.setArtefactToSlot(player, addCharmProgress(bag, 0), ItemArtefact.Type.CHARM);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingSpawnEvent(EntityJoinWorldEvent event) {
		EntityPlayer player = null;

		if (event.getEntity() instanceof ISummonedCreature && ((ISummonedCreature) event.getEntity()).getCaster() instanceof EntityPlayer) {
			player = (EntityPlayer) ((ISummonedCreature) event.getEntity()).getCaster();
		} else if (SummonedCreatureData.isSummonedEntity(event.getEntity()) && SummonedCreatureData.get((EntityLiving) event.getEntity()).getCaster() instanceof EntityPlayer) {
			player = (EntityPlayer) SummonedCreatureData.get((EntityLiving) event.getEntity()).getCaster();
		}

		if (player != null) {

			if (ItemArtefact.isArtefactActive(player, NDItems.charm_mushroom_minion) && !player.getHeldItemOffhand().isEmpty()) {
				EntityLiving minion = (EntityLiving) event.getEntity();

				ItemStack offHandStack = player.getHeldItemOffhand();
				Item item = offHandStack.getItem();
				if (item == Item.getItemFromBlock(Modblocks.VEIL_SHROOM)) {
					// invisibility
					//if (!minion.world.isRemote) {
					minion.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 600));
					//}
					offHandStack.shrink(1);
				} else if (item == Item.getItemFromBlock(Modblocks.CORDY_SHROOM)) {
					// poisionus spore effect
					// TODO:
					//  minion.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 600));
					offHandStack.shrink(1);
				} else if (item == Item.getItemFromBlock(Modblocks.BLOODTOOTH_SHROOM)) {
					// regeneration
					minion.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600));
					offHandStack.shrink(1);
				} else if (item == Item.getItemFromBlock(Modblocks.GLOWSHROOM)) {
					// Speed and glow
					minion.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 1));
					minion.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 600));
					offHandStack.shrink(1);
				}
			} else if (ItemArtefact.isArtefactActive(player, NDItems.charm_scarab) && WizardryUtilsTools.isEntityConsideredUndead(event.getEntity()) ) {
				((EntityLiving) event.getEntity()).addPotionEffect(new PotionEffect(NDPotions.locusts, Integer.MAX_VALUE, 0));
			}

			if (event.getEntity() instanceof EntityZombieMinion) {
				EntityZombieMinion zombie = (EntityZombieMinion) event.getEntity();
				Entity owner = zombie.getOwner();
				if (owner instanceof EntityPlayer) {

					int unburiedRings = 0;
					boolean gotHelmetCharm = false;
					for (ItemArtefact artefact : ItemArtefact.getActiveArtefacts((EntityPlayer) owner)) {
						if (artefact == NDItems.ring_legion || artefact == NDItems.ring_nameless) {
							unburiedRings++;
						}
						if (artefact == WizardryItems.charm_undead_helmets) {
							gotHelmetCharm = true;
						}
					}
					if (unburiedRings > 0) {
						// only need to apply the replacement logic once
						EntityUnburied minion = new EntityUnburied(event.getWorld());
						SummonedCreatureData minionData = SummonedCreatureData.get(minion);
						minion.setPosition(zombie.posX, zombie.posY, zombie.posZ);
						minionData.setCaster((EntityPlayer) owner);
						minionData.setLifetime(zombie.getLifetime());

						if (unburiedRings == 2) {
							// got the set; if also have the charm_undead_helmets artefact, having a helmet is guaranteed and it picks another piece
							int i = event.getWorld().rand.nextInt(gotHelmetCharm ? 4 : 5);
							switch (i) {
								case 0:
									minion.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SHOVEL));
									break;
								case 1:
									minion.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
									break;
								case 2:
									minion.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
									break;
								case 3:
									minion.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
									break;
								case 4:
									minion.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
									break;
								case 5:
									minion.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
							}
							if (gotHelmetCharm) { minion.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET)); }
						}
						event.getWorld().spawnEntity(minion);
						event.setCanceled(true);
					}
				}
			}
		}

	}

	@SubscribeEvent
	public static void onLivingDamageEvent(LivingDamageEvent event) {
		if (event.getEntity() instanceof EntityPlayer && event.getSource().getTrueSource() != null) {
			if (WizardryUtilsTools.isEntityConsideredUndead(event.getSource().getTrueSource()) && (ItemArtefact.isArtefactActive((EntityPlayer) event.getEntity(), NDItems.amulet_necromatic_ward))) {
				event.setAmount(event.getAmount() * 0.85f);
			}
		}
	}

	private static ItemStack addCharmProgress(ItemStack stack, int newProgress) {
		NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
		int progress = 0;
		if (newProgress != 0) {
			progress = nbt.hasKey("progress") ? Math.min(100, nbt.getInteger("progress") + newProgress) : newProgress;
		}
		nbt.setInteger("progress", progress);
		stack.setTagCompound(nbt);
		return stack;
	}

}
