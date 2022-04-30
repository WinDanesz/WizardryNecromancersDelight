package com.windanesz.necromancersdelight.potion;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.windanesz.necromancersdelight.NecromancersDelight;
import electroblob.wizardry.constants.Constants;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardryPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber
public class PotionLocusts extends PotionMagicEffect implements ICustomPotionParticles {

	public PotionLocusts(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(NecromancersDelight.MODID, "textures/gui/potion_icons/locusts.png"));
		this.setPotionName("potion." + NecromancersDelight.MODID + ":locusts");
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
					"locust_swarm", world,
					x + (double)((new Random()).nextFloat() * 2.0F) - 1,
					y + (double)((new Random()).nextFloat() * 1),
					z + (double)((new Random()).nextFloat() * 2.0F) - 1,
					0.0D,
					0.0D,
					0.0D,
					0.0F,
					0.0F,
					0.0F);

		}
	}

	@SubscribeEvent
	public static void onBreakSpeedEvent(BreakSpeed event){
		if(event.getEntityPlayer().isPotionActive(WizardryPotions.frost)){
			// Amplifier + 1 because it starts at 0
			event.setNewSpeed(event.getOriginalSpeed() * (1 - Constants.FROST_FATIGUE_PER_LEVEL
					* (event.getEntityPlayer().getActivePotionEffect(WizardryPotions.frost).getAmplifier() + 1)));
		}
	}

	@Override
	public void performEffect(EntityLivingBase entity, int strength) {
		if (entity.isBurning() && entity.world.canSeeSky(entity.getPosition()) && entity.world.isDaytime() && !entity.isInLava()) {
			entity.extinguish();
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
}
