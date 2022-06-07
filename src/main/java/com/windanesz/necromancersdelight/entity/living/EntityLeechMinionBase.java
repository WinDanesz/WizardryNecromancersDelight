package com.windanesz.necromancersdelight.entity.living;

import com.Fishmod.mod_LavaCow.entities.EntityParasite;
import com.Fishmod.mod_LavaCow.init.FishItems;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.entity.living.EntityZombieMinion;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.util.AllyDesignationSystem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityLeechMinionBase extends EntitySpider implements ISummonedCreature {

	private static final DataParameter<Boolean> SPAWN_PARTICLES = EntityDataManager.createKey(EntityZombieMinion.class, DataSerializers.BOOLEAN);

	// Field implementations
	private int lifetime = -1;
	private UUID casterUUID;

	public EntityLeechMinionBase(World world) {
		super(world);
		this.setSize(0.8F, 0.3F);
		this.experienceValue = 0;

	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SPAWN_PARTICLES, true);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(4, new AIParasiteAttack(this));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class,
				0, false, true, this.getTargetSelector()));
	}

	// Setter + getter implementations
	@Override
	public int getLifetime() { return lifetime; }

	@Override
	public void setLifetime(int lifetime) { this.lifetime = lifetime; }

	// Recommended overrides

	@Override
	public UUID getOwnerId() { return casterUUID; }

	@Override
	public void setOwnerId(UUID uuid) { this.casterUUID = uuid; }

	@Override
	protected int getExperiencePoints(EntityPlayer player) { return 0; }

	@Override
	protected boolean canDropLoot() { return false; }

	@Override
	protected Item getDropItem() { return null; }

	@Override
	protected ResourceLocation getLootTable() { return null; }

	@Override
	public boolean canPickUpLoot() { return false; }

	// This vanilla method has nothing to do with the custom despawn() method.
	@Override
	protected boolean canDespawn() {
		return getCaster() == null && getOwnerId() == null;
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	@Override
	public boolean canAttackClass(Class<? extends EntityLivingBase> entityType) {
		// Returns true unless the given entity type is a flying entity.
		return !EntityFlying.class.isAssignableFrom(entityType);
	}

	@Override
	public ITextComponent getDisplayName() {
		if (getCaster() != null) {
			return new TextComponentTranslation(NAMEPLATE_TRANSLATION_KEY, getCaster().getName(),
					new TextComponentTranslation("entity." + this.getEntityString() + ".name"));
		} else {
			return super.getDisplayName();
		}
	}

	@Override
	public boolean hasCustomName() {
		// If this returns true, the renderer will show the nameplate when looking directly at the entity
		return Wizardry.settings.summonedCreatureNames && getCaster() != null;
	}

	@Override
	public void onUpdate() {

		if (this.getRidingEntity() != null) {
			this.setPositionAndRotation(getRidingEntity().posX, getRidingEntity().posY, getRidingEntity().posZ, getRidingEntity().rotationYaw, getRidingEntity().rotationPitch);
		}

		super.onUpdate();
		this.updateDelegate();
	}

	@Override
	public void onLivingUpdate() {
		if (this.getRidingEntity() != null && this.getRidingEntity() instanceof EntityLivingBase && this.isServerWorld()) {
			attachedLogic();
		}

		super.onLivingUpdate();
	}

	public void attachedLogic() {
		if (this.getRidingEntity() != null && this.getRidingEntity().isBurning()) {
			this.setFire(20);
		}
	}

	@Override
	public void setRevengeTarget(EntityLivingBase entity) {
		if (this.shouldRevengeTarget(entity)) { super.setRevengeTarget(entity); }
	}

	@Override
	public void onSpawn() {
		if (this.dataManager.get(SPAWN_PARTICLES)) { this.spawnParticleEffect(); }
	}

	@Override
	public void onDespawn() {
		this.spawnParticleEffect();
	}

	private void spawnParticleEffect() {
		if (this.world.isRemote) {
			for (int i = 0; i < 15; i++) {
				this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + this.rand.nextFloat() - 0.5f,
						this.posY + this.rand.nextFloat() * 2, this.posZ + this.rand.nextFloat() - 0.5f, 0, 0, 0);
			}
		}
	}

	@Override
	public boolean hasParticleEffect() { return true; }

	@Override
	public boolean hasAnimation() {
		return this.dataManager.get(SPAWN_PARTICLES) || this.ticksExisted > 20;
	}

	public void hideParticles() {
		this.dataManager.set(SPAWN_PARTICLES, false);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		// In this case, the delegate method determines whether super is called.
		// Rather handily, we can make use of Java's short-circuiting method of evaluating OR statements.
		return this.interactDelegate(player, hand) || super.processInteract(player, hand);
	}

	protected void collideWithEntity(Entity entityIn) {
		if (!this.world.isRemote && entityIn instanceof EntityLivingBase && !AllyDesignationSystem.isAllied((EntityLivingBase) entityIn,this) && !(entityIn instanceof EntityParasite)  && !(entityIn instanceof EntityLeechMinionBase)) {
			this.startRiding(entityIn);
			this.getServer().getPlayerList().sendPacketToAllPlayers(new SPacketSetPassengers(entityIn));
			this.isJumping = false;
			this.navigator.clearPath();
			setLifetime(-1);
			this.setAttackTarget((EntityLivingBase) entityIn);
		}
	}

	static class AIParasiteAttack extends EntityAIAttackMelee {
		public AIParasiteAttack(EntityLeechMinionBase leech) {
			super(leech, 1.0D, true);
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			float f = this.attacker.getBrightness();

			if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
				this.attacker.setAttackTarget((EntityLivingBase) null);
				return false;
			} else {
				return super.shouldContinueExecuting();
			}
		}

		protected double getAttackReachSqr(EntityLivingBase attackTarget) {
			return (double) (attackTarget.width + 0.1F);
		}
	}

	@Override
	public float getEyeHeight()
	{
		return 0.1F;
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return FishItems.ENTITY_PARASITE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return FishItems.ENTITY_PARASITE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return FishItems.ENTITY_PARASITE_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15F, 1.0F);
	}
}
