package net.nix.clientmod;

import static net.nix.clientmod.Hlestamod.MODID;
import static net.nix.clientmod.Hlestamod.VERSION;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import com.asaskevich.konforochka.AS_Konforochka;

import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = MODID, version = VERSION)
public class Hlestamod {

	public static final String MODID = "Konforochka's Client Side Tweaks";
	public static final String VERSION = "1.0";

	@SidedProxy(clientSide = "net.nix.clientmod.ClientProxy", serverSide = "net.nix.clientmod.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		AS_Konforochka.init(event);
		for (ModContainer o : Loader.instance().getModList()) {
			if (o.getModId().toLowerCase().contains("gregtech"))
				System.err
						.println("[Hlestamod] DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH");
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.onModInit(event);
	}

	@EventHandler
	@SideOnly(Side.CLIENT)
	public void postInit(FMLPostInitializationEvent event) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(Loader.instance().getConfigDir() + "/.OreDictNames.txt"));
			writer.append("There are listed all registered in game OreDictionary names\n\n");
			for (String s : OreDictionary.getOreNames()) {
				for (int i = 0; i < OreDictionary.getOres(s).size(); i++) {
					writer.append(s + " -- " + OreDictionary.getOres(s).get(i).getDisplayName() + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {}
			}
		}
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		ClientProxy.addCapes();

		for (ModContainer o : Loader.instance().getModList()) {
			if (o.getModId().toLowerCase().contains("gregtech")) {
				try {

					ModContainer mod = o;
					Field ann = FMLModContainer.class.getDeclaredField("eventBus");
					ann.setAccessible(true);
					com.google.common.eventbus.EventBus googlebus = (com.google.common.eventbus.EventBus) ann.get(mod);
					googlebus.unregister(mod);

					Class clazz = Class.forName("gregtechmod.common.GT_OreDictHandler");
					MinecraftForge.EVENT_BUS.unregister(clazz);
					System.out.println("[nixhlestakov] Hehehehe!");
				} catch (Exception e) {
					System.err.println("[nixhlestakov] Can't crash GregTech");
					e.printStackTrace();
				}
			}
		}
	}
}
