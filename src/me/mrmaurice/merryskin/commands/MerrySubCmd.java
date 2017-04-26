package me.mrmaurice.merryskin.commands;

import java.util.Arrays;
import java.util.List;

import me.mrmaurice.merryskin.MerrySkin;
import me.mrmaurice.merryskin.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class MerrySubCmd implements Comparable<MerrySubCmd> {

	public final String USAGE;
	private final String PERMISSION;
	private final int PRIORITY;
	protected final List<String> aliases;
	
	public MerrySubCmd(String[] aliases, int prior, String usage, String perm){
		this.aliases = Arrays.asList(aliases);
		USAGE = aliases[0] + " " + usage;
		PRIORITY = prior;
		PERMISSION = perm;
	}
	
	public boolean isSubCommand(String subCmd){
		return aliases.contains(subCmd);
	}

	public abstract void onCommand(ProxiedPlayer player, String[] args);
	
	@Override
	public int compareTo(MerrySubCmd cmd) {
		return ((Integer) PRIORITY).compareTo(cmd.PRIORITY);
	}

	public String getCommandName() {
		return aliases.get(0);
	}
	
	public boolean hasPermission(ProxiedPlayer player){
		if(PERMISSION == null) return true;
		if(!player.hasPermission(PERMISSION))
			Utils.sendMessage(player, MerrySkin.getInstance().getConfig().getString("messages.noPermissions"), true);
		return player.hasPermission(PERMISSION);
	}
	
	public boolean hasPermission(ProxiedPlayer player, String perm){
		if(perm == null) return true;
		if(!player.hasPermission(perm))
			Utils.sendMessage(player, MerrySkin.getInstance().getConfig().getString("messages.noPermissions"), true);
		return player.hasPermission(perm);
	}
	
}
