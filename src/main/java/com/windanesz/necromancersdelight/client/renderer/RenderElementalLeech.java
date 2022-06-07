package com.windanesz.necromancersdelight.client.renderer;

import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.entity.living.EntityElementalLeechMinion;
import com.windanesz.necromancersdelight.entity.living.EntityLeechMinionBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderElementalLeech extends RenderLeech {

	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(NecromancersDelight.MODID, "textures/entity/leech_magic.png"),
			new ResourceLocation(NecromancersDelight.MODID, "textures/entity/leech_fire.png"),
			new ResourceLocation(NecromancersDelight.MODID, "textures/entity/leech_ice.png"),
			new ResourceLocation(NecromancersDelight.MODID, "textures/entity/leech_lightning.png"),
			new ResourceLocation(NecromancersDelight.MODID, "textures/entity/leech_necromancy.png"),
			new ResourceLocation(NecromancersDelight.MODID, "textures/entity/leech_earth.png"),
			new ResourceLocation(NecromancersDelight.MODID, "textures/entity/leech_sorcery.png"),
			new ResourceLocation(NecromancersDelight.MODID, "textures/entity/leech_healing.png")};

	public RenderElementalLeech(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLeechMinionBase entity) {
		return TEXTURES[((EntityElementalLeechMinion) entity).getElement().ordinal()];
	}
}
