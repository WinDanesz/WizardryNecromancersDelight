package com.windanesz.necromancersdelight.item;

import electroblob.wizardry.Wizardry;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.item.IWorkbenchItem;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLeechCrystalAmulet extends ItemArtefact implements IWorkbenchItem {

	public ItemLeechCrystalAmulet(EnumRarity rarity, Type type) {
		super(rarity, type);
	}

	public static Element getElement(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("element")) {
			String element = stack.getTagCompound().getString("element");
			return Element.fromName(element.toLowerCase());
		}
		return Element.MAGIC;
	}

	public static ItemStack setElement(ItemStack stack, Element element) {
		NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();

		//noinspection ConstantConditions
		nbt.setString("element", element.name().toLowerCase());
		stack.setTagCompound(nbt);
		return stack;
	}

	@Override
	public int getSpellSlotCount(ItemStack stack) { return 0; }

	@Override
	public boolean onApplyButtonPressed(EntityPlayer player, Slot centre, Slot crystals, Slot upgrade, Slot[] spellBooks) {
		boolean changed = false;

		if (crystals.getStack() != ItemStack.EMPTY) {
			Element element = Element.values()[crystals.getStack().getMetadata()];

			if (element != Element.MAGIC) {

				ItemStack original = centre.getStack().copy();
				centre.putStack(setElement(centre.getStack(), element));
				changed = !ItemStack.areItemStacksEqual(centre.getStack(), original);
				if (changed) { crystals.decrStackSize(1); }
			}
		}
		return changed;
	}

	@Override
	public boolean showTooltip(ItemStack stack) {
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, world, tooltip, advanced);
		Wizardry.proxy.addMultiLineDescription(tooltip, "item." + this.getRegistryName() + ".desc2", getElement(stack).getDisplayName());
	}
}
