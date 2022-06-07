package com.windanesz.necromancersdelight.spell;

import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.entity.living.EntityElementalLeechMinion;
import com.windanesz.necromancersdelight.item.ItemLeechCrystalAmulet;
import com.windanesz.necromancersdelight.registry.NDItems;
import com.windanesz.wizardryutils.integration.baubles.BaublesIntegration;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.spell.SpellMinion;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class SummonElementalLeech extends SpellMinion<EntityElementalLeechMinion> {

	public SummonElementalLeech() {
		super(NecromancersDelight.MODID, "summon_elemental_leech", EntityElementalLeechMinion::new);
		this.soundValues(7, 0.6f, 0);
	}

	@Override
	protected void addMinionExtras(EntityElementalLeechMinion minion, BlockPos pos, EntityLivingBase caster, SpellModifiers modifiers, int alreadySpawned) {
		minion.setDropChance(EntityEquipmentSlot.MAINHAND, 0.0f);

		Element element = Element.values()[minion.world.rand.nextInt(Element.values().length - 1) + 1];
		if (caster instanceof EntityPlayer && ItemArtefact.isArtefactActive((EntityPlayer) caster, NDItems.amulet_leech_crystal)) {
			ItemStack stack = BaublesIntegration.getEquippedArtefactStacks((EntityPlayer) caster, ItemArtefact.Type.AMULET).get(0);
			Element stackElement = ItemLeechCrystalAmulet.getElement(stack);
			if (stackElement != Element.MAGIC) {
				element = stackElement;
			}
		}

		minion.setElement(element);
		minion.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(
				new AttributeModifier(POTENCY_ATTRIBUTE_MODIFIER, modifiers.get(SpellModifiers.POTENCY) - 1 * 0.7f, EntityUtils.Operations.MULTIPLY_CUMULATIVE));
		minion.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(
				new AttributeModifier(POTENCY_ATTRIBUTE_MODIFIER, modifiers.amplified(SpellModifiers.POTENCY, 1.5f) - 1, EntityUtils.Operations.MULTIPLY_CUMULATIVE));
		minion.setHealth(minion.getMaxHealth());
	}

}
