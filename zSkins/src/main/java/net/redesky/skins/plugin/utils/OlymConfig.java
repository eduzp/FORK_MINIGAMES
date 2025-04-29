package net.redesky.skins.plugin.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import net.redesky.skins.Core;
import net.redesky.skins.bukkit.compatibility.BukkitYamlConfiguration;
import net.redesky.skins.bungee.compatibility.BungeeYamlConfiguration;
import net.redesky.skins.plugin.compatibility.YamlConfiguration;

public class OlymConfig {
   private File file;
   private YamlConfiguration config;
   public static final OlymLogger LOGGER;
   private static Map<String, OlymConfig> cache;

   private OlymConfig(String path, String name) {
      this.file = new File(path + "/" + name + ".yml");
      if (!this.file.exists()) {
         this.file.getParentFile().mkdirs();
         InputStream in = Core.getResource(name + ".yml");
         if (in != null) {
            FileUtils.copyFile(in, this.file);
         } else {
            try {
               this.file.createNewFile();
            } catch (IOException var6) {
               LOGGER.log(Level.SEVERE, "Unexpected error ocurred creating file " + this.file.getName() + ": ", var6);
            }
         }
      }

      try {
         this.config = (YamlConfiguration)(Core.BUNGEE ? new BungeeYamlConfiguration(this.file) : new BukkitYamlConfiguration(this.file));
      } catch (IOException var5) {
         LOGGER.log(Level.SEVERE, "Unexpected error ocurred creating config " + this.file.getName() + ": ", var5);
      }

   }

   public boolean createSection(String path) {
      return this.config.createSection(path);
   }

   public boolean set(String path, Object obj) {
      return this.config.set(path, obj);
   }

   public boolean contains(String path) {
      return this.config.contains(path);
   }

   public Object get(String path) {
      return this.config.get(path);
   }

   public int getInt(String path) {
      return this.config.getInt(path);
   }

   public int getInt(String path, int def) {
      return this.config.getInt(path, def);
   }

   public double getDouble(String path) {
      return this.config.getDouble(path);
   }

   public double getDouble(String path, double def) {
      return this.config.getDouble(path, def);
   }

   public String getString(String path) {
      return this.config.getString(path);
   }

   public boolean getBoolean(String path) {
      return this.config.getBoolean(path);
   }

   public List<String> getStringList(String path) {
      return this.config.getStringList(path);
   }

   public Set<String> getKeys(boolean flag) {
      return this.config.getKeys(flag);
   }

   public boolean reload() {
      return this.config.reload();
   }

   public boolean save() {
      return this.config.save();
   }

   public File getFile() {
      return this.file;
   }

   public static OlymConfig getConfig(String name) {
      return getConfig(name, "plugins/RedeSky");
   }

   public static OlymConfig getConfig(String name, String path) {
      if (!cache.containsKey(path + "/" + name)) {
         cache.put(path + "/" + name, new OlymConfig(path, name));
      }

      return (OlymConfig)cache.get(path + "/" + name);
   }

   static {
      LOGGER = Core.LOGGER.getModule("CONFIG_UTILS");
      cache = new HashMap();
   }
}
