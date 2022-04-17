package com.windanesz.necromancersdelight.registry;

import com.Fishmod.mod_LavaCow.init.FishItems;
import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.wizardryutils.spell.SpellDynamicConjuration;
import electroblob.wizardry.spell.Spell;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@ObjectHolder(NecromancersDelight.MODID)
@EventBusSubscriber
public final class NDSpells {

	public static final Spell bound_frozen_dagger = placeholder();

	private NDSpells() {} // no instances

	@Nonnull
	@SuppressWarnings("ConstantConditions")
	private static <T> T placeholder() { return null; }

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Spell> event) {

		IForgeRegistry<Spell> registry = event.getRegistry();

		registry.register(new SpellDynamicConjuration(NecromancersDelight.MODID, "bound_frozen_dagger", FishItems.FROZEN_DAGGER));
	}
}
