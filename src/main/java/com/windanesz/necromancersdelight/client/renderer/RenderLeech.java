package com.windanesz.necromancersdelight.client.renderer;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelParasite;
import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.entity.living.EntityLeechMinionBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderLeech extends RenderLiving<EntityLeechMinionBase> {

	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(NecromancersDelight.MODID, "textures/entity/mana_leech.png")
	};

	public RenderLeech(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelParasite(), 0.3F);
	}

	@Override
	protected float getDeathMaxRotation(EntityLeechMinionBase entityLivingBaseIn) {
		 return 180F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLeechMinionBase entity) {
		return TEXTURES[0];
	}

	@Override
	protected void preRenderCallback(EntityLeechMinionBase entity, float partialTickTime) {
		if (entity.getRidingEntity() != null) {
			GlStateManager.scale(1.2F, 1.2F, 1.2F);
			GlStateManager.translate(0.0F, 0.0F, -0.4F);
			GlStateManager.rotate(180F, 0F, 1F, 0F);
			if((entity.getRidingEntity() instanceof EntityPlayer || entity.getRidingEntity() instanceof EntityZombie || entity.getRidingEntity() instanceof EntityVillager || entity.getRidingEntity() instanceof AbstractIllager || entity.getRidingEntity() instanceof EntitySkeleton) && !((EntityLivingBase)entity.getRidingEntity()).isChild()) {
				if(!(entity.getRidingEntity() instanceof EntityPlayer))GlStateManager.translate(0.0F, 0.3F, -0.3F);
				GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);

			}
		}
	}

}
