package jc.jcaddons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Timer implements Runnable {
    public void run() {
        JCADDONS plugin = (JCADDONS)JCADDONS.getPlugin(JCADDONS.class);
        if (Handlers.time.keySet() == null)
            return;
        for (String a : Handlers.time.keySet()) {
            Player p = Bukkit.getPlayer(a);
            if (p != null) {
                if (((Integer)Handlers.time.get(p.getName())).intValue() == plugin.cfg.getInt("Settings.time")) {
                    ((BossBar)Handlers.bar.get(p.getName())).removePlayer(p);
                    Handlers.bar.remove(p.getName());
                    Handlers.time.remove(p.getName());
                    p.teleport(Handlers.oldLoc.get(p.getName()));
                    Handlers.oldLoc.remove(p.getName());
                    continue;
                }
                int time = ((Integer)Handlers.time.get(p.getName())).intValue();
                Handlers.time.remove(p.getName());
                Handlers.time.put(p.getName(), Integer.valueOf(time + 1));
                ((BossBar)Handlers.bar.get(p.getName())).removePlayer(p);
                Handlers.bar.remove(p.getName());
                BossBar b = Bukkit.createBossBar(text(plugin.cfg.getString("Settings.bossbar").replace("#time#", String.valueOf(plugin.cfg.getInt("Settings.time") - ((Integer)Handlers.time.get(p.getName())).intValue()))), BarColor.RED, BarStyle.SOLID, new org.bukkit.boss.BarFlag[0]);
                b.addPlayer(p);
                b.setVisible(true);
                Handlers.bar.put(p.getName(), b);
            }
        }
    }

    private String text(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}