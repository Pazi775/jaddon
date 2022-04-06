package jc.jcaddons;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static jc.jcaddons.JCADDONS.logs;
import static jc.jcaddons.JCADDONS.yamlConfiguration;

public class command extends AbstractCommand {
    public command(String spreadplayers) {
        super("jc");
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = simpleDateFormat.format(new Date());

    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(JCADDONS.getInstance().getConfig().getString("messages.usage").replace("&", "§"));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("jc.reload")) {
                sender.sendMessage(JCADDONS.getInstance().getConfig().getString("messages.noPermission").replace("&", "§"));
                return;
            }

            JCADDONS.getInstance().reloadConfig();
            sender.sendMessage(JCADDONS.getInstance().getConfig().getString("messages.reload.yes").replace("&", "§"));
            return;
        }

        else if (args[0].equalsIgnoreCase("check")) {
            if (!sender.hasPermission("jc.check")) {
                sender.sendMessage(JCADDONS.getInstance().getConfig().getString("messages.checknoprm").replace("&", "§"));
                return;
            }

            Player who = (Player) sender;
            Player to = Bukkit.getPlayer(args[1]);

            if (to != null) {
                who.teleport(to.getLocation());
                who.setGameMode(GameMode.SPECTATOR);

                JCADDONS.getLogs().set(to.getName() + "." + "onХелпер", who.getName());
                JCADDONS.getLogs().set(to.getName() + "." + "ДАТА", date);

                try {
                    yamlConfiguration.save(logs);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                JCADDONS.permissions.add(to);
                sender.sendMessage(JCADDONS.getInstance().getConfig().getString("messages.checkyes").replace("&", "§"));
                return;
            } else {
                sender.sendMessage(JCADDONS.getInstance().getConfig().getString("messages.noplayercheck").replace("&", "§"));
                return;
            }
        }

        else if (args[0].equalsIgnoreCase("uncheck")) {
            if (!sender.hasPermission("jc.uncheck")) {
                sender.sendMessage(JCADDONS.getInstance().getConfig().getString("messages.unchecknoprm").replace("&", "§"));
                return;
            }

            Player who = (Player) sender;
            Player to = Bukkit.getPlayer(args[1]);

            if (to != null) {
                JCADDONS.getLogs().set(to.getName() + "." + "offХЕЛПЕР", who.getName());
                JCADDONS.getLogs().set(to.getName() + "." + "ДАТА", date);

                try {
                    yamlConfiguration.save(logs);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                JCADDONS.permissions.remove(to);
                who.setGameMode(GameMode.SURVIVAL);
                sender.sendMessage(JCADDONS.getInstance().getConfig().getString("messages.uncheckyes").replace("&", "§"));
            }
        } else sender.sendMessage(JCADDONS.getInstance().getConfig().getString("messages.nocmd").replace("&", "§"));
    }

    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) return Lists.newArrayList("reload");

        if (args.length == 2) {
            List<String> players = new ArrayList<>();

            for (Player p : Bukkit.getOnlinePlayers()) {
                players.add(p.getName());
            }

            return players;
        }
        return Lists.newArrayList();
    }
}