package me.mrmaurice.merryskin.commands.subcommands;

import java.awt.image.BufferedImage;

import me.mrmaurice.merryskin.MerrySkin;
import me.mrmaurice.merryskin.cache.SkinCache;
import me.mrmaurice.merryskin.commands.MerrySubCmd;
import me.mrmaurice.merryskin.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

public class Update extends MerrySubCmd {

	private Configuration config;
	
	public Update(String[] aliases, int prior, String usage, String perm) {
		super(aliases, prior, usage, perm);
		config = MerrySkin.getInstance().getConfig();
	}

	@Override
	public void onCommand(ProxiedPlayer player, String[] args) {
		SkinCache pCache = MerrySkin.getCacheManager().getPlayer(player);
		BufferedImage pSkin = MerrySkin.getFactory().getSkin(player);
		if(pSkin != null){
			if(!MerrySkin.getFactory().createSkin(player, pSkin, pCache.getType())){
				Utils.sendMessage(player, config.getString("messages.updateSkin.updatingError"), true);
				return;
			}
			
			Utils.sendMessage(player, config.getString("messages.updateSkin.skinUpdated"), true);
			return;
		}
		
		MerrySkin.getCacheManager().applyDefaultSkin(player);
		String msg = Utils.replacePlace(config.getString("messages.updateSkin.noAccountSkinError"), "%player%", player.getName());
		Utils.sendMessage(player, msg, true);
		
	}

}
