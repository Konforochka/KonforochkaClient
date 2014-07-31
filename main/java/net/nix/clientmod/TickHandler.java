package net.nix.clientmod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;

public class TickHandler {

    public boolean hasSet = false;

    public String GregoSplash() {
        return "YOUR GREGTECH IS NOT WORKING DELETE GREGTECH";

    }

    @SubscribeEvent
    public void start(RenderTickEvent event) {
        if (event.side.equals(Side.CLIENT)) {
            tickStart();
            tickEnd();
        }
    }

    public void tickStart() {
        for (ModContainer o : Loader.instance().getModList())
        {
            if (o.getModId().toLowerCase().contains("gregtech"))
            try {
                Minecraft mc = Minecraft.getMinecraft();
                if (mc.currentScreen != null) {
                    if (mc.currentScreen instanceof GuiMainMenu) {
                        GuiMainMenu mm = (GuiMainMenu) mc.currentScreen;
                        if (!hasSet) {
                            ObfuscationReflectionHelper.setPrivateValue(
                                    GuiMainMenu.class, mm, GregoSplash(),
                                    "splashText", "field_73975_c", "r");
                            hasSet = true;
                        }
                    } else {
                        hasSet = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tickEnd() {
        try {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen != null) {
                if (mc.currentScreen instanceof GuiMainMenu) {
                    GuiMainMenu mm = (GuiMainMenu) mc.currentScreen;
                    mm.drawString(mc.fontRenderer, "Konforochka's Client Side Tweaks v." + Hlestamod.VERSION, 2, 2, 0xffffff);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
