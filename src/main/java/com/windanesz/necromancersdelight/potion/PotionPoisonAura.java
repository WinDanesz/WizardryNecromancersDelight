package com.windanesz.necromancersdelight.potion;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.windanesz.necromancersdelight.NecromancersDelight;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.AllyDesignationSystem;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class PotionPoisonAura extends PotionMagicEffect implements ICustomPotionParticles {

	public PotionPoisonAura(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(NecromancersDelight.MODID, "textures/gui/potion_icons/poisonous_spore.png"));
		this.setPotionName("potion." + NecromancersDelight.MODID + ":poisonous_spore");
	}

	@Override
	public void spawnCustomParticle(World world, double x, double y, double z){
		if (world.rand.nextInt(5) == 0 && world.isRemote) {
			Vec3d[] spore_color = new Vec3d[]{new Vec3d(0.83D, 0.73D, 0.5D), new Vec3d(0.0D, 0.98D, 0.93D)};
//			mod_LavaCow.PROXY.spawnCustomParticle(
//					"spore", world,
//					x + (double)((new Random()).nextFloat() * 2.0F) - 1,
//					y + (double)((new Random()).nextFloat()),
//					z + (double)((new Random()).nextFloat() * 1 * 2.0F) -1,
//					0.0D,
//					0.0D,
//					0.0D, (float)spore_color[0].x, (float)spore_color[0].y, (float)spore_color[0].z);

			mod_LavaCow.PROXY.spawnCustomParticle(
					"spore", world,
					x + (double)((new Random()).nextFloat() * 2.0F) - 1,
					y + (double)((new Random()).nextFloat() * 1),
					z + (double)((new Random()).nextFloat() * 2.0F) - 1,
					0.0D,0.0D, 0.0D,
					0.83F, 0.73F, 0.5F);

		}
	}

	@Override
	public void performEffect(EntityLivingBase entity, int strength) {
		if (entity.ticksExisted % 19 == 0) {
			for (EntityLivingBase target : EntityUtils.getEntitiesWithinRadius(2, entity.posX, entity.posY, entity.posZ, entity.world, EntityLivingBase.class)) {
				if (target != entity && !AllyDesignationSystem.isAllied(entity, target)) {
					target.addPotionEffect(new PotionEffect(MobEffects.POISON, 80, strength));
				}
			}
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
}
