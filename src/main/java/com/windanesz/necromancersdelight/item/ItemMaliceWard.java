package com.windanesz.necromancersdelight.item;

import com.windanesz.necromancersdelight.Settings;
import com.windanesz.necromancersdelight.registry.NDItems;
import com.windanesz.wizardryutils.item.ITickableArtefact;
import com.windanesz.wizardryutils.tools.WizardryUtilsTools;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemMaliceWard extends ItemArtefact implements ITickableArtefact {

	public ItemMaliceWard(EnumRarity rarity, Type type) {
		super(rarity, type);
	}

	@Override
	public void onWornTick(ItemStack itemStack, EntityLivingBase wearer) {
		if (wearer.ticksExisted % 20 == 0 && wearer instanceof EntityPlayer && !((EntityPlayer) wearer).getCooldownTracker().hasCooldown(NDItems.amulet_malice_ward)) {

			boolean protect = false;
			for (EntityLivingBase entity : EntityUtils.getEntitiesWithinRadius(5, wearer.posX, wearer.posY, wearer.posZ, wearer.world, EntityLivingBase.class)) {
				if (WizardryUtilsTools.isEntityConsideredUndead(entity)) {
					protect = true;
					break;
				}
			}

			if (protect) {
				wearer.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 200, Settings.generalSettings.amulet_malice_ward_absorption_level));
				((EntityPlayer) wearer).getCooldownTracker().setCooldown(NDItems.amulet_malice_ward, Settings.generalSettings.amulet_malice_ward_cooldown);
			}
		}
	}
}
