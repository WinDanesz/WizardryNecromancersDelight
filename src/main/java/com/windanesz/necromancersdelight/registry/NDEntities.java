package com.windanesz.necromancersdelight.registry;

import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.entity.projectile.EntityStinkBomb;
import com.windanesz.wizardryutils.registry.EntityRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class NDEntities {

	private NDEntities() {}
	/**
	 * Incrementing index for the mod-specific entity network ID.
	 */
	private static int id = 0;

	@SubscribeEvent
	public static void register(RegistryEvent.Register<EntityEntry> event) {

		IForgeRegistry<EntityEntry> registry = event.getRegistry();
		registry.register(EntityRegistry.createEntry(EntityStinkBomb.class, "stink_bomb", NecromancersDelight.MODID, EntityRegistry.TrackingType.PROJECTILE).build());

	}
}
