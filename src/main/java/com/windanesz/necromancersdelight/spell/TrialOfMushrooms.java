package com.windanesz.necromancersdelight.spell;

import com.Fishmod.mod_LavaCow.init.Modblocks;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class TrialOfMushrooms extends Spell {

	public static final String BLOODTOOTH_AMPLIFIER = "bloodtooth_amplifier";
	public static final String BLOODTOOTH_DURATION = "bloodtooth_duration";
	public static final String VEILSHROOM_AMPLIFIER = "veilshroom_amplifier";
	public static final String VEILSHROOM_DURATION = "veilshroom_duration";
	public static final String CORDYCEPS_AMPLIFIER = "cordyceps_amplifier";
	public static final String CORDYCEPS_DURATION = "cordyceps_duration";
	public static final String GLOWSHROOM_AMPLIFIER = "glowshroom_amplifier";
	public static final String GLOWSHROOM_DURATION = "glowshroom_duration";

	public TrialOfMushrooms(String modID, String name, EnumAction action, boolean isContinuous) {
		super(modID, name, action, isContinuous);
		addProperties(BLOODTOOTH_AMPLIFIER, BLOODTOOTH_DURATION, VEILSHROOM_AMPLIFIER, VEILSHROOM_DURATION, CORDYCEPS_AMPLIFIER,
				CORDYCEPS_DURATION, GLOWSHROOM_AMPLIFIER, GLOWSHROOM_DURATION);
	}

	@Override
	public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
		ItemStack offHandStack = caster.getHeldItemOffhand();
		Item item = offHandStack.getItem();
		if (offHandStack.isEmpty()) {
			if (!world.isRemote) {
				caster.sendStatusMessage(new TextComponentTranslation("spell.necromancersdelight:trial_of_mushrooms.missing_item"), false);
			}
			return false;
		}

		if (item == Item.getItemFromBlock(Modblocks.VEIL_SHROOM)) {
			caster.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, getProperty(VEILSHROOM_DURATION).intValue()));
			offHandStack.shrink(1);
			return true;
		} else if (item == Item.getItemFromBlock(Modblocks.CORDY_SHROOM)) {
			caster.addPotionEffect(new PotionEffect(MobEffects.POISON, 60, 1));
			caster.addPotionEffect(new PotionEffect(MobEffects.HASTE, getProperty(CORDYCEPS_DURATION).intValue(), getProperty(CORDYCEPS_AMPLIFIER).intValue()));
			caster.addPotionEffect(new PotionEffect(MobEffects.SPEED, getProperty(CORDYCEPS_DURATION).intValue(), getProperty(CORDYCEPS_AMPLIFIER).intValue()));
			offHandStack.shrink(1);
			return true;
		} else if (item == Item.getItemFromBlock(Modblocks.BLOODTOOTH_SHROOM)) {
			caster.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, getProperty(BLOODTOOTH_DURATION).intValue(), getProperty(BLOODTOOTH_AMPLIFIER).intValue()));
			offHandStack.shrink(1);
			return true;
		} else if (item == Item.getItemFromBlock(Modblocks.GLOWSHROOM)) {
			caster.addPotionEffect(new PotionEffect(MobEffects.GLOWING, getProperty(GLOWSHROOM_DURATION).intValue(), getProperty(GLOWSHROOM_AMPLIFIER).intValue()));
			caster.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, getProperty(GLOWSHROOM_DURATION).intValue(), getProperty(GLOWSHROOM_AMPLIFIER).intValue()));
			offHandStack.shrink(1);
			return true;
		}

		return false;
	}
}
