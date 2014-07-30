package net.nix.clientmod;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

import static net.nix.clientmod.Hlestamod.*;

@Mod(modid = MODID, version = VERSION)
public class Hlestamod {

    public static final String MODID = "Konforochka's Client Side Tweaks";
    public static final String VERSION = "summer adventures 2014 edition";

    @SidedProxy(clientSide = "net.nix.clientmod.ClientProxy", serverSide = "net.nix.clientmod.CommonProxy")
    public static CommonProxy proxy;


    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {

        if (Loader.isModLoaded("gregtech_addon")) {
            System.err.println("[Hlestamod] DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH DELETE GREGTECH");
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    @SideOnly(Side.CLIENT)
    public void postInit(FMLPostInitializationEvent event){
        Writer writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(Loader.instance().getConfigDir() + "/.OreDictNames.txt"));
            writer.append("There are listed all registered in game OreDictionary names\n\n");
            for(String s : OreDictionary.getOreNames()){
                for(int i = 0; i < OreDictionary.getOres(s).size(); i++){
                    writer.append(s + " -- " + OreDictionary.getOres(s).get(i).getDisplayName() + "\n");
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(writer != null){
                try{
                    writer.close();
                }catch(IOException e){}
            }
        }
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        ClientProxy.addCapes();

        for (ModContainer o : Loader.instance().getModList())
        {
            if (o.getModId().toLowerCase().contains("gregtech"))
            {
                try
                {

                    ModContainer mod = o;
                    Field ann = FMLModContainer.class.getDeclaredField("eventBus");
                    ann.setAccessible(true);
                    com.google.common.eventbus.EventBus googlebus = (com.google.common.eventbus.EventBus) ann.get(mod);
                    googlebus.unregister(mod);

                    Class clazz = Class.forName("gregtechmod.common.GT_OreDictHandler");
                    MinecraftForge.EVENT_BUS.unregister(clazz);
                    System.out.println("[nixhlestakov] Hehehehe!");
                }
                catch (Exception e)
                {
                    System.err.println("[nixhlestakov] Can't crash GregTech");
                    e.printStackTrace();
                }
            }
        }
    }
}
