package jc.jcaddons;

import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {
    public AbstractCommand(String command) {
        PluginCommand pluginCommand = JCADDONS.getInstance().getCommand(command);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
    }

    public abstract void execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);

    public List<String> complete(CommandSender sender, String[] args) {
        return null;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender == null) ;
        execute(sender, label, args);
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return filter(complete(sender, args), args);
    }

    private List<String> filter(List<String> list, String[] args) {
        if (list == null)
            return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for (String arg : list) {
            if (arg.toLowerCase().startsWith(last.toLowerCase()))
                result.add(arg);
        }
        return result;
    }
}
