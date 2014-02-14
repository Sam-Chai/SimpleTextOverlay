package com.github.lunatrius.ingameinfo;

import com.github.lunatrius.core.version.VersionChecker;
import com.github.lunatrius.ingameinfo.command.InGameInfoCommand;
import com.github.lunatrius.ingameinfo.config.Config;
import com.github.lunatrius.ingameinfo.lib.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.client.ClientCommandHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME)
public class InGameInfoXML {
	@Instance(Reference.MODID)
	public static InGameInfoXML instance;

	private final InGameInfoCore core = InGameInfoCore.instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		VersionChecker.registerMod(event.getModMetadata());

		Reference.logger = event.getModLog();

		Reference.config = new Config(event.getSuggestedConfigurationFile());
		Reference.config.save();

		Ticker.showInChat = Reference.config.getShowInChat();

		this.core.setConfigDirectory(event.getModConfigurationDirectory());
		this.core.copyDefaultConfig();
		this.core.setConfigFile(Reference.config.getConfigName());
		this.core.reloadConfig();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new Ticker(this.core));

		ClientCommandHandler.instance.registerCommand(new InGameInfoCommand(this.core));
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		this.core.setServer(event.getServer());
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		this.core.setServer(null);
	}
}
