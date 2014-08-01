package com.asaskevich.konforochka;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindler {
	public static KeyBinding renderBlockDamage;

	public static void init() {
		renderBlockDamage = new KeyBinding("Render Block Damage", Keyboard.KEY_F, "Konforochka");
		ClientRegistry.registerKeyBinding(renderBlockDamage);
	}
}
