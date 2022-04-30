package com.windanesz.necromancersdelight.client;

import com.windanesz.necromancersdelight.CommonProxy;
import com.windanesz.necromancersdelight.NecromancersDelight;
import electroblob.wizardry.client.renderer.entity.RenderProjectile;
import electroblob.wizardry.entity.projectile.EntityPoisonBomb;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityPoisonBomb.class,
				manager -> new RenderProjectile(manager, 0.6f, new ResourceLocation(NecromancersDelight.MODID, "textures/items/stink_bomb.png"), false));
	}
}