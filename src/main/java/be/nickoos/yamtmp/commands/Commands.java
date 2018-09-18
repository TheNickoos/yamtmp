package be.nickoos.yamtmp.commands;

import java.util.Map;

import be.nickoos.yamtmp.yamtmp;
import be.nickoos.yamtmp.commands.CommandExecutor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.util.CaseInsensitiveMap;

public class Commands extends CommandExecutor {
	private final Map<String, Command> subCommands = new CaseInsensitiveMap<>();
	public Commands() {
		super("yamtmp");
		init();
	}

	private void init() {
	        addSubCommand(new CommandBase("reload", "yamtmp.admin", this::commandReload ));
	        addSubCommand(new CommandBase("help", null, this::commandHelp, "?"));
	        addSubCommand(new CommandBase("announce", "yamtmp.admin", this::commandHelp, "?"));
	        setDefaultAction(this::commandHelp);
	}
	private void commandReload(CommandSender sender) {
        boolean success = yamtmp.reload();
        if (success) {
            sender.sendMessage("&aSuccessfully reloaded YAMTMP.");
        } else {
            sender.sendMessage("&cAn error occurred while reloaded YAMTMP.");
        }
	}
	private void commandHelp(CommandSender sender) {
	        sender.sendMessage("&e&lAvailable Commands:");
	        if(checkPermission(sender, subCommands.get("help"))) { sender.sendMessage("&e/yamtmp reload &f&oReload the configuration"); }
	        if(checkPermission(sender, subCommands.get("announce"))) { sender.sendMessage("&e/yamtmp announce addglobal [message] &f&oAdd a global announce"); }
	        if(checkPermission(sender, subCommands.get("announce"))) { sender.sendMessage("&e/yamtmp announce addserver [server] [message] &f&oAdd a server announce"); }
	        if(checkPermission(sender, subCommands.get("announce"))) { sender.sendMessage("&e/yamtmp announce del [id] &f&oDel an announce"); }
	        if(checkPermission(sender, subCommands.get("announce"))) { sender.sendMessage("&e/yamtmp announce list &f&oList all announce"); }
	}
	
	private void announce(CommandSender sender, String[] args) {
		String param1 = args[1];
		if (param1 == "addglobal") {
			if (args[2] == "") { //Pas bon 
			} else {
				// Ajouter annonce global
			}
		}
		if (param1 == "addserver") {
			if (args[2] == "" || args[3] == "") { //Pas bon 
			} else {
				// Ajouter annonce serveur
			}
		}
		if (param1 == "del") {
			if (args[2] == "") { //Pas bon 
			} else {
				// Supprimer annonce
			}
		}
		if (param1 == "list") {
			// liste des annonces
		}
	}
}
