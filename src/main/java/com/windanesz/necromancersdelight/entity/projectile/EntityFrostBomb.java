package com.windanesz.necromancersdelight.entity.projectile;

import com.windanesz.necromancersdelight.registry.NDSpells;
import electroblob.wizardry.entity.projectile.EntityBomb;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.MagicDamage.DamageType;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.ParticleBuilder.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class EntityFrostBomb extends EntityBomb {

	public EntityFrostBomb(World world) {
		super(world);
	}

	@Override
	public int getLifetime() {
		return -1;
	}

	@Override
	protected void onImpact(RayTraceResult rayTrace) {

		Entity entityHit = rayTrace.entityHit;

		if (entityHit != null) {
			// This is if the frost bomb gets a direct hit
			float damage = NDSpells.frost_bomb.getProperty(Spell.DIRECT_DAMAGE).floatValue() * damageMultiplier;

			entityHit.attackEntityFrom(
					MagicDamage.causeIndirectMagicDamage(this, this.getThrower(), DamageType.FROST).setProjectile(), damage);

			if (entityHit instanceof EntityLivingBase && !MagicDamage.isEntityImmune(DamageType.FROST, entityHit)) {
				((EntityLivingBase) entityHit).addPotionEffect(new PotionEffect(WizardryPotions.frost,
						NDSpells.frost_bomb.getProperty(Spell.DIRECT_EFFECT_DURATION).intValue(),
						NDSpells.frost_bomb.getProperty(Spell.DIRECT_EFFECT_STRENGTH).intValue()));
			}
		}

		// Particle effect
		if (world.isRemote) {
			float brightness = 0.9f + (world.rand.nextFloat() / 10);
			ParticleBuilder.create(Type.FLASH).pos(this.getPositionVector()).scale(5 * blastMultiplier)
					.clr(brightness, brightness + 0.1f, 1).spawn(world);

			for (int i = 0; i < 60 * blastMultiplier; i++) {

				ParticleBuilder.create(Type.SPARKLE, rand, posX, posY, posZ, 2 * blastMultiplier, false).time(35)
						.scale(2).clr(brightness, brightness + 0.1f, 1).spawn(world);

				ParticleBuilder.create(Type.DARK_MAGIC, rand, posX, posY, posZ, 2 * blastMultiplier, false)
						.clr(brightness, brightness + 0.1f, 1).spawn(world);

				ParticleBuilder.create(Type.SNOW, rand, posX, posY, posZ, 2 * blastMultiplier, false)
						.clr(brightness, brightness + 0.1f, 1).spawn(world);
			}
			// Spawning this after the other particles fixes the rendering colour bug. It's a bit of a cheat, but it
			// works pretty well.
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 0, 0, 0);
		}

		if (!this.world.isRemote) {

			this.playSound(WizardrySounds.ENTITY_POISON_BOMB_SMASH, 1.5F, rand.nextFloat() * 0.4F + 0.6F);
			this.playSound(WizardrySounds.ENTITY_POISON_BOMB_POISON, 1.2F, 1.0f);

			double range = NDSpells.frost_bomb.getProperty(Spell.EFFECT_RADIUS).floatValue() * blastMultiplier;

			List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, this.posX, this.posY,
					this.posZ, this.world);

			for (EntityLivingBase target : targets) {
				if (target != entityHit && target != this.getThrower()
						&& !MagicDamage.isEntityImmune(DamageType.FROST, target)) {
					target.attackEntityFrom(
							MagicDamage.causeIndirectMagicDamage(this, this.getThrower(), DamageType.FROST),
							NDSpells.frost_bomb.getProperty(Spell.SPLASH_DAMAGE).floatValue() * damageMultiplier);
					target.addPotionEffect(new PotionEffect(WizardryPotions.frost,
							NDSpells.frost_bomb.getProperty(Spell.SPLASH_EFFECT_DURATION).intValue(),
							NDSpells.frost_bomb.getProperty(Spell.SPLASH_EFFECT_STRENGTH).intValue()));
				}
			}

			this.setDead();
		}
	}
}
