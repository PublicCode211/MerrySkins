package me.mrmaurice.merryskin.commands.subcommands;

import me.mrmaurice.merryskin.MerrySkin;
import me.mrmaurice.merryskin.cache.SkinCache;
import me.mrmaurice.merryskin.commands.MerrySubCmd;
import me.mrmaurice.merryskin.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

public class Toggle extends MerrySubCmd {

	private Configuration config;
	
	public Toggle(String[] aliases, int prior, String usage, String perm) {
		super(aliases, prior, usage, perm);
		config = MerrySkin.getInstance().getConfig();
	}

	@Override
	public void onCommand(ProxiedPlayer player, String[] args) {
		if(!hasPermission(player)) return;
		SkinCache cache = MerrySkin.getCacheManager().getPlayer(player);
		if(cache.isEnabled() == true){
			cache.setEnabled(false);
			Utils.sendMessage(player, config.getString("messages.toggleSkin.disabledSkin"), true);
		} else {
			cache.setEnabled(true);
			Utils.sendMessage(player, config.getString("messages.toggleSkin.enabledSkin"), true);
		}
		MerrySkin.getCacheManager().saveSkin(player, cache);
	}

}
