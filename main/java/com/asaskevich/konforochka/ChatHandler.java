package com.asaskevich.konforochka;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChatHandler {

	private HashMap<String, String> players = new HashMap<String, String>();
	private HashSet<String> badWords = new HashSet<String>();

	public ChatHandler() {
		// Our players
		players.put("asaskevich", "§n§l§6");
		players.put("NixHlestakov", "§n§l§c");
		players.put("St@SyaN", "§n§l§5");
		players.put("DaGGeR", "§n§l§a");
		players.put("stitchmod", "§n§l§e");
		players.put("BogdanG", "§n§l§2");
		players.put("Slaughter", "§n§l§9");
		// Add your bad words
		badWords.add("pony");
	}

	@SubscribeEvent
	public void onMessageReceived(ClientChatReceivedEvent event) {
		try {
			IChatComponent message = event.message;
			String text = message.getUnformattedText();
			for (String user : players.keySet())
				text = text.replaceAll("<" + user + ">", players.get(user) + "<" + user + ">§r");
			for (String word : badWords)
				text = text.replaceAll(word, "******");
			event.setCanceled(true);
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(text));
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
