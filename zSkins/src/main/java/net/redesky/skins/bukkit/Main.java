package net.redesky.skins.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import net.redesky.skins.Core;
import net.redesky.skins.Language;
import net.redesky.skins.bukkit.api.Group;
import net.redesky.skins.bukkit.cmd.Commands;
import net.redesky.skins.bukkit.database.Database;
import net.redesky.skins.bukkit.listeners.BungeeSkinListener;
import net.redesky.skins.bukkit.listeners.Listeners;
import net.redesky.skins.plugin.CooldownStorage;

public class Main extends JavaPlugin {
   private static Main instance;

   public Main() {
      instance = this;
   }

   public void onEnable() {
      this.saveDefaultConfig();
      Language.setupSettings();
      Group.makeGroups();
      Database.makeBackend();
      boolean bungee = this.getConfig().getBoolean("bungeecord");
      if (bungee) {
         BungeeSkinListener skinListener = new BungeeSkinListener();
         Bukkit.getPluginManager().registerEvents(skinListener, getInstance());
         Bukkit.getMessenger().registerOutgoingPluginChannel(getInstance(), "RedeSky");
         Bukkit.getMessenger().registerIncomingPluginChannel(getInstance(), "RedeSky", skinListener);
         Core.LOGGER.info("O plugin foi ativado.");
      } else {
         Commands.makeCommands();
         Listeners.makeListeners();
         Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new CooldownStorage(), 0L, 200L);
         Core.LOGGER.info("O plugin foi ativado.");
      }
   }

   public void onDisable() {
      Core.LOGGER.info("O plugin foi desativado.");
   }

   public static Main getInstance() {
      return instance;
   }
}
