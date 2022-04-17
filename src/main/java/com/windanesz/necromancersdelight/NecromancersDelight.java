package com.windanesz.necromancersdelight;

import com.windanesz.necromancersdelight.client.NDGuiHandler;
import com.windanesz.necromancersdelight.network.SBPacketHandler;
import com.windanesz.necromancersdelight.registry.NDBlocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(modid = NecromancersDelight.MODID, name = NecromancersDelight.NAME, version = "@VERSION@", acceptedMinecraftVersions = "[@MCVERSION@]",
		dependencies = "required-after:ebwizardry@[@WIZARDRY_VERSION@,4.4);required-after:mod_lavacow@[@FUR_VERSION@,4.4)")
public class NecromancersDelight {

	public static final String MODID = "necromancersdelight";
	public static final String NAME = "Necromancer's Delight";

	public static final Random rand = new Random();

	public static Logger logger;

	// The instance of wizardry that Forge uses.
	@Mod.Instance(NecromancersDelight.MODID)
	public static NecromancersDelight instance;

	// Location of the proxy code, used by Forge.
	@SidedProxy(clientSide = "com.windanesz.necromancersdelight.client.ClientProxy", serverSide = "com.windanesz.necromancersdelight.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		// content mods

		proxy.registerRenderers();

		// Register things that don't have registries
		NDBlocks.registerTileEntities();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		MinecraftForge.EVENT_BUS.register(instance);
		proxy.registerParticles();
		proxy.init();
		SBPacketHandler.initPackets();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new NDGuiHandler());

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) { }

	@EventHandler
	public void serverStartup(FMLServerStartingEvent event) { }

}
