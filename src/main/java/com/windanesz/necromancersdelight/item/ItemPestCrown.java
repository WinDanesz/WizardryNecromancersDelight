package com.windanesz.necromancersdelight.item;

import com.Fishmod.mod_LavaCow.entities.tameable.EntityWeta;
import com.windanesz.necromancersdelight.Settings;
import com.windanesz.necromancersdelight.registry.NDItems;
import com.windanesz.wizardryutils.capability.SummonedCreatureData;
import com.windanesz.wizardryutils.item.ITickableArtefact;
import com.windanesz.wizardryutils.item.ItemNewArtefact;
import electroblob.wizardry.data.IStoredVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.util.BlockUtils;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class ItemPestCrown extends ItemNewArtefact implements ITickableArtefact {

	public static final IStoredVariable<UUID> UUID_KEY = IStoredVariable.StoredVariable.ofUUID("wetaMinionUUID", Persistence.ALWAYS);

	public ItemPestCrown(EnumRarity rarity, Type type) {
		super(rarity, type);
		WizardData.registerStoredVariables(UUID_KEY);
	}

	@Override
	public void onWornTick(ItemStack itemStack, EntityLivingBase wearer) {
		if (!wearer.world.isRemote) {
			if (wearer.ticksExisted % 20 == 0 && wearer instanceof EntityPlayer && !((EntityPlayer) wearer).getCooldownTracker().hasCooldown(NDItems.head_pest_crown)) {
				WizardData data = WizardData.get((EntityPlayer)wearer);
				Entity oldWeta = EntityUtils.getEntityByUUID(wearer.world, data.getVariable(UUID_KEY));

				if (oldWeta == null) {
					World world = wearer.world;
					EntityPlayer caster = (EntityPlayer) wearer;

					BlockPos pos = BlockUtils.findNearbyFloorSpace(caster, 2, 4);
					if(pos == null) return;

					EntityWeta newWeta = new EntityWeta(world);
					newWeta.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
					newWeta.setTamed(true);
					newWeta.setOwnerId(caster.getUniqueID());

					// only need to apply the replacement logic once
					SummonedCreatureData minionData = SummonedCreatureData.get(newWeta);
					minionData.setCaster(caster);
					minionData.setLifetime(-1);
					minionData.setFollowOwner(true);
					world.spawnEntity(newWeta);

					data.setVariable(UUID_KEY, newWeta.getUniqueID());
					((EntityPlayer) wearer).getCooldownTracker().setCooldown(NDItems.head_pest_crown, Settings.generalSettings.head_pest_crown_cooldown);
				}
			}
		}
	}
}
