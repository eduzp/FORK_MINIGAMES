package mc.twilight.utilidades.bungee;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;
import mc.twilight.utilidades.bungee.cmd.Commands;
import mc.twilight.utilidades.bungee.listeners.Listeners;

import java.util.HashMap;
import java.util.Map;

public class Bungee extends Plugin {

    public static Map<ProxiedPlayer, ProxiedPlayer> baianice = new HashMap<>();
    private static Bungee instance;

    public Bungee() {
        instance = this;
    }

    public static Bungee getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Commands.setupCommands();
        getProxy().getPluginManager().registerListener(this, new Listeners());
        this.getLogger().info("O plugin foi ativado.");
    }
}
