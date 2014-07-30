package net.nix.clientmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy {

    public static void addCapes() {
        String capeURL = "http://nix.eu.gg/hlestamod/capeleet.png";
        String[] owners = {"St@SyaN", "DaGGeR", "stitchmod"};

        ThreadDownloadImageData image = new ThreadDownloadImageData(capeURL, null, null);
        ThreadDownloadImageData nixcape = new ThreadDownloadImageData("http://nix.eu.gg/hlestamod/cape.png", null, null);
        ThreadDownloadImageData sjcape = new ThreadDownloadImageData("http://nix.eu.gg/hlestamod/capesj.png", null, null);

        Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/NixHlestakov"), (ITextureObject) nixcape);
        Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/asaskevich"), (ITextureObject) sjcape);

        Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("skins/asaskevich"), (ITextureObject) new ThreadDownloadImageData("http://nix.eu.gg/hlestamod/sjskin.png", null, null));
        Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("skins/stitchmod"), (ITextureObject) new ThreadDownloadImageData("http://nix.eu.gg/hlestamod/stitchmod.png", null, null));
        Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("skins/BogdanG"), (ITextureObject) new ThreadDownloadImageData("http://nix.eu.gg/hlestamod/bogdan.png", null, null));
        Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("skins/Bogdan-G"), (ITextureObject) new ThreadDownloadImageData("http://nix.eu.gg/hlestamod/bogdan.png", null, null));

        for (String username : owners) {
            Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/" + username), (ITextureObject) image);
        }
    }
}