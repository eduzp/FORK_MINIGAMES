package mc.twilight.utilidades.listeners;

import mc.twilight.utilidades.listeners.player.AsyncPlayerChatListener;
import mc.twilight.utilidades.listeners.player.PlayerJoinListener;
import mc.twilight.utilidades.listeners.player.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import mc.twilight.utilidades.Main;

public class Listeners {

    public static void setupListeners() {
        try {
            PluginManager pm = Bukkit.getPluginManager();

            pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class)
                    .invoke(pm, new PlayerJoinListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class)
                    .invoke(pm, new PlayerQuitListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class)
                    .invoke(pm, new AsyncPlayerChatListener(), Main.getInstance());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}