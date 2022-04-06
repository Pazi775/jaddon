package jc.jcaddons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class Handlers implements Listener, CommandExecutor {
    public static HashMap<String, Integer> time = new HashMap();
    public static HashMap<String, BossBar> bar = new HashMap();
    public static HashMap<String, Location> oldLoc = new HashMap();

    public ItemStack getPotion() {
        JCADDONS plugin = (JCADDONS)JCADDONS.getPlugin(JCADDONS.class);
        ItemStack p = new ItemStack(Material.POTION);
        PotionMeta m = (PotionMeta)p.getItemMeta();
        m.setDisplayName(this.text(plugin.cfg.getString("Settings.item.name")));
        List<String> lore = plugin.cfg.getStringList("Settings.item.lore");
        Iterator var6 = lore.iterator();

        while(var6.hasNext()) {
            String lores = (String)var6.next();
            lore.set(lore.indexOf(lores), this.text(lores));
        }

        m.setLore(lore);
        p.setItemMeta(m);
        return p;
    }

    @EventHandler
    public void onInteract(PlayerItemConsumeEvent e) {
        JCADDONS plugin = (JCADDONS)JCADDONS.getPlugin(JCADDONS.class);
        ItemStack s = e.getItem();
        Player p = e.getPlayer();
        if (s != null) {
            if (s.hasItemMeta()) {
                if (s.getItemMeta().getDisplayName() != null) {
                    if (s.getItemMeta().getDisplayName().equals(this.getPotion().getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.getInventory().removeItem(new ItemStack[]{this.getPotion()});
                        time.put(p.getName(), 1);
                        oldLoc.put(p.getName(), p.getLocation());
                        BossBar b = Bukkit.createBossBar(this.text(plugin.cfg.getString("Settings.bossbar").replace("#time#", String.valueOf(plugin.cfg.getInt("Settings.time") - (Integer)time.get(p.getName())))), BarColor.RED, BarStyle.SOLID, new BarFlag[0]);
                        b.addPlayer(p);
                        b.setVisible(true);
                        bar.put(p.getName(), b);
                    }

                }
            }
        }
    }

    private String text(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
        if (!(sender instanceof Player)) {
            Player p = Bukkit.getPlayer(arg3[0]);
            p.getInventory().addItem(new ItemStack[]{this.getPotion()});
            return true;
        } else {
            return sender instanceof Player;
        }
    }
}