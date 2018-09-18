package be.nickoos.yamtmp.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CommandBase extends Command {
    private final BiConsumer<CommandSender, String[]> action;

    public CommandBase(String name, String permission, Consumer<CommandSender> action, String... aliases) {
        this(name, permission, (a, b) -> action.accept(a), aliases);
    }

    public CommandBase(String name, String permission, BiConsumer<CommandSender, String[]> action, String... aliases) {
        super(name, permission, aliases);
        this.action = action;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        action.accept(sender, args);
    }

    public static Consumer<CommandSender> playerCommand(Consumer<ProxiedPlayer> cmd) {
        return sender -> {
            if (sender instanceof ProxiedPlayer) {
                cmd.accept(((ProxiedPlayer) sender));
            } else {
                sender.sendMessage("&cThis command can only be used ingame.");
            }
        };
    }
}