package net.redesky.skins.bungee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;
import net.redesky.skins.Core;
import net.redesky.skins.Language;
import net.redesky.skins.bungee.api.Group;
import net.redesky.skins.bungee.cmd.Commands;
import net.redesky.skins.bungee.database.Database;
import net.redesky.skins.bungee.listeners.Listeners;
import net.redesky.skins.plugin.CooldownStorage;
import net.redesky.skins.plugin.utils.FileUtils;

public class Main extends Plugin {
   private static Main instance;
   private Configuration config;

   public Main() {
      instance = this;
   }

   public void onEnable() {
      this.saveDefaultConfig();
      Language.setupSettings();
      Group.makeGroups();
      Database.makeBackend();
      Commands.makeCommands();
      Listeners.makeListeners();
      ProxyServer.getInstance().registerChannel("RedeSky");
      ProxyServer.getInstance().getScheduler().schedule(this, new CooldownStorage(), 0L, 10L, TimeUnit.SECONDS);
      Core.LOGGER.info("O plugin foi ativado.");
   }

   public void onDisable() {
      instance = null;
      Core.LOGGER.info("O plugin foi desativado.");
   }

   public void saveDefaultConfig() {
      File file = new File("plugins/RedeSky/config.yml");
      if (!file.exists()) {
         file.getParentFile().mkdirs();
         FileUtils.copyFile(this.getResourceAsStream("config.yml"), file);
      }

      try {
         this.config = YamlConfiguration.getProvider(YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
      } catch (UnsupportedEncodingException var3) {
         Core.LOGGER.log(Level.WARNING, "Cannot load config.yml: ", var3);
      } catch (FileNotFoundException var4) {
         Core.LOGGER.log(Level.WARNING, "Cannot load config.yml: ", var4);
      }

   }

   public Configuration getConfig() {
      return this.config;
   }

   public static Main getInstance() {
      return instance;
   }
}
