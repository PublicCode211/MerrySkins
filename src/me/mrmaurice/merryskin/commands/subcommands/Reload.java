package me.mrmaurice.merryskin.commands.subcommands;

import me.mrmaurice.merryskin.MerrySkin;
import me.mrmaurice.merryskin.commands.MerrySubCmd;
import me.mrmaurice.merryskin.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Reload extends MerrySubCmd {

	public Reload(String[] aliases, int prior, String usage, String perm) {
		super(aliases, prior, usage, perm);
	}

	@Override
	public void onCommand(ProxiedPlayer player, String[] args) {
		if(!hasPermission(player)) return;
		
		MerrySkin.getInstance().loadConfig();
		Utils.sendMessage(player, "&aConfig reloaded!", true);
		
		
	}

}
