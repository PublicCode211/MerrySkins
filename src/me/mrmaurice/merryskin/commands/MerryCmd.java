package me.mrmaurice.merryskin.commands;

import java.util.ArrayList;
import java.util.Collections;

import me.mrmaurice.merryskin.MerrySkin;
import me.mrmaurice.merryskin.commands.subcommands.Change;
import me.mrmaurice.merryskin.commands.subcommands.Reload;
import me.mrmaurice.merryskin.commands.subcommands.Toggle;
import me.mrmaurice.merryskin.commands.subcommands.Update;
import me.mrmaurice.merryskin.utils.Utils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class MerryCmd extends Command {

	private ArrayList<MerrySubCmd> subCmds = new ArrayList<>();
	private final String BASE_PERM;
	
	private Configuration config;
	
	public MerryCmd(String[] aliases, String permission, String basePermission) {
		super(aliases[0], permission, aliases);
		config = MerrySkin.getInstance().getConfig();
		BASE_PERM = basePermission;
		
		String[] change = config.getStringList("commands.subCommands.change").isEmpty() 
				? new String[] {"change"} 
				: config.getStringList("commands.subCommands.change").toArray(new String[0]);
				
		String[] toggle = config.getStringList("commands.subCommands.toggle").isEmpty() 
				? new String[] {"toggle"} 
				: config.getStringList("commands.subCommands.toggle").toArray(new String[0]);;
				
		String[] update = config.getStringList("commands.subCommands.update").isEmpty() 
				? new String[] {"update"} 
				: config.getStringList("commands.subCommands.update").toArray(new String[0]);;
		
		addSubCommand(
				new Change(change, 0, "<list/hatType> &b- Change your hat!", BASE_PERM + ".change"));
		addSubCommand(
				new Toggle(toggle, 1, "&b- Enable the apply of the hat!", BASE_PERM + ".toggle"));
		addSubCommand(
				new Update(update, 2, "&b- If you get a new skin, use this command!", null));
		addSubCommand(
				new Reload(new String[] {"reload"}, 5, "&b- Reload your config!", BASE_PERM + ".reload"));
		
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (isPlayer(sender))
			onSubCommand((ProxiedPlayer) sender, args);
		return;
	}

	private void onSubCommand(ProxiedPlayer player, String[] args) {
		if (args.length == 0) {
			showHelp(player);
			return;
		}
		for (MerrySubCmd command : subCmds) {
			if (command.isSubCommand(args[0])) {
				command.onCommand(player, args);
				return;
			}
		}
		showHelp(player);
		Utils.sendMessage(player, config.getString("messages.commands.commandNotFound"), true);

	}

	public void addSubCommand(MerrySubCmd command) {
		subCmds.add(command);
		sort();
	}
	
	public static boolean isPlayer(CommandSender sender) {
		return sender instanceof ProxiedPlayer;
	}

	protected void sort() {
		Collections.sort(subCmds);
	}
	
	public void showHelp(CommandSender sender){
		ProxiedPlayer player = (ProxiedPlayer) sender;
		StringBuilder sb = new StringBuilder();
		sb.append(config.getString("messages.commands.helpHeader") + "\n");
		for (MerrySubCmd command : subCmds)
			if(command.hasPermission(player))
				sb.append("&7/" + this.getName() + " " + command.USAGE + "\n");
		sb.append(config.getString("messages.commands.helpFooter"));
		Utils.sendMessage(sender, sb.toString(), false);
			
	}

}
