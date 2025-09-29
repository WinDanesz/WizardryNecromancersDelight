package com.windanesz.necromancersdelight.spell;

import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Carcinize extends SpellRay {

	public Carcinize(String modid, String name) {
		super(modid, name, SpellActions.POINT, false);
		this.ignoreLivingEntities(true);
	}

	@Override
	protected boolean onEntityHit(World world, Entity entity, Vec3d vec3d, @Nullable EntityLivingBase entityLivingBase, Vec3d vec3d1, int i, SpellModifiers spellModifiers) {
		return false;
	}

	@Override
	protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {

		// Check if the targeted block is a chest
		if (!(world.getBlockState(pos).getBlock() instanceof BlockChest)) {
			if (caster instanceof EntityPlayer && !world.isRemote) {
				caster.sendMessage(new TextComponentTranslation("spell.necromancersdelight:carcinize.not_chest"));
			}
			return false;
		}

		// Check if caster is a player
		if (!(caster instanceof EntityPlayer)) {
			return false;
		}

		EntityPlayer player = (EntityPlayer) caster;

		// Check if player has a Crystal
		boolean hasCrystal = false;
		ItemStack crystalStack = ItemStack.EMPTY;

		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (stack.getItem() == WizardryItems.magic_crystal && stack.getMetadata() == 5) {
				hasCrystal = true;
				crystalStack = stack;
				break;
			}
		}

		if (!hasCrystal) {
			if (!world.isRemote) {
				player.sendMessage(new TextComponentTranslation("spell.necromancersdelight:carcinize.no_crystal"));
			}
			return false;
		}

		// Consume the Crystal
		if (!world.isRemote) {
			crystalStack.shrink(1);

			// Break the chest and drop its contents
			world.destroyBlock(pos, true);

			// Spawn the tamed Mimicrab
			EntityMimic mimicrab = new EntityMimic(world);
			mimicrab.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
			mimicrab.setTamed(true);
			mimicrab.setOwnerId(player.getUniqueID());
			world.spawnEntity(mimicrab);
		}

		return true;
	}

	@Override
	protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
		return false;
	}
}
