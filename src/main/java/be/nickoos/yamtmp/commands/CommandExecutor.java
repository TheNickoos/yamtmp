package be.nickoos.yamtmp.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.util.CaseInsensitiveMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import be.nickoos.yamtmp.yamtmp;

public class CommandExecutor extends Command implements TabExecutor {
    private final Map<String, Command> subCommands = new CaseInsensitiveMap<>();
    private Consumer<CommandSender> defaultAction = null;

    public CommandExecutor(String name) {
        super(name);
    }

    public CommandExecutor(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    public void addSubCommand(Command command) {
        subCommands.put(command.getName(), command);
        for (String alias : command.getAliases()) {
            subCommands.put(alias, command);
        }
    }

    public void setDefaultAction(Consumer<CommandSender> defaultAction) {
        this.defaultAction = defaultAction;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0 && subCommands.containsKey(args[0])) {
            Command command = subCommands.get(args[0]);
            if (command.getPermission() == null || checkPermission(sender, command)) {
                command.execute(sender, Arrays.copyOfRange(args, 1, args.length));
            } else {
                sender.sendMessage(TextComponent.fromLegacyText(ProxyServer.getInstance().getTranslation("no_permission")));
            }
        } else if (defaultAction != null) {
            defaultAction.accept(sender);
        } else {
            sender.sendMessage("&cWrong usage!");
        }
    }

    protected boolean checkPermission(CommandSender sender, Command command) {
		if (sender.hasPermission("yamtmp." + command)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return subCommands.keySet().stream()
                    .filter(cmd -> cmd.startsWith(args[0]))
                    .collect(Collectors.toList());
        } else if (args.length > 1) {
            Command command = subCommands.get(args[0]);
            if (command != null && command instanceof TabExecutor) {
                if (command.getPermission() == null || checkPermission(sender, command)) {
                    ((TabExecutor) command).onTabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }
        return Collections.emptyList();
    }

}