package com.windanesz.necromancersdelight.client;

import com.Fishmod.mod_LavaCow.client.renders.entity.RenderVespa;
import com.windanesz.necromancersdelight.CommonProxy;
import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.client.renderer.RenderElementalLeech;
import com.windanesz.necromancersdelight.client.renderer.RenderLeech;
import com.windanesz.necromancersdelight.entity.living.EntityElementalLeechMinion;
import com.windanesz.necromancersdelight.entity.living.EntityManaLeechMinion;
import com.windanesz.necromancersdelight.entity.living.EntityVespaMinion;
import com.windanesz.necromancersdelight.entity.projectile.EntityFrostBomb;
import com.windanesz.necromancersdelight.entity.projectile.EntityStinkBomb;
import electroblob.wizardry.client.renderer.entity.RenderProjectile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	/**
	 * Called from preInit() in the main mod class to initialise the renderers.
	 */
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityStinkBomb.class,
				manager -> new RenderProjectile(manager, 0.6f, new ResourceLocation(NecromancersDelight.MODID, "textures/items/stink_bomb.png"), false));
		RenderingRegistry.registerEntityRenderingHandler(EntityFrostBomb.class,
				manager -> new RenderProjectile(manager, 0.6f, new ResourceLocation(NecromancersDelight.MODID, "textures/items/frost_bomb.png"), false));
		RenderingRegistry.registerEntityRenderingHandler(EntityManaLeechMinion.class,
				manager -> new RenderLeech(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityElementalLeechMinion.class,
				manager -> new RenderElementalLeech(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityVespaMinion.class,
				manager -> new RenderVespa(manager));
	}
}