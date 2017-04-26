package me.mrmaurice.merryskin.commands.subcommands;

import java.awt.image.BufferedImage;

import me.mrmaurice.merryskin.HeadEnum;
import me.mrmaurice.merryskin.MerrySkin;
import me.mrmaurice.merryskin.cache.SkinCache;
import me.mrmaurice.merryskin.commands.MerrySubCmd;
import me.mrmaurice.merryskin.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

public class Change extends MerrySubCmd {

	private Configuration config;
	
	public Change(String[] aliases, int prior, String usage, String perm) {
		super(aliases, prior, usage, perm);
		config = MerrySkin.getInstance().getConfig();
	}

	@Override
	public void onCommand(ProxiedPlayer player, String[] args) {
		if(!hasPermission(player)) return;
		
		if(args.length != 2){
			Utils.sendMessage(player, USAGE, true);
			return;
		}
		
		if(args[1].equalsIgnoreCase("list")){
			sendHats(player);
			return;
		}
		
		
		if(!isHat(args[1])){
			String msg = Utils.replacePlace(config.getString("messages.hatChange.noHatFound"), "%hat%", args[1]);
			Utils.sendMessage(player, msg, true);
			sendHats(player);
			return;
		}
		
		SkinCache cache = MerrySkin.getCacheManager().getPlayer(player);
		if(cache.getType() == HeadEnum.valueOf(args[1].toUpperCase())){
			Utils.sendMessage(player, config.getString("messages.hatChange.alreadyUsingTheSameHat"), true);
			return;
		}
			
		cache.setType(HeadEnum.valueOf(args[1].toUpperCase()));
		BufferedImage pSkin = MerrySkin.getFactory().getSkin(player);
		if(pSkin == null){
			Utils.sendMessage(player, config.getString("messages.hatChange.noAccountSkinError"), true);
			return;
		}
		
		if(!MerrySkin.getFactory().createSkin(player, pSkin, cache.getType())){
			Utils.sendMessage(player, config.getString("messages.hatChange.errorInChangeSkin"), true);
			return;
		}
		Utils.sendMessage(player, config.getString("messages.hatChange.skinChanged"), true);
				
	}
	
	private boolean isHat(String hat){
		for(HeadEnum head : HeadEnum.values())
			if(head.name().equalsIgnoreCase(hat)) return true;
		return false;
	}

	private void sendHats(ProxiedPlayer player){
		StringBuilder sb = new StringBuilder();
		sb.append("&aAvaliable Hats: \n");
		for(HeadEnum head : HeadEnum.values()){
			sb.append("  &7/merrySkins change &b" + head.name().toLowerCase() + "\n");
		}
		Utils.sendMessage(player, sb.toString().trim(), false);
	}
	
}
