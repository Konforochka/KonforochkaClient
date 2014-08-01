package com.asaskevich.konforochka;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class AS_Konforochka {
	public static RenderHandler renderHandler;
	public static KeyInputHandler keyInputHandler;
	public static EntityKillHandler entityKillHandler;
	public static ChatHandler chatHandler;
	public static Minecraft mc;

	public static void init(FMLPreInitializationEvent event) {
		// Init
		boolean isRender = getRenderSetting(event.getSuggestedConfigurationFile());
		mc = Minecraft.getMinecraft();
		chatHandler = new ChatHandler();
		entityKillHandler = new EntityKillHandler();
		renderHandler = new RenderHandler(mc, isRender);
		keyInputHandler = new KeyInputHandler(renderHandler, event.getSuggestedConfigurationFile());
		KeyBindler.init();
		// Register handlers
		MinecraftForge.EVENT_BUS.register(chatHandler);
		MinecraftForge.EVENT_BUS.register(entityKillHandler);
		MinecraftForge.EVENT_BUS.register(renderHandler);
		FMLCommonHandler.instance().bus().register(keyInputHandler);
	}

	public static boolean getRenderSetting(File configFile) {
		Configuration config = new Configuration(configFile);
		config.load();
		boolean renderBlockDamage = config.get("konforochka", "show_block_damage", true).getBoolean(true);
		config.save();
		return renderBlockDamage;
	}

}
