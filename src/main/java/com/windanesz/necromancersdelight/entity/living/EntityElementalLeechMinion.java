package com.windanesz.necromancersdelight.entity.living;

import electroblob.wizardry.constants.Element;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.util.MagicDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityElementalLeechMinion extends EntityLeechMinionBase {

	public static final DataParameter<Integer> ELEMENT = EntityDataManager.createKey(EntityElementalLeechMinion.class, DataSerializers.VARINT);

	public EntityElementalLeechMinion(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ELEMENT, 0);
	}

	@Nullable
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		this.setElement(Element.values()[rand.nextInt(Element.values().length - 1) + 1]);

		return super.onInitialSpawn(difficulty, livingdata);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		float dmg = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();

		if (target instanceof EntityLivingBase && !MagicDamage.isEntityImmune(getDamageType(), target)) {
			target.attackEntityFrom(MagicDamage.causeDirectMagicDamage(this, getDamageType()), dmg);

			if (getElement() == Element.FIRE) {
				target.setFire(2);
			} else {
				((EntityLivingBase) target).addPotionEffect(getPotionEffect());
			}
			return true;
		}

		return false;
	}

	public Element getElement() {
		return Element.values()[this.dataManager.get(ELEMENT)];
	}

	public void setElement(Element element) {
		this.dataManager.set(ELEMENT, element.ordinal());
	}

	public MagicDamage.DamageType getDamageType() {
		switch (getElement()) {
			case FIRE:
				return MagicDamage.DamageType.FIRE;
			case ICE:
				return MagicDamage.DamageType.FROST;
			case LIGHTNING:
				return MagicDamage.DamageType.SHOCK;
			case NECROMANCY:
				return MagicDamage.DamageType.WITHER;
			case EARTH:
				return MagicDamage.DamageType.POISON;
			case SORCERY:
				return MagicDamage.DamageType.FORCE;
			case HEALING:
				return MagicDamage.DamageType.RADIANT;
			default:
				return MagicDamage.DamageType.MAGIC;
		}
	}

	public PotionEffect getPotionEffect() {
		switch (getElement()) {
			case ICE:
				return new PotionEffect(WizardryPotions.frost, 40, 0);
			case LIGHTNING:
				return new PotionEffect(MobEffects.GLOWING, 40, 0);
			case NECROMANCY:
				return new PotionEffect(MobEffects.WITHER, 40, 0);
			case EARTH:
				return new PotionEffect(MobEffects.POISON, 40, 0);
			case SORCERY:
				return new PotionEffect(WizardryPotions.containment, 40, 0);
			case HEALING:
				return new PotionEffect(WizardryPotions.arcane_jammer, 40, 0);
			default:
				return new PotionEffect(MobEffects.SLOWNESS, 40, 0);
		}
	}
}
