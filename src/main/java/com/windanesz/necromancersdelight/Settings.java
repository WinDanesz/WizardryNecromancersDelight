package com.windanesz.necromancersdelight;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = NecromancersDelight.MODID, name = "necromancersdelight") // No fancy configs here so we can use the annotation, hurrah!
public class Settings {


	@SuppressWarnings("unused")
	@Mod.EventBusSubscriber(modid = NecromancersDelight.MODID)
	private static class EventHandler {
		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(NecromancersDelight.MODID)) {
				ConfigManager.sync(NecromancersDelight.MODID, Config.Type.INSTANCE);
			}
		}
	}

	@Config.Name("General Settings")
	@Config.LangKey("settings.necromancersdelight:general_settings")
	public static GeneralSettings generalSettings = new GeneralSettings();

	public static class GeneralSettings {
		@Config.Name("Malice Ward Absorption Amount")
		@Config.Comment("Sets the Absorption level the Malice Ward amulet gives.")
		@Config.RequiresMcRestart
		public int amulet_malice_ward_absorption_level = 2;

		@Config.Name("Malice Ward Cooldown")
		@Config.Comment("Sets the cooldown of the Malice Ward amulet (in ticks, 20 = 1 second).")
		@Config.RequiresMcRestart
		public int amulet_malice_ward_cooldown = 1200;

		@Config.Name("Crown of Pests Cooldown")
		@Config.Comment("Sets the entity respawn cooldown of the Crown of Pests headgear (in ticks, 20 = 1 second).")
		public int head_pest_crown_cooldown = 800;
	}

	@Config.Name("Spell Tweaks Settings")
	@Config.LangKey("settings.necromancersdelight:spell_tweaks")
	public static SpellTweaksSettings spellTweaksSettings = new SpellTweaksSettings();

	public static class SpellTweaksSettings {
	}

}
