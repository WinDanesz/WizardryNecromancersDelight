package com.windanesz.necromancersdelight.registry;

import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.potion.PotionLocusts;
import com.windanesz.necromancersdelight.potion.PotionPoisonAura;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@GameRegistry.ObjectHolder(NecromancersDelight.MODID)
@Mod.EventBusSubscriber
public final class NDPotions {

	private NDPotions(){} // No instances!

	@Nonnull
	@SuppressWarnings("ConstantConditions")
	private static <T> T placeholder(){ return null; }

	public static final Potion locusts = placeholder();
	public static final Potion poisonous_spore = placeholder();

	public static void registerPotion(IForgeRegistry<Potion> registry, String name, Potion potion){
		potion.setRegistryName(NecromancersDelight.MODID, name);
		// For some reason, Potion#getName() doesn't prepend "potion." itself, so it has to be done here.
		potion.setPotionName("potion." + potion.getRegistryName().toString());
		registry.register(potion);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Potion> event){

		IForgeRegistry<Potion> registry = event.getRegistry();

		// Interestingly, setting the colour to black stops the particles from rendering.

		registerPotion(registry, "locusts", new PotionLocusts(false, 0));
		registerPotion(registry, "poisonous_spore", new PotionPoisonAura(false, 0));

	}

}