package mc.twilight.utilidades.items.object;

import mc.twilight.utilidades.items.types.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import mc.twilight.utilidades.items.types.*;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractItem implements Listener {

    private String name;

    public AbstractItem(String name) {
        this.name = name;
    }

    public static void setupItems() {
        new Lançador();
        new Flordavida();
        new Tnt();
        new Foguete();
        new SuperPulo();
    }

    private static Map<String, AbstractItem> items = new LinkedHashMap<>();

    public void register(Plugin plugin) {
        items.put(this.name, this);
        if (plugin != null) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }
    }

}
