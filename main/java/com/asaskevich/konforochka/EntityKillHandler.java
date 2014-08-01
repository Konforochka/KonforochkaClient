package com.asaskevich.konforochka;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityKillHandler {
	private Random r = new Random();

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		try {
			if (event.source.getDamageType().equals("player")) {
				EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
				String user = player.getGameProfile().getName();
				String entity = event.entityLiving.getCommandSenderName();
				MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(getRandomMessage(user, entity)));
			}
		} catch (Exception e) {
			// Something happened
		}
	}

	public String getRandomMessage(String user, String entity) {
		final int code = r.nextInt(5);
		switch (code) {
		case 0:
			return "§n§l§c" + user + "§r is very bad boy! He killed §n§l§2" + entity + "§r";
		case 1:
			return "§n§l§c" + user + "§r vs §n§l§2" + entity + "§r  1:0";
		case 2:
			return "§n§l§2<" + entity + ">§r NOOOOO!";
		case 3:
			return "§n§l§2<" + entity + ">§r Help! §n§l§c" + user + "§r wants to kill me!";
		case 4:
			return "Hey, §n§l§c" + entity + "§r, I want your DIAMONDS!!! Myahaha!";
		}
		return "";
	}
}
