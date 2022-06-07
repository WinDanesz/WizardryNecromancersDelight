package com.windanesz.necromancersdelight.entity.living;

import com.windanesz.necromancersdelight.registry.NDItems;
import electroblob.wizardry.item.IManaStoringItem;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityManaLeechMinion extends EntityLeechMinionBase {

	private boolean weakensSpells = false;

	public EntityManaLeechMinion(World world) {
		super(world);
	}

	public boolean isWeakensSpells() { return weakensSpells; }

	public void setWeakensSpells(boolean weakensSpells) { this.weakensSpells = weakensSpells; }

	public void attachedLogic() {
		super.attachedLogic();
		if (this.getRidingEntity() != null && this.getRidingEntity().ticksExisted % 20 == 0) {
			attackEntityAsMob(this.getRidingEntity());
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		if (target instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) target;
			if (entity.getHeldItemMainhand().getItem() instanceof IManaStoringItem) {
				stealMana(entity, EnumHand.MAIN_HAND);
			}
			if (entity.getHeldItemOffhand().getItem() instanceof IManaStoringItem) {
				stealMana(entity, EnumHand.OFF_HAND);
			}
		}
		return true;
		//return super.attackEntityAsMob(target);
	}

	private void stealMana(EntityLivingBase target, EnumHand manaSourceHand) {
		int oldMana = ((IManaStoringItem) target.getHeldItem(manaSourceHand).getItem()).getMana(target.getHeldItem(manaSourceHand));
		int stolen = (int) (oldMana * 0.05);
		((IManaStoringItem) target.getHeldItem(manaSourceHand).getItem()).consumeMana(target.getHeldItem(manaSourceHand), stolen, target);

		if (this.getCaster() instanceof EntityPlayer && ItemArtefact.isArtefactActive((EntityPlayer) this.getCaster(), NDItems.amulet_leechlink)) {
			EntityPlayer caster = (EntityPlayer) this.getCaster();

			if (caster.getHeldItemMainhand().getItem() instanceof IManaStoringItem) {
				ItemStack stack = this.getCaster().getHeldItem(EnumHand.MAIN_HAND);
				((IManaStoringItem) stack.getItem()).rechargeMana(stack, (int) (stolen * 0.5f));
				caster.setHeldItem(EnumHand.MAIN_HAND, stack);
			} else if (caster.getHeldItemOffhand().getItem() instanceof IManaStoringItem) {
				ItemStack stack = this.getCaster().getHeldItem(EnumHand.OFF_HAND);
				((IManaStoringItem) stack.getItem()).rechargeMana(stack, (int) (stolen * 0.5f));
				caster.setHeldItem(EnumHand.OFF_HAND, stack);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("weakensSpells", weakensSpells);
		this.writeNBTDelegate(nbt);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.readNBTDelegate(nbt);
		if (nbt.hasKey("weakensSpells")) {
			setWeakensSpells(nbt.getBoolean("weakensSpells"));
		}
	}

}
