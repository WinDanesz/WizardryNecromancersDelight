package com.windanesz.necromancersdelight.spell;

import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.entity.living.EntityManaLeechMinion;
import electroblob.wizardry.spell.SpellMinion;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class SummonManaLeech extends SpellMinion<EntityManaLeechMinion> {

	public SummonManaLeech(){
		super(NecromancersDelight.MODID,"summon_mana_leech", EntityManaLeechMinion::new);
		this.soundValues(7, 0.6f, 0);
	}

	@Override
	protected void addMinionExtras(EntityManaLeechMinion minion, BlockPos pos, EntityLivingBase caster, SpellModifiers modifiers, int alreadySpawned){
		minion.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		minion.setDropChance(EntityEquipmentSlot.MAINHAND, 0.0f);
	}

}
