package jc.jcaddons;

import jc.jcaddons.Listener.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class JCADDONS extends JavaPlugin {
    public static ArrayList<Player> permissions = new ArrayList<>();
    private static JCADDONS instance;

    FileConfiguration cfg = getConfig();
    static File logs = new File("plugins/JCADDONS/logs.yml");
    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(logs);

    public static JCADDONS getInstance() {
        return instance;
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        instance = this;
        saveDefaultConfig();

        if (!logs.exists()) {
            try {
                logs.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "╭﹏﹏﹏﹏﹏﹏JADDONS﹏﹏﹏﹏﹏﹏");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "│ SUPPORT-@xzyouname");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "│ GRUPA-@JuileStudio");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "│ Deverloper-XXXpaziXXX");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "╰﹏﹏﹏﹏﹏﹏JADDONS﹏﹏﹏﹏﹏﹏");
        new command("spreadplayers");
        getCommand("jpotions").setExecutor(new Handlers());
        Bukkit.getScheduler().runTaskTimer(this, new Timer(), 20L, 20L);
        Bukkit.getPluginManager().registerEvents(new Handlers(), this);


    }   

    public static YamlConfiguration getLogs() {
        return yamlConfiguration;
    }
}